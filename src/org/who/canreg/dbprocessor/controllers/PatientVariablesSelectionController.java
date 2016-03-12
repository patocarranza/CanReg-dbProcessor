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

import org.rosuda.REngine.REXPString;
import org.who.canreg.dbprocessor.gui.PatientVariablesSelectionPanel;
import org.who.canreg.dbprocessor.r.RExecutor;

/**
 *
 * @author patri_000
 */
public class PatientVariablesSelectionController implements LongProcessStrategy {
    
    private PatientVariablesSelectionPanel guiPanel;
        
    public PatientVariablesSelectionController(PatientVariablesSelectionPanel guiPanel) {
        this.guiPanel = guiPanel;        
    }

    @Override
    public void execute(Object... parameters) throws Exception {
        RExecutor rexec = new RExecutor();              
        rexec.execVoidFunction(" non.MP.data <- data.frame(rbind(non.MP.data,\n" 
                                + "MP.data[MP.data[,c(\"" + ((String)parameters[0]) + "\")] %in%  "
                                + "mig.MP.non.MP.codes(MP.data, non.MP.data, \"" + (String)parameters[0] + "\"),]),\n" 
                                + "stringsAsFactors = FALSE)");
        rexec.execVoidFunction("MP.data <- MP.data[!(MP.data[,c(\"" + ((String)parameters[0]) + "\")] %in% "
                                + "mig.MP.non.MP.codes(MP.data, non.MP.data, \"" + ((String)parameters[0]) + "\")),]");
        String regNum = ((REXPString) rexec.execFunction("get.var(doc.data,\"regnumber\")")).asStrings()[0];
        String MPTotal = ((REXPString) rexec.execFunction("get.var(doc.data,\"pmtot\")")).asStrings()[0];
        String MPSeq = ((REXPString) rexec.execFunction("get.var(doc.data,\"pmseq\")")).asStrings()[0];
        String[] variables = ((REXPString) rexec.execFunction("names(mig.data)")).asStrings();
        
        this.guiPanel.setRegistryNumberComboValues(variables, regNum);
        this.guiPanel.setMPTotalComboValues(variables, MPTotal);   
        this.guiPanel.setMPSequenceComboValues(variables, MPSeq);
        this.guiPanel.setVisible(true);
    }

    @Override
    public void afterExecute(MainController controller) {
        this.guiPanel.setMainController(controller);
    }

    @Override
    public String getDescription() {
        return "Registry number and multiple primary total setup";
    }
    
}
