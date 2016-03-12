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

import org.rosuda.REngine.REXPInteger;
import org.rosuda.REngine.REXPString;
import org.who.canreg.dbprocessor.gui.SourcesSelectionPanel;
import org.who.canreg.dbprocessor.r.RExecutor;

/**
 *
 * @author patri_000
 */
public class SourcesSelectionController implements LongProcessStrategy {

    private SourcesSelectionPanel guiPanel;
    
    public SourcesSelectionController(SourcesSelectionPanel guiPanel) {
        this.guiPanel = guiPanel;
    }
    
    @Override
    public void execute(Object... parameters) throws Exception {
        String MPCodeVar = (String) parameters[0];
        String UpdateDateVar = (String) parameters[1];
        String regNumVar = (String) parameters[2];
        String MPTotalVar = (String) parameters[3];
        String MPSequence = (String) parameters[4];
        
        RExecutor rexec = new RExecutor();              
        rexec.execVoidFunction("MP.data[,c(\"" + regNumVar +"\")] <- expanded.cases(MP.data,\"" + MPCodeVar + 
                                                                    "\",\"" + MPTotalVar + "\",\"" + regNumVar + "\")"
                                                                    + "[,c(\"" + regNumVar + "\")]");
                                           
        rexec.execVoidFunction("MPTot.data <- as.data.frame.table(table(MP.data[,c(\"" + MPTotalVar + "\")]),stringsAsFactors = FALSE)");
        rexec.execVoidFunction("names(MPTot.data)[1] <- \"PMTot\"");
        rexec.execVoidFunction("MPTot.data$PMTot <- as.numeric(MPTot.data$PMTot)");
        rexec.execVoidFunction("total.patients <- sum(MPTot.data$Freq/MPTot.data$PMTot) + nrow(non.MP.data)");
        rexec.execVoidFunction("patient.data <- patient(patient.data, non.MP.data, MP.data, \"" + UpdateDateVar + "\","
                                                        + "\"" + MPCodeVar + "\",\"" + regNumVar + "\")");
        rexec.execVoidFunction("tumour.data <- tumour(tumour.data, non.MP.data, MP.data, \"" + MPSequence + "\",\"" + regNumVar + "\")");
        rexec.execVoidFunction("source.var.data <- data.frame(doc.data[doc.data$table==\"Source\",c(\"short_name\",\"full_name\")],\n" +
"                                stringsAsFactors = FALSE)");
        rexec.execVoidFunction("source.var.data <- source.var.data[!(source.var.data$short_name %in% c(\"TumourIDSourceTable\",\"SourceRecordID\")),]");
        String[] labels = ((REXPString) rexec.execFunction("source.var.data$full_name")).asStrings();
        int combosAmmount = ((REXPInteger) rexec.execFunction(" nrow(source.var.data)")).asIntegers()[0];
        String[] combosValues = ((REXPString) rexec.execFunction("names(mig.data)")).asStrings();
        
        this.guiPanel.setAmmountOfVisibleCombos(combosAmmount);
        this.guiPanel.setLabels(labels);
        this.guiPanel.setCombosValues(combosValues);
        this.guiPanel.setVisible(true);
    }

    @Override
    public void afterExecute(MainController controller) {
        this.guiPanel.setMainController(controller);
    }

    @Override
    public String getDescription() {
        return "Sources initialization";
    }
    
}
