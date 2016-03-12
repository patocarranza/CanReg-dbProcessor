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

import java.util.ArrayList;
import org.who.canreg.dbprocessor.controllers.MainController;

/**
 *
 * @author patri_000
 */
public class SourcesSelectionPanel extends javax.swing.JPanel {

    private final ArrayList<SourcePanel> sourcePanels;
    private String[] comboValues;
    private int visibleCombos;
    private String[] labels;
    private MainController controller;
    
    public SourcesSelectionPanel() {
        initComponents();
        this.sourcePanels = new ArrayList<>();
        this.sourcePanels.add(initialSourcePanel);
    }
    
    public void setMainController(MainController controller) {
        this.controller = controller;
    }
    
    public void refresh() {
        for(int i = 1; i < sourcePanels.size(); i++) {
            this.sourceContainer.remove(sourcePanels.get(i));
            this.sourcePanels.remove(i);
        }
        
        this.initialSourcePanel.refresh();
    }
           
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sourceContainer = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 32767));
        jLabel6 = new javax.swing.JLabel();
        filler10 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        initialSourcePanel = new org.who.canreg.dbprocessor.gui.SourcePanel();
        btnAddSource = new javax.swing.JButton();
        btnRemoveSource = new javax.swing.JButton();
        btnRemoveSource1 = new javax.swing.JButton();

        sourceContainer.setLayout(new javax.swing.BoxLayout(sourceContainer, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel5.setMaximumSize(new java.awt.Dimension(33066, 40));
        jPanel5.setPreferredSize(new java.awt.Dimension(299, 40));
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));
        jPanel5.add(filler9);

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setText("Step 5: define the variables of each source of information");
        jPanel5.add(jLabel6);

        filler10.setBackground(new java.awt.Color(102, 255, 102));
        filler10.setForeground(new java.awt.Color(153, 153, 0));
        filler10.setOpaque(true);
        jPanel5.add(filler10);

        sourceContainer.add(jPanel5);
        sourceContainer.add(initialSourcePanel);

        btnAddSource.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnAddSource.setText("Add Source");
        btnAddSource.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSourceActionPerformed(evt);
            }
        });

        btnRemoveSource.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnRemoveSource.setText("Remove Source");
        btnRemoveSource.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveSourceActionPerformed(evt);
            }
        });

        btnRemoveSource1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnRemoveSource1.setText("Finish");
        btnRemoveSource1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveSource1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(sourceContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRemoveSource, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddSource, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnRemoveSource1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(sourceContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnRemoveSource1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAddSource)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveSource)))
                .addGap(42, 42, 42))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    public void setCombosValues(String[] values) {
        this.comboValues = values;
        for(SourcePanel panel : this.sourcePanels)
            panel.setCombosValues(values);              
    }

    public void setAmmountOfVisibleCombos(int ammount) {
        this.visibleCombos = ammount;
        for(SourcePanel panel : this.sourcePanels)
            panel.setAmmountOfVisibleCombos(ammount);     
    }
    
    public void setLabels(String... label) {
        this.labels = label;
        for(SourcePanel panel : this.sourcePanels)
            panel.setLabels(label);        
    }    
    
    private void btnAddSourceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSourceActionPerformed
        SourcePanel sourcePanel = new SourcePanel();
        sourcePanel.setCombosValues(this.comboValues);
        sourcePanel.setAmmountOfVisibleCombos(this.visibleCombos);
        sourcePanel.setLabels(this.labels);
        this.sourcePanels.add(sourcePanel);
        this.sourceContainer.add(sourcePanel);
    }//GEN-LAST:event_btnAddSourceActionPerformed

    private void btnRemoveSourceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveSourceActionPerformed
        SourcePanel sourcePanel = this.sourcePanels.get((sourcePanels.size() -1));
        this.sourceContainer.remove(sourcePanel);
        this.sourcePanels.remove(sourcePanel);
        this.sourceContainer.revalidate();
        this.sourceContainer.repaint();
    }//GEN-LAST:event_btnRemoveSourceActionPerformed

    private void btnRemoveSource1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveSource1ActionPerformed
        int ammountOfSources = sourcePanels.size();
        //int ammountOfVarsPerSource = visibleCombos;
        ArrayList<String[]> selectedValues = new ArrayList<>(ammountOfSources);
        for(SourcePanel panel : this.sourcePanels) {
            String[] values = panel.getSelectedValues();
            selectedValues.add(values);
        }
            
        this.controller.sourcesSelected(ammountOfSources, labels, selectedValues); 
    }//GEN-LAST:event_btnRemoveSource1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddSource;
    private javax.swing.JButton btnRemoveSource;
    private javax.swing.JButton btnRemoveSource1;
    private javax.swing.Box.Filler filler10;
    private javax.swing.Box.Filler filler9;
    private org.who.canreg.dbprocessor.gui.SourcePanel initialSourcePanel;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel sourceContainer;
    // End of variables declaration//GEN-END:variables
}
