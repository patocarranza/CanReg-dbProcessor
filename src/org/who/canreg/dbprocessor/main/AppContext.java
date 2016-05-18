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
package org.who.canreg.dbprocessor.main;

import java.io.File;
import java.nio.file.Paths;

/**
 * Global Constants for this app.
 * @author Patricio Carranza, Beatriz Carballo
 * @version 1.00.000
 * last update: 23/02/2016
 */
public class AppContext {                
    
    public static final boolean productionBuild = false;    
    public static final String appVersion = "Ver: 1.01.000";
        
    public static final String techSupportMail = "beatrizcarballo@gmail.com";          
    public static final String appName = "CanReg Database Processor";                  
    
    public static final File currentAppPath = Paths.get("").toAbsolutePath().toFile();
    //public static final File programData = new File(System.getenv("ProgramData"));      
    //public static final File reportsTempFolder = new File(programData, "CanReg Database Processor Temp files");       
}
