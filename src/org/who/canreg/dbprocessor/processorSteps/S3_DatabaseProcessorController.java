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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPInteger;
import org.rosuda.REngine.REXPString;
import org.rosuda.REngine.RList;
import org.who.canreg.dbprocessor.controllers.LongProcessStrategy;
import org.who.canreg.dbprocessor.controllers.MainController;
import org.who.canreg.dbprocessor.gui.S3_DatabaseProcessingErrorsPanel;
import org.who.canreg.dbprocessor.main.AppContext;
import org.who.canreg.dbprocessor.main.Main;
import org.who.canreg.dbprocessor.r.RExecutor;
import org.who.canreg.dbprocessor.utils.Utils;

/**
 * Step 3: using the variables selected in Step 2, this controller checks that
 * the database has no errors. All errors are output to a list present in 
 * S3_DatabaseProcessingErrorsPanel. 
 * @author Patricio Carranza, Beatriz Carballo
 * @version 1.03.000
 * last update: 18/05/2016
 */
public class S3_DatabaseProcessorController implements LongProcessStrategy {
    
    private S3_DatabaseProcessingErrorsPanel guiPanel;
    private MainController controller;
    private ArrayList<String> headers;    
    private String[][] dataValues = null; 
    
    public S3_DatabaseProcessorController(S3_DatabaseProcessingErrorsPanel guiPanel) {
        this.guiPanel = guiPanel;
    }

    @Override
    public void execute(Object... parameters) throws Exception {
        String updateDateVar = (String) parameters[0];
        String MPCodeVar = (String) parameters[1]; 
        Enumeration<String> rightPanelVars = (Enumeration) parameters[2];
        StringBuilder str = new StringBuilder();
        while(rightPanelVars.hasMoreElements()) {
            str.append("\"").append(rightPanelVars.nextElement()).append("\"");
            if(rightPanelVars.hasMoreElements())    
                str.append(", ");
        }
        
        RExecutor rexec = new RExecutor();        
        headers = new ArrayList<>();    
        dataValues = null; 
        
        rexec.execVoidFunction("MP.data <- subset(mig.data,mig.data[,names(mig.data) %in% c(\"" + MPCodeVar + "\")]!=\"\")");
        rexec.execVoidFunction("non.MP.data <- subset(mig.data,is.na(mig.data[,names(mig.data) %in% c(\"" + MPCodeVar + "\")]) "
                                                    + " | mig.data[,names(mig.data) %in% c(\"" + MPCodeVar + "\")]==\"\")");         
        rexec.execVoidFunction("if ( ! mig.MP.non.MP.codes(MP.data, non.MP.data, \"" + MPCodeVar + "\")){\n" +
"    non.MP.data <- data.frame(rbind(non.MP.data,\n" +
"                                    MP.data[MP.data[,c(\"" + MPCodeVar + "\")] %in%  mig.MP.non.MP.codes(MP.data, non.MP.data, \"" + MPCodeVar + "\"),]),\n" +
"                              stringsAsFactors = FALSE)\n" +
"    MP.data <- MP.data[!(MP.data[,c(\"" + MPCodeVar + "\")] %in%  mig.MP.non.MP.codes(MP.data, non.MP.data, \"" + MPCodeVar + "\")),]\n" +
"  }else{NULL}");                               
        rexec.execVoidFunction("error.MP.cases <- mp.error(MP.data, \n" +
                                         "\"" + updateDateVar + "\",#update date\n" +
                                         "c(" + str.toString() + ")," +
                                         "\"" + MPCodeVar + "\") # multiple primary code");
        REXP rxpResult = rexec.execFunction("error.MP.cases");
        if(rxpResult instanceof REXPString &&
           ((REXPString)rxpResult).asStrings()[0].toLowerCase().contains("there is not any error")) {
            this.guiPanel.refresh();
            this.guiPanel.disableDownloadButton();
        }
        else {
            rexec.execVoidFunction("error.MP.cases <- data.frame(apply(error.MP.cases, 2, as.character), stringsAsFactors = FALSE)");
            rexec.execVoidFunction("error.MP.cases[is.na(error.MP.cases)] <- ''");
            REXP rxp = rexec.execFunction("error.MP.cases[,c(" + str.toString() + ")]");
            RList result = rxp.asList();
            for(Object obj : result.names) {
                headers.add((String) obj);
            }   

            Collection col = result.values();             
            int qtyColumns = (result.names.size() +1);            
            int currentColumnIndex = 0;    
            int currentRowIndex = 0;

            //This loop is each column in the table brought by the R script
            for(Object objRexpstr : col) {
                REXP values = (REXP) objRexpstr;

                if(currentColumnIndex == 0) {
                    int qtyRows = 0;
                    if(values instanceof REXPString)
                        qtyRows = values.asStrings().length;
                    else if(values instanceof REXPInteger)
                        qtyRows = values.asIntegers().length;
                    dataValues = new String[qtyRows][qtyColumns];                    
                }                    

                //This loop is each value in a column
                if(values instanceof REXPString) {
                    for(String value : values.asStrings()) {
                        dataValues[currentRowIndex][currentColumnIndex] = value;
                        currentRowIndex++;
                    }
                } else if(values instanceof REXPInteger) {
                    for(Integer value : values.asIntegers()) {
                        dataValues[currentRowIndex][currentColumnIndex] = value.toString();
                        currentRowIndex++;
                    }
                }
                currentColumnIndex++;
                currentRowIndex = 0;
            }

            this.guiPanel.loadTable(dataValues, headers.toArray(new String[0]));
        }
        this.guiPanel.setVisible(true);
    }

