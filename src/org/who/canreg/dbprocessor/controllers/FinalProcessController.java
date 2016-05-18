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
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.rosuda.REngine.REXPLogical;
import org.rosuda.REngine.REXPString;
import org.who.canreg.dbprocessor.main.AppContext;
import org.who.canreg.dbprocessor.r.RExecutor;
import org.who.canreg.dbprocessor.utils.Utils;

/**
 *
 * @author patri_000
 */
public class FinalProcessController implements LongProcessStrategy {

    @Override
    public void execute(Object... parameters) throws Exception {
        int ammountOfSources = (Integer) parameters[0];
        String[] labels = (String[]) parameters[1];
        ArrayList<String[]> selectedValuesList = (ArrayList) parameters[2];        
        String MPSequence = (String) parameters[3];
        String regNumber = (String) parameters[4];
        
        RExecutor rexec = new RExecutor(); 
        String[] labelTypes = ((REXPString) rexec.execFunction("source.var.data$short_name")).asStrings();
        StringBuilder strSelectedValues = new StringBuilder();
        StringBuilder strLabelTypes = new StringBuilder();
        for(int i = 1; i <= ammountOfSources; i++) {
            strSelectedValues.append(i).append(", ");
            strLabelTypes.append(i).append(", ");
                
            String[] selectedValues = selectedValuesList.get((i-1));
            for(int j = 0; j < selectedValues.length; j++) {
                strSelectedValues.append("\"").append(selectedValues[j]).append("\"");                
                strLabelTypes.append("\"").append(labelTypes[j]).append("\"");
                if(j == (selectedValues.length-1) && i == ammountOfSources) {} 
                else {
                    strSelectedValues.append(", ");
                    strLabelTypes.append(", ");
                }
            }            
        }
        
        rexec.execVoidFunction("source.data <- sources(source.data, non.MP.data, MP.data, " + ammountOfSources + ", "
                               + "c(" + strSelectedValues.toString() + "), c(" + strLabelTypes.toString() + "), "
                               + "\"" + MPSequence + "\", \"" + regNumber + "\")");
        rexec.execVoidFunction("patient.dic <- doc.data$short_name[doc.data$variable_type==\"Dict\" & doc.data$table==\"Patient\"]");
        rexec.execVoidFunction("source.dic <- doc.data$short_name[doc.data$variable_type==\"Dict\" & doc.data$table==\"Source\"]");
        rexec.execVoidFunction("tumour.dic <- doc.data$short_name[doc.data$variable_type==\"Dict\" & doc.data$table==\"Tumour\"]");
        rexec.execVoidFunction("patient.data <- leading.zeros(patient.data, doc.data, patient.dic)");
        rexec.execVoidFunction("source.data <- leading.zeros(source.data, doc.data, source.dic)");
        rexec.execVoidFunction("tumour.data <- leading.zeros(tumour.data, doc.data, tumour.dic)");
        
        JFileChooser chooser = new JFileChooser(AppContext.currentAppPath);  
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //chooser.setSelectedFile(AppContext.currentAppPath);
        chooser.setDialogTitle("Choose a folder to save the 3 datasets");
        int result = chooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File destination = chooser.getSelectedFile();
            AppContext.currentAppPath = chooser.getCurrentDirectory();
            boolean finalResult = ((REXPLogical) rexec.execFunction("check.save(total.patients, MP.data , non.MP.data, " +
                                                                   "patient.data, tumour.data, source.data, " +
                                                                   "\"" + Utils.fixPath(destination.getAbsolutePath()) + "\")")).isTRUE()[0];
            if(finalResult)
                JOptionPane.showMessageDialog(null, "The files have been successfully created in the folder \n"
                                                    + destination.getAbsolutePath(), "SUCCESS!!", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(null, "There was an error creating the files in the folder \n"
                                                    + destination.getAbsolutePath(), "ERROR", JOptionPane.ERROR_MESSAGE);            
        }
    }

    @Override
    public void afterExecute(MainController controller) {
        //We're done buddy :)
    }

    @Override
    public String getDescription() {
        return "Final processing of files";
    }
    
}
