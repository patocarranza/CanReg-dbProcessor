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
package org.who.canreg.dbprocessor.controllers;

import java.io.File;
import org.who.canreg.dbprocessor.gui.DatabasePanel;
import org.who.canreg.dbprocessor.r.RExecutor;
import org.who.canreg.dbprocessor.utils.Utils;

/**
 *
 * @author patri_000
 */
public class DatabaseMigrationPreparatorController implements LongProcessStrategy {
    
    private final DatabasePanel guiPanel;
    
    public DatabaseMigrationPreparatorController(DatabasePanel guiPanel) {
        this.guiPanel = guiPanel;
    }

    @Override
    public void execute(Object... parameters) throws Exception {
        File file = (File) parameters[0];        
        String filePath = Utils.fixPath(file.getAbsolutePath());
        RExecutor rexec = new RExecutor();              
        rexec.execVoidFunction("mig.data <- read.csv(\"" + filePath + "\",stringsAsFactors = FALSE)");
        rexec.execVoidFunction("patient.data <- tables.cr5(doc.data,\"Patient\")");
        rexec.execVoidFunction("tumour.data <- tables.cr5(doc.data,\"Tumour\")");
        rexec.execVoidFunction("source.data <- tables.cr5(doc.data,\"Source\")");
        rexec.execVoidFunction("doc.data <- var.length(doc.data, dic.data)");        
        this.guiPanel.setDatabaseFileLocation(filePath);        
    }

    @Override
    public void afterExecute(MainController controller) {
        controller.databaseSelectionCompleted();
    }

    @Override
    public String getDescription() {
        return "DATABASE TO MIGRATE";
    }
    
}