    @Override
    public void afterExecute(MainController controller) {
        this.guiPanel.setDatabaseProcessorController(this);
        this.controller = controller;
    }

    @Override
    public String getDescription() {
        return "Database Processing and error detection";
    }

    public void saveErrorsToCSV() {
        JFileChooser chooser = new JFileChooser(AppContext.currentAppPath);  
        //chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setSelectedFile(new File(AppContext.currentAppPath, "error file.csv"));
        chooser.setDialogTitle("Choose a folder to save the error file");
        int result = chooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            AppContext.currentAppPath = chooser.getCurrentDirectory();            
            Main.startLoading();         
            Utils.startTimer();        
            SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>(){
                @Override
                protected Void doInBackground() {                        
                    try {
                        File destination = chooser.getSelectedFile();                        
                        RExecutor rexec = new RExecutor();
                        rexec.execVoidFunction("write.csv(error.MP.cases, \"" + Utils.fixPath(destination.getAbsolutePath()) + "\", row.names=FALSE)");
                    }
                    catch(Exception ex) {                    
                        ex.printStackTrace();
                    }

                    return null;
                }
            };

            mySwingWorker.addPropertyChangeListener(new PropertyChangeListener() {
               @Override
               public void propertyChange(PropertyChangeEvent evt) {
                  if (evt.getPropertyName().equals("state")) {
                     if (evt.getNewValue() == SwingWorker.StateValue.DONE) {
                         Main.stopLoading();                     
                         System.out.println("Error printo to csv DURATION: "
                                            + Utils.endTimer() + "ms");                  
                     }
                  }
               }
            });

            mySwingWorker.execute();            
        }
    }

    public void btnContinueAction() {
        if(this.headers.size() > 0) {            
            int result = JOptionPane.showConfirmDialog(null, "If you decide to continue, the patient data to be\n"
                                                + "used will be the one with the newest Update date.\n\n"
                                                + "Are you sure you want to continue???", 
                                        "WARNING!!!", JOptionPane.OK_CANCEL_OPTION);
            if(result == JOptionPane.OK_OPTION) {
                this.controller.H_databaseProcessingCompleted();
            }
        } else
            this.controller.H_databaseProcessingCompleted();
    }
    
}
