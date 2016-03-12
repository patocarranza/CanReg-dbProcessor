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
package org.who.canreg.dbprocessor.r;

import java.io.IOException;
import java.net.SocketException;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.*;

/**
 * Class in charge of executing R commands (scripts or simple lines). 
 * This is the only class that should be used to communicate with R.
 * @author Patricio Carranza, Beatriz Carballo
 * @version 1.02.001
 * Last update: 11/19/2015
 */
public class RExecutor {
    
    //Just one connection, R is not multithreaded by itself so far.
    private static RConnection RConn;       
        
    /**
     * Default constructor. Every instance of RExecutor will share the same
     * Rserve Connection, so creating multiple RExecutor instances is not a problem.
     * Windows does not allow multiple Rserve connections, Unix-like does but Rserve Connection
     * concurrency is not designed in this app.
     * @throws java.io.IOException
     * @throws org.rosuda.REngine.Rserve.RserveException
     * @throws com.jewel.core.exceptions.RserveStartupException
     * @throws com.jewel.core.exceptions.RserveLibraryNotFoundException
     * @throws com.jewel.core.exceptions.REnvironmentNotFoundException     
     */
    public RExecutor() throws IOException, RserveException, 
                              RserveLibraryNotFoundException,
                              REnvironmentNotFoundException,
                              Exception {                    
        try {           
            if(RConn == null || ! isConnected()) {                  
                RserveService.startService();            
                RConn = new RConnection();    
                RConn.setStringEncoding("utf8");
                loadPackages();
            } 
        }
        //We give it a second try... if not, exceptions will be thrown.
        catch(Exception ex) {
             if(RConn == null || ! isConnected()) {                  
                RserveService.startService();            
                RConn = new RConnection(); 
                RConn.setStringEncoding("utf8");
                loadPackages();
            }    
        }
    }           
    
    private void loadPackages() throws RserveException, SocketException {
        execFunction("require(splitstackshape)");
        execFunction("require(XML)");
        execFunction("require(stringr)");
        execFunction("require(plyr)");      
        execFunction("require(migCR5)");
    }
    
    public final boolean isConnected() {
        boolean connected = false;
        if(RConn != null) {
            try {
                REXP fx = execFunction("1 + 2");
                if(fx != null)
                    connected = true;
            } catch(Exception ex) {
                connected = false;
            }
        }
        return connected;
    }
    
    final void loadScript(String scriptPath) throws RserveException, SocketException {
        RConn.eval("source(\"" + scriptPath + "\")");           
    }
    
    public REXP execFunction(String function) throws RserveException, SocketException {        
        return RConn.eval(function);
    }
    
    public void execVoidFunction(String function) throws RserveException, SocketException {        
        RConn.voidEval(function);
    }
    
    public void assign(String variableName, REXP variable) throws RserveException {
        RConn.assign(variableName, variable);        
    }
    
    public static void shutdownRserve() throws SocketException {
        if(RConn != null) {
            try {
                RConn.shutdown();
            } catch(RserveException ex) {
                ex.printStackTrace();
            }
        }        
    }
}
