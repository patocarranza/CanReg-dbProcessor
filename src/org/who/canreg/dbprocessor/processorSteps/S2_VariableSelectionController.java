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
package org.who.canreg.dbprocessor.processorSteps;

import java.util.ArrayList;
import org.rosuda.REngine.REXPString;
import org.who.canreg.dbprocessor.controllers.LongProcessStrategy;
import org.who.canreg.dbprocessor.controllers.MainController;
import org.who.canreg.dbprocessor.gui.S2_VariablesSelectionPanel;
import org.who.canreg.dbprocessor.r.RExecutor;

/**
 * Step 2: a database file has been approved by 
 * S1_DatabaseMigrationPreparatorController. Now the user is presented with
 * variables and she/he must select the ones that have to be compared in order
 * to continue to Step 3.
 * @author Patricio Carranza, Beatriz Carballo
 * @version 1.01.000
 * last update: 18/05/2016
 */
public class S2_VariableSelectionController implements LongProcessStrategy {
    
    private S2_VariablesSelectionPanel guiPanel;
    
    
    /**
     * This controller needs to be instantiated as part of an afterExecute() !!!!
     * @param guiPanel 
     */
    public S2_VariableSelectionController(S2_VariablesSelectionPanel guiPanel) {
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
