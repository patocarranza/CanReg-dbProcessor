/*
 * Copyright (C) 2016 Beatriz Carballo, Patricio Carranza
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
package org.who.canreg.dbprocessor.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import org.apache.commons.io.FileUtils;
import org.who.canreg.dbprocessor.main.AppContext;


/**
 * Class that handles a log file creation and use. This log is used to track
 * errors.
 * @author Patricio Carranza, Beatriz Carballo
 * @version 1.06.001
 * last update: 02/13/2016
 */
public class LogManager {
    
    private static LogManager log;
    private static File debugLogFile/*, runtimeExceptionsLogFile*/;    
    private static PrintStream debug;    


    private LogManager() throws IOException {			
            //Create the "Logs" folder (or locate it, if it's existent).
            debugLogFile = new File("Logs");
            //runtimeExceptionsLogFile = new File("Logs");
            
            //We delete the directory if there are more than 50 log files inside
            if(debugLogFile.exists()) {
                if(debugLogFile.list().length >= 50) 
                    FileUtils.cleanDirectory(debugLogFile);                
            }            
            else 
                debugLogFile.mkdirs();
            
            debugLogFile = new File(debugLogFile, "Log " + Utils.getCurrentTime2() +  ".txt");   
            debug = new PrintStream(debugLogFile);
            if(AppContext.productionBuild)
                System.setErr(debug);                                      
                        
            //************DEPRECATED*************//
            //runtimeExceptionsLogFile = new File(runtimeExceptionsLogFile, "Error Log " + Utils.getCurrentTime2() +  ".txt");
            //We log unchecked/runtime errors in a file
            //System.setErr(new PrintStream(runtimeExceptionsLogFile));    	
            //************DEPRECATED*************//                        
    }
    
    private static void createLog() {
        if(log == null) {
            try {
                log = new LogManager();
                logInfo(LogManager.class, "++++++ LOG CREATED SUCCESSFULLY ++++++");
            }
            catch(IOException ex) { 
                System.out.println("Problem Creating Log\n" + ex.getMessage());
            }
        }
    }

    public static LogManager getInstance() {   
        if(log == null)             
            createLog();
        return log;        
    }    
    
    
    /**
     * Writes the exception.printStackTrace() into the log file, and into the console.
     * @param ex the exception to be written into the log file
     * @throws IOException 
     */
    private static synchronized void printException(Exception ex) throws IOException {
        if(log == null)             
            createLog();
        
        if(ex != null) {
            //Goes directly to System.err, which has been re-routed to a log file.
            ex.printStackTrace();
            
        }
        
        //This was the previous implementation. Very interesting, should not be erased =).
        /*PrintStream ps = null;
        File tempFile = new File("Logs\\tempFile.txt");
        tempFile.deleteOnExit();
        tempFile.createNewFile();
        try {
            ps = new PrintStream(tempFile);
            if(ex == null)
                ps.print("\nUNCLASSIFIED/NULL EXCEPTION");
            else {
                ex.printStackTrace(ps);
                ex.printStackTrace();
            }
            
            String line = null;
            BufferedReader br = new BufferedReader(new FileReader(tempFile));
            while((line = br.readLine()) != null ) {
                logHandleError(line);
            }
            br.close();           
        } catch(FileNotFoundException ex1) {
            System.out.println("Log file not found\n");                                
        } finally {
            if(ps != null)
                ps.close();
        } */      
    }
    
    /**
     * Logs an exception, including complete class name (includes package name),
     * the method in which the exception was raised and the message inside the
     * exception (therefore, manual setting of the message of the exception is
     * highly recommended).  This method should only be used when the call to this
     * method IS made in the place where the problem occurred.
     * @param clazz
     * @param ex
     */
    public static synchronized void logError(Class clazz, Exception ex) {
        writeToLog(clazz, ex, 2);
    }
    
    /**
     * Logs an exception, including complete class name (includes package name),
     * the method in which the exception was raised and the message inside the
     * exception (therefore, manual setting of the message of the exception is
     * highly recommended). This method should only be used when the call to this
     * method is NOT made in the place where the problem occurred. Examples:
     * used when exceptions are logged by a class error manager (like handleError()
     * in implementers of LoadReportObserver) or by an intermediate method (like
     * Dialog.errorDialog()).
     * @param clazz
     * @param ex
     */
    public static synchronized void logHandleError(Class clazz, Exception ex) {
        writeToLog(clazz, ex, 3);
    }
            
    
    public static synchronized void logInfo(Class clazz, String msg) {        
        if(log == null)             
            createLog();
        if(log != null) {
            try {
                if(msg == null) msg = "No info message";
                StringBuilder build = new StringBuilder();                
                build.append(Utils.getCurrentTime()).append("[INFO]: ")
                                                    .append(clazz.getName())
                                                    .append(".")
                                                    .append(Utils.getMethodName(1))
                                                    .append("() - ")
                                                    .append(msg);                           
                debug.println(build.toString());
                System.out.println(build.toString());            
            }
            catch(Exception ex) {
                System.out.println("Problem Writing In Log\n" + ex.getMessage());                               
            }
        }
    }
    
    private static void writeToLog(Class clazz, Exception ex, int methodDistance) {
        if(log == null)             
            createLog();        
        if(log != null) {
            try {
                StringBuilder build = new StringBuilder();
                String msg = "No error message";
                if(ex != null && ex.getMessage() != null) msg = ex.getMessage();
                build.append(Utils.getCurrentTime()).append("[ERROR]: ")
                                                    .append(clazz.getCanonicalName())
                                                    .append(".")
                                                    .append(Utils.getMethodName(methodDistance))
                                                    .append("() - ")
                                                    .append(msg);                
                debug.println(build.toString());
                System.out.println(build.toString());
                if(ex != null)
                    printException(ex);                
            } catch(IOException ex1) {
                System.out.println("Problem Writing In Log\n" + ex1.getMessage());                                
            }
        }
    }          
}
