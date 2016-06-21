/*
 * Copyright (C) 2016 International Agency for Research on Cancer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * @author Patricio Carranza (patocarranza@gmail.com), 
 *         Beatriz Carballo (beatrizcarballo@gmail.com)
 */
package org.who.canreg.dbprocessor.r;

import java.io.*;
import org.rosuda.REngine.Rserve.*;

/**
 * Class in charge of preparing the R environment, handling Rserve 
 * and every configuration needed to maintain the Java-R bridge
 * alive. ONLY IMPLEMENTED FOR WINDOWS ENVIRONMENTS.  
 * @version 1.00.000
 * Last update: 23/02/2016
 */
class RserveService {    

    //Relative paths of R implementations. It's relative because both of them are included
    //with this app (nevermind if the user has an implementation installed)
    private static final String RSCRIPT_RELATIVE_PATH_x64 = "R\\bin\\x64\\Rscript.exe";
    private static final String RSCRIPT_RELATIVE_PATH_x86 = "R\\bin\\i386\\Rscript.exe";
    
    //The R folder (r32 path or r64 path)
    private static File rFolder;

    //The libraries in R that must be installed in order for this app to work
    private static final String[] libraries = new String[]{"Rserve", "XML"};        
    
    
    static void startService() throws IOException, RserveException,
                                      RserveLibraryNotFoundException, 
                                      REnvironmentNotFoundException {        
        prepareREnvironment(); 
        startRserve("--no-save --slave --RS-encoding utf8");
    }
    
    /**
     * Prepares all the variables needed to launch Rserve.exe
     * These variables depend on the operative system, Jewel installation location, etc.
     * @throws IOException
     * @throws RserveLibraryNotFoundException
     * @throws RserveStartupException
     * @throws REnvironmentNotFoundException 
     */
    private static void prepareREnvironment() throws IOException, RserveLibraryNotFoundException, 
                                                     REnvironmentNotFoundException {
        //Only Windows compatibility. No unix-like compatibility so far.
        if (System.getProperty("os.name").contains("Windows")) {    
            
            //os.arch system property may deceive because a 32-bit JVM might be installed
            //on a 64-bit OS (ergo, return 32 when the OS is 64). On a 64-bit Windows 
            //the environment variable "Program files (x86)" will exist, while on a 32-bit
            //Windows it won't exist (instead only Program Files will exist).
            if(System.getenv("ProgramFiles(x86)") != null) 
                rFolder = new File(RSCRIPT_RELATIVE_PATH_x64);
            else
                rFolder = new File(RSCRIPT_RELATIVE_PATH_x86);
            
            //For some reason, the R envionment packed with this app might not be there...
            if( ! rFolder.exists()) {                
                throw new REnvironmentNotFoundException(rFolder.getAbsolutePath());
            }            
            
            //Since the idea is to ship the app with all the configurations done,
            //ALL REQUIRED PACKAGES ARE CONSIDERED PREVIOUSLY INSTALLED. 
            //Every package was installed, from windows cmd, like this:
            //$R32\bin> Rscript.exe -e "install.packages('XML_3.98-1.1.zip', repos = NULL)"
            //And the location of those installed packages is given by
            //$R32\bin> Rscript.exe -e ".libPaths()" (which should return "R32\library")            
            //Anyway, we leave this here just in case.
            /*try {                
                Process install = Runtime.getRuntime().exec(currentDisc + " && " + "\"" + rFolder + "\" -e \"install.packages('Rserve.zip', repos = NULL);\"");
                Process install2 = Runtime.getRuntime().exec(currentDisc + " && " + "\"" + rFolder + "\" -e \"install.packages('XML_3.98-1.1.zip', repos = NULL);\"");
            }
            catch(IOException io) {
                io.printStackTrace();
            }*/
            
            /*for(String str : libraries) {
                 loadLibrary(str);
            }*/
        }
    }    
    
