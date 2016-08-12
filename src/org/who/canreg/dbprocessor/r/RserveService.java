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
import org.who.canreg.dbprocessor.utils.Utils;

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
    private static final String RSCRIPT_LIB_PATH = "R\\library";
    
    //The R folder (r32 path or r64 path)
    private static File rFolder; 
    
    
    static void startService() throws IOException, RserveException,
                                      RserveLibraryNotFoundException, 
                                      REnvironmentNotFoundException {        
        prepareREnvironment(); 
        startRserve("--vanilla --slave --RS-encoding utf8");
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
            File libraryFolder = new File(RSCRIPT_LIB_PATH);
            
            //Here is where we launch R and Rserve.
            //The format of this command is this:
            // "absolute\path\to\RScript.exe" -e ".libPaths(new='fixed//path//to//jewelapp//R//library'); .libPaths(); library(Rserve); Rserve(args='')"
            //This cmd command includes the next actions:
            //- Execution of an RScript.exe environment in which we launch an instance
            //  of RServe.exe. This RServe instance is in charge of the R environment, 
            //  so it's important that we launch that RServe from the correct R folder
            //  (if there's more than 1 R folder in the computer, is very likely all
            //   are included in the libPaths() and we might end up executing the RServe from
            //   a different folder than the R contained in the Jewel app).
            //- To make sure we execute the RServe found in the Jewel app, we set libPaths()
            //  with the current path of the R/library folder inside the Jewel app.
            //- .libPaths() creates an output string that indicates the R library that is being
            //  considered ONLY for this execution of RScript.exe (this output should ALWAYS
            //  be ONLY the Jewel app R library folder).
            //- By the time the action 'library(Rserve)' gets called there's no doubt that 
            //  the Rserve that's being launched is the one inside the Jewel app folder.            
            String cmd = "\"" + rFolder.getAbsolutePath() + "\" -e" +  
                         "\".libPaths(new='" + Utils.fixPath(libraryFolder.getAbsolutePath()) + "'); " + 
                         ".libPaths(); library(Rserve); Rserve(args='" + args + "')\"";            
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
                        if(errorStream) 
                            System.out.println("error: " + line);
                        else
                            System.out.println("output: " + line);
                    }
                } 
                catch (IOException e) {
                    /*nothing to do here*/                  
                }
            }
    }
}


