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

import java.util.ArrayList;
import org.rosuda.REngine.REXPString;
import org.who.canreg.dbprocessor.gui.VariablesSelectionPanel;
import org.who.canreg.dbprocessor.r.RExecutor;

/**
 *
 * @author patri_000
 */
public class VariableSelectionController implements LongProcessStrategy {
    
    private VariablesSelectionPanel guiPanel;
    
    
    /**
     * This controller needs to be instantiated as part of an afterExecute() !!!!
     * @param guiPanel 
     */
    public VariableSelectionController(VariablesSelectionPanel guiPanel) {
        this.guiPanel = guiPanel;        
    }

    @Override
    public void execute(Object... parameters) throws Exception {
        RExecutor rexec = new RExecutor();              
        String defaultUpdateDate = ((REXPString) rexec.execFunction("get.var(doc.data,\"update\")")).asStrings()[0];
        String defaultMPCode = ((REXPString) rexec.execFunction("get.var(doc.data,\"mp\")")).asStrings()[0];        
        String[] variables = ((REXPString) rexec.execFunction("names(mig.data)")).asStrings();     
        this.guiPanel.setUpdateDateCombo(variables, defaultUpdateDate);
        this.guiPanel.setMPCodeComboValues(variables, defaultMPCode);
        this.guiPanel.setLeftListValues(variables);
        this.guiPanel.setVisible(true);
    }

    @Override
    public void afterExecute(MainController controller) {
        this.guiPanel.setMainController(controller);
    }

    @Override
    public String getDescription() {
        return "Database Patient Important Variables Preparation";
    }
    
}
