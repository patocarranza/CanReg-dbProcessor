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
package org.who.canreg.dbprocessor.gui;


/**
 * This panel is fixed during the entire life cycle of this app 
 * because it's highly related to Loading. All visual components
 * of this app are attached to this panel.
 * @author Patricio Carranza, Beatriz Carballo
 * @version 1.00.000
 * last update: 23/02/2016
 */
public class RootPanel extends javax.swing.JPanel {

   
    public RootPanel() {
        initComponents();
        this.hideAllPanels();
    }
    
    public void refreshPanelsContent() {
        this.databaseDefinitionPanel1.refresh();    
        this.databasePanel1.refresh();
        this.variablesSelectionPanel1.refresh();
        this.databaseProcessingErrorsPanel1.refresh();
        this.patientVariablesSelectionPanel1.refresh();
        this.sourcesSelectionPanel1.refresh();
    }
    
    public void hideAllPanels() {
        this.databaseDefinitionPanel1.setVisible(false);    
        this.databasePanel1.setVisible(false);
        this.variablesSelectionPanel1.setVisible(false);
        this.databaseProcessingErrorsPanel1.setVisible(false);
        this.patientVariablesSelectionPanel1.setVisible(false);
        this.sourcesSelectionPanel1.setVisible(false);
    }
    
    public DatabaseDefinitionPanel getDatabaseDefinitionPanel() {
        return this.databaseDefinitionPanel1;
    }
    
    public VariablesSelectionPanel getVariablesSelectionPanel() {
        return this.variablesSelectionPanel1;        
    }
    
    public DatabaseProcessingErrorsPanel getDatabaseErrorsPanel() {
        return this.databaseProcessingErrorsPanel1;
    }

    public DatabasePanel getDatabasePanel() {
        return this.databasePanel1;
    }
    
    public PatientVariablesSelectionPanel getPatientVariablesSelectionPanel() {
        return this.patientVariablesSelectionPanel1;
    }
    
    public SourcesSelectionPanel getSourcesSelectionPanel() {
        return this.sourcesSelectionPanel1;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        databaseDefinitionPanel1 = new org.who.canreg.dbprocessor.gui.DatabaseDefinitionPanel();
        databasePanel1 = new org.who.canreg.dbprocessor.gui.DatabasePanel();
        jPanel2 = new javax.swing.JPanel();
        variablesSelectionPanel1 = new org.who.canreg.dbprocessor.gui.VariablesSelectionPanel();
        databaseProcessingErrorsPanel1 = new org.who.canreg.dbprocessor.gui.DatabaseProcessingErrorsPanel();
        patientVariablesSelectionPanel1 = new org.who.canreg.dbprocessor.gui.PatientVariablesSelectionPanel();
        sourcesSelectionPanel1 = new org.who.canreg.dbprocessor.gui.SourcesSelectionPanel();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.PAGE_AXIS));
        jPanel1.add(databaseDefinitionPanel1);
        jPanel1.add(databasePanel1);

        jPanel2.setMaximumSize(new java.awt.Dimension(32767, 612));
        jPanel2.setMinimumSize(new java.awt.Dimension(798, 612));
        jPanel2.setPreferredSize(new java.awt.Dimension(926, 612));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        variablesSelectionPanel1.setMinimumSize(new java.awt.Dimension(926, 580));
        jPanel2.add(variablesSelectionPanel1);

        jPanel1.add(jPanel2);
        jPanel1.add(databaseProcessingErrorsPanel1);
        jPanel1.add(patientVariablesSelectionPanel1);
        jPanel1.add(sourcesSelectionPanel1);

        jScrollPane1.setViewportView(jPanel1);

        add(jScrollPane1);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.who.canreg.dbprocessor.gui.DatabaseDefinitionPanel databaseDefinitionPanel1;
    private org.who.canreg.dbprocessor.gui.DatabasePanel databasePanel1;
    private org.who.canreg.dbprocessor.gui.DatabaseProcessingErrorsPanel databaseProcessingErrorsPanel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private org.who.canreg.dbprocessor.gui.PatientVariablesSelectionPanel patientVariablesSelectionPanel1;
    private org.who.canreg.dbprocessor.gui.SourcesSelectionPanel sourcesSelectionPanel1;
    private org.who.canreg.dbprocessor.gui.VariablesSelectionPanel variablesSelectionPanel1;
    // End of variables declaration//GEN-END:variables

       
}
