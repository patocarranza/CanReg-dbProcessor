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

/**
 * The R environment was not found.
 * @author Patricio Carranza, Beatriz Carballo
 * @version 0.1.2
 * last update: 07/11/2014
 */
public class REnvironmentNotFoundException extends Exception {
    
    private String folder;
    
    public REnvironmentNotFoundException(String msg) {
        this.folder = msg;
    }
    
    @Override
    public String getMessage() {
        return "The folder " + folder + " does NOT EXIST";
    }
}