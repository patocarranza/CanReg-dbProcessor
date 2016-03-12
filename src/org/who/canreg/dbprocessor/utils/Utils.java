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

import java.awt.Dimension;
import java.io.*;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Miscelaneous methods.
 * @author Patricio Carranza, Beatriz Carballo
 * @version 1.00.000
 * Last update: 23/02/2016
 */
public class Utils {
    
    public static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();      
           
    public static String fixPath(String path) {
        if(path == null || path.isEmpty())
            throw new NullPointerException("File path is null. File must be missing.");
        return path.replace(File.separator, "//");
    }
    
    public static String getCurrentYear() {        
        return new SimpleDateFormat("yyyy").format(new Date());
    }
    
    public static String getCurrentTime() {        
        return new SimpleDateFormat("dd/MM/yyyy" + " HH:mm:ss").format(new Date());
    }
    
    public static String getCurrentTime2() {        
        return new SimpleDateFormat("dd-MM-yyyy" + " HH_mm_ss").format(new Date());
    }
    
   
    public static String getFileExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
     
    public static String getTextFromFile(String filePath)
                throws IOException {
        BufferedReader reader = null;
        StringBuilder result = new StringBuilder();
        String line = null;
        
        //Some methods could send a filePath with :, but most OS don't allow
        //that character in a file name.
        if(filePath.contains(":"))
            filePath = filePath.replace(":", "_");
        
        try {            
           reader = new BufferedReader(new FileReader(filePath));
           
           while((line = reader.readLine()) != null) {
               result.append(line);               
           }
        } finally {
            if(reader != null) {
                try { reader.close(); }
                catch(IOException ex) {
                    System.out.println("File not closed");                    
                }
            }                
        }
                
        return result.toString();
    }
   
    /////////////////////////////////////////////////////////////////////////////////////////////////
    //******SPEED TEST TOOLS********
    /////////////////////////////////////////////////////////////////////////////////////////////////
    private static long nanoStart;
    private static LinkedList<Long> records;
        
    /**
     * When we want to see the performance of a set of instructions, the 
     * best thing to do is make a "record" for each important instruction.
     * That way we can not only know how much all of the instructions took
     * but also how much each instruction took, and then we can determine 
     * where's a bottleneck.
     * The procedure would be:
     *      Utils.startRecords();
     *      Utils.startTimer();
     *      **small set of instructions**
     *      long smallSetPerformance = Utils.endTimer();
     *      Utils.startTimer();
     *      **small set of instructions**
     *      ...
     *      ...
     *      long totalTimeSinceStartRecords = Utils.getResult();
     */
    public static void startRecords() {
        records = new LinkedList<>();
    }
    
    /**
     * Sets the timer to zero, in order to start a new speed measure
     */
    public static void startTimer() {
        nanoStart = System.nanoTime();
    }
    
    /**
     * Time in milliseconds since the last startTimer().
     * It also saves such time to a list of times (that list is 
     * initialized with startRecords()).
     * @return time, in milliseconds, since the last startTimer().
     */
    public static long endTimer() {
        long result = (System.nanoTime() - nanoStart) / 1000000;
        if(records != null)
            records.add(result);
        return result;        
    }
    
    /**
     * Returns the total time lapsed since the last startRecords().
     * @return 
     */
    public static long getResult() {
        endTimer();
        long result = 0;
        for(Long lo : records)
            result += lo;
        
        startRecords();
        return result;
    }    
                    
    
    /**
     * Kills RServe.exe processes that have been left orphans from
     * previous Jewel executions. Orphan processes are left alive when
     * Jewel is closed from Task Manager. They don't interfere, but they
     * use resources.
     */
    public static void killOrphanRServeProcesses() {        
        Process p = null;
        try {
            //Obtains the tasklist Windows process. Basically the Task Manager, and kills
            //all instances of Rserve.exe found
            p = Runtime.getRuntime().exec(System.getenv("windir") +"\\system32\\taskkill.exe /F /IM Rserve.exe");
            //Sleep because otherwise it won't execute the process... God knows why...
            Thread.sleep(500);
        } catch(IOException | InterruptedException ex) {            
        } finally {
             if(p != null)
                 p.destroy();            
        }         
    }

    /**
     * Obtains the Process Object according to the way they it is written in  
     * Microsoft Windows's Task Manager. 
     * THIS METHOD IS NOT PLATFORM INDEPENDENT (WINDOWS IMPLEMENTATION ONLY).
     * Very likely might be several PIDs with
     * the same name, but with different PID indicates that they are different
     * instances (like the several svchost.exe in the Task Manager).
     * @param processName name of the process as written in the Task Manager.
     * @return Processes with the same name running. Integer is the PID, and String is the
     * complete Task Manager line output.
     */
    private static HashMap<Integer, String> getProcessByName(String processName) {
        //Processes with the same name running. Integer is the PID, and String is the
        //complete Task Manager line output. Very likely might be several PIDs with
        //the same String, but with different PID indicates that they are different
        //instances (like the several svchost.exe in the Task Manager)
        HashMap<Integer, String> processInstances = new HashMap<>(20);        
        String line;        
        Process p = null;
        BufferedReader input = null;
        //pidIndex is platform-specific. Read the System.out.println(line);
        //at the beginning of the while loop to understand.
        int pidIndex = (processName.split("\\W+").length) + 1;
        
        try {
            //obtains the tasklist Windows process. Basically the Task Manager
            p = Runtime.getRuntime().exec(System.getenv("windir") +"\\system32\\tasklist.exe");
            
            //We're going to read the tasklist.
            input =  new BufferedReader(new InputStreamReader(p.getInputStream()));
            //each line contains the name of the process associated to a program
            //(for example: winword.exe for Word), PID, RAM consumption, etc.
            while ((line = input.readLine()) != null) {     
                //System.out.println(line);
                  
                //Separate each line in words.
                String[] tokens = line.split("\\W+");
                
                //Let's see if the current line refers to a process instance
                if(line.contains(processName)) {
                    
                    int PID = Integer.parseInt(tokens[pidIndex]);
                    processInstances.put(PID, line);
                }                   
            }                  
        } catch (IOException ex) {
            IOException iex = new IOException("Processes cannot be read from tasklist.exe", ex);                     
        } finally {
            try {
                input.close();
            } catch (IOException ex) {
                //Not worth it...
            }
            p.destroy();            
        }      
        
        return processInstances; 
    }      
    
    //********GET CURRENT EXECUTING METHOD NAME********
    //obtained from http://stackoverflow.com/questions/442747/getting-the-name-of-the-current-executing-method/8592871
    // save it static to have it available on every call
    private static Method m;
    static {
        try {
            m = Throwable.class.getDeclaredMethod("getStackTraceElement", int.class);
            m.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getMethodName(final int depth) {
        try {
            StackTraceElement element = (StackTraceElement) m.invoke(new Throwable(), depth + 1);
            return element.getMethodName();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
