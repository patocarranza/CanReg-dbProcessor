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

import org.who.canreg.dbprocessor.processorSteps.S4_PatientVariablesSelectionController;
import org.who.canreg.dbprocessor.processorSteps.S3_DatabaseProcessorController;
import org.who.canreg.dbprocessor.processorSteps.S2_VariableSelectionController;
import org.who.canreg.dbprocessor.processorSteps.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.who.canreg.dbprocessor.gui.RootPanel;
import org.who.canreg.dbprocessor.main.AppContext;
import org.who.canreg.dbprocessor.main.Main;
import org.who.canreg.dbprocessor.r.RExecutor;
import org.who.canreg.dbprocessor.utils.DialogForm;
import org.who.canreg.dbprocessor.utils.Utils;

/**
 * Main controller. Initializes and configures controllers.
 * @author Patricio Carranza, Beatriz Carballo
 * @version 1.01.000
 * last update: 18/05/2016
 */
public class MainController {
    
    private final RootPanel rootPanel;        
    private String MPCodeVar;
    private String UpdateDateVar;
    private String MPSequenceVar;
    private String registryNumberVar;
    
    public MainController(RootPanel rootPanel) throws Exception {
        this.rootPanel = rootPanel;        
        //We start the RServe here. If this suceeds, there shouldn't be any problems
        //regarding R during the life cycle of the app.
        RExecutor rexec = new RExecutor();
    }

    public void A_selectDatabaseDefinition() {
        this.rootPanel.refreshPanelsContent();
        this.rootPanel.hideAllPanels();
        final File dbDef = this.B_showDatabaseDefinitionFileSelection();
        if(dbDef != null) {
            this.longProcess(new S0_DatabaseDefinitionController(this.rootPanel.getDatabaseDefinitionPanel()), dbDef);
        }            
    }
    
    private File B_showDatabaseDefinitionFileSelection() {
        File fileSelected = null;        
        JFileChooser chooser = new JFileChooser(AppContext.currentAppPath);        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Database Definition", "xml");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Select a CanReg5 Database Definition file");
        int result = chooser.showOpenDialog(null);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            fileSelected = chooser.getSelectedFile();
        }
        return fileSelected;
    }    
    
    public void C_databaseDefinitonCompleted() {
        this.rootPanel.getDatabasePanel().setVisible(true);
        this.rootPanel.getDatabasePanel().setMainController(this);
    }
    
    public void D_selectDatabaseFile() {
        final File dbFile = this.E_showDatabaseFileSelection();
        if(dbFile != null) {
            this.longProcess(new S1_DatabaseMigrationPreparatorController(this.rootPanel.getDatabasePanel()), 
                             dbFile);
        }            
    }
    
    private File E_showDatabaseFileSelection() {
        File fileSelected = null;        
        JFileChooser chooser = new JFileChooser(AppContext.currentAppPath);                
        chooser.setFileFilter(new DatabaseFileFilter());
        chooser.setDialogTitle("Select CanReg4 Database to migrate");
        int result = chooser.showOpenDialog(null);
        
        if (result == JFileChooser.APPROVE_OPTION) 
            fileSelected = chooser.getSelectedFile();      
        return fileSelected;
    }          
    
    public void F_databaseSelectionCompleted() {        
        this.longProcess(new S2_VariableSelectionController(this.rootPanel.getVariablesSelectionPanel()));
    }

    public void G_processDatabase(Object selectedUpdateDateVar,
                                  Object selectedMPCodeVar, 
                                  Enumeration<String> rightPanelVars) {
        this.MPCodeVar = (String) selectedMPCodeVar;
        this.UpdateDateVar = (String) selectedUpdateDateVar;
        this.longProcess(new S3_DatabaseProcessorController(this.rootPanel.getDatabaseErrorsPanel()), 
                         selectedUpdateDateVar, 
                         selectedMPCodeVar,
                         rightPanelVars);
    }
    
    public void H_databaseProcessingCompleted() {
        this.longProcess(new S4_PatientVariablesSelectionController(
                                this.rootPanel.getPatientVariablesSelectionPanel()), 
                         this.MPCodeVar);
    }
    
    public void patientVariablesSelected(String registryNumber, String MPTotal, String MPSequence) {
        this.MPSequenceVar = MPSequence;
        this.registryNumberVar = registryNumber;
        this.longProcess(new SourcesSelectionController(this.rootPanel.getSourcesSelectionPanel()), 
                         this.MPCodeVar, 
                         this.UpdateDateVar, 
                         registryNumber,
                         MPTotal, 
                         MPSequence);
    } 
    
    public void sourcesSelected(int ammountOfSources, 
                                String[] labels, 
                                ArrayList<String[]> selectedValues) {
        this.longProcess(new FinalProcessController(), 
                        ammountOfSources, 
                        labels, 
                        selectedValues, 
                        this.MPSequenceVar, 
                        this.registryNumberVar);
    }
    
    private void longProcess(final LongProcessStrategy strategy, 
                             final Object... parameters) {                                            
        Main.startLoading();         
        Utils.startTimer();        

        final MainController controller = this;
        SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>(){
            @Override
            protected Void doInBackground() {                        
                try {
                    strategy.execute(parameters);
                    strategy.afterExecute(controller);
                }
                catch(Exception ex) {                    
                    DialogForm.errorMessage(this.getClass(), ex, "There has been an error in this step. Please try \n"
                                                        + "again, checking that the values selected are correct.\n"
                                                        + "If the problem persists, please contact " + AppContext.techSupportMail + ".", 
                                            "Error");   
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
                     System.out.println(strategy.getDescription() + " DURATION: "
                                        + Utils.endTimer() + "ms");                  
                 }
              }
           }
        });

        mySwingWorker.execute(); 
    }                    
}