    /*
     * Loads the library into memory, to be used by R scripts. In the process of doing this, it checks if
     * the library is installed (if not, an RserveLibraryNotFoundException will be thrown).
     * METHOD NOT USED, LIBRARIES ARE LOADED WITHIN RExecutor.java
     * @param libName name of the library 
     * @throws RserveLibraryNotFoundException
     * @throws IOException 
     */
    private static void loadLibrary(String libName) throws RserveLibraryNotFoundException, IOException {        
        Process proc = null;
        try {
            String str = "\"" + rFolder.getAbsolutePath() + "\" -e \"library(" + libName +");\"";
            proc = executeCmd(str);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(proc.getErrorStream()))) {
                String line;
                
                //For some bizarre reason, with some packages the errorStream prints what should be
                //printed on an outputStream. Therefore, sometimes we get normal messages instead of
                //error messages. We take care of this by knowing beforehand which are normal messages.
                while ((line = br.readLine()) != null) {
                    //normal message for package XML
                    if(line.equalsIgnoreCase("Loading required package: methods"))
                        continue;
                    throw new RserveLibraryNotFoundException(line);                    
                }
            }
        } catch(IOException ex) {
            throw ex;
        } finally {
            if(proc != null) {
               proc.destroy();
               //proc = null;
            }
        }         
    }
    
    private static Process executeCmd(String command) throws IOException {
        return Runtime.getRuntime().exec(command);
    }
    
    /**
     * Starts the Rserve.exe process, which we'll use to communicate with R
     * @param args Rserve arguments
     * @throws IOException
     * @throws RserveStartupException 
     */
    private static void startRserve(String args) throws IOException {
        Process proc = null;
        StreamHog errorHog = null;
        StreamHog outputHog = null;
        
        try {
            String cmd = "\"" + rFolder.getAbsolutePath() + "\" -e \"library(Rserve); Rserve(args='" + args + "')\"";
            proc = executeCmd(cmd);

            //We cannot write "while((lineOutput = brError.readLine()) != null)"
            //because in Windows if there's no error, the readLine() method never
            //returns, so we would get stucked.
            //Same thing happens with the output: once Rserve has started, it never
            //returns.        
            errorHog = new StreamHog(proc.getErrorStream(), true);
            outputHog = new StreamHog(proc.getInputStream(), false); 
        } catch(IOException ex) {
            throw ex;
        } finally {
            //This would kill the Rserve.exe process
            /*if(proc != null) {
               proc.destroy();
               proc = null;
            }*/
        }  
        
        //We have to try a couple of times, since Rserve() takes a little bit to start
        int attempts = 0;
        while (attempts < 7) {
            try {
                RConnection c = new RConnection();                
                errorHog.interrupt();
                errorHog = null;
                outputHog.interrupt();
                outputHog = null;
                c.close();
                return;
            } catch (Exception e2) {
                Exception ex2 = new Exception("RConnection attempt " + (attempts+1) + " Unsuccessful. ", e2);                
            }            
            try { Thread.sleep(250); } catch (InterruptedException ix) { }
            attempts++;
        }
    }                  
    

    private static class StreamHog extends Thread {

            private InputStream is;
            private volatile boolean rserveReady;	
            private boolean errorStream;

            StreamHog(InputStream is, boolean errorStream) {
                    this.is = is;	
                    this.errorStream = errorStream;
                    start();
            }

            public boolean isRserveReady() {
                    return rserveReady;
            }
            public void run() {
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String line = null;
                    while ( (line = br.readLine()) != null) {                         
                        if(Thread.interrupted()) {
                            Thread.currentThread().interrupt(); 
                            return;
                        }
                        /*if(errorStream) {                           
                            LogThread.writeToLogHandleError("Err.output: " + line);
                        }
                        else
                            LogThread.writeToLogHandleError("Out.output: " + line);*/
                    }
                } 
                catch (IOException e) {
                    /*nothing to do here*/                  
                }
            }
    }
}


