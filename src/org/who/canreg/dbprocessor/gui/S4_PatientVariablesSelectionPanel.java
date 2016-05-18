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

import javax.swing.DefaultComboBoxModel;
import org.who.canreg.dbprocessor.controllers.MainController;

/**
 *
 * @author patri_000
 */
public class S4_PatientVariablesSelectionPanel extends javax.swing.JPanel {

    private MainController controller;
    
    public S4_PatientVariablesSelectionPanel() {
        initComponents();        
    }
    
    public void setMainController(MainController controller) {
        this.controller = controller;
    }
    
    void refresh() {
        this.cmbRegNumber.setModel(new javax.swing.DefaultComboBoxModel());
        this.cmbMPTotal.setModel(new javax.swing.DefaultComboBoxModel());
        this.cmbMPSeq.setModel(new javax.swing.DefaultComboBoxModel());
    }

    public void setRegistryNumberComboValues(String[] values, String initialSelectedValue) {
        ((DefaultComboBoxModel)this.cmbRegNumber.getModel()).removeAllElements();
        this.cmbRegNumber.setModel(new javax.swing.DefaultComboBoxModel(values));
        this.cmbRegNumber.setSelectedItem(initialSelectedValue);
    }
    
    public void setMPTotalComboValues(String[] values, String initialSelectedValue) {
        ((DefaultComboBoxModel)this.cmbMPTotal.getModel()).removeAllElements();
        this.cmbMPTotal.setModel(new javax.swing.DefaultComboBoxModel(values));
        this.cmbMPTotal.setSelectedItem(initialSelectedValue);
    }
    
    public void setMPSequenceComboValues(String[] values, String initialSelectedValue) {
        ((DefaultComboBoxModel)this.cmbMPSeq.getModel()).removeAllElements();
        this.cmbMPSeq.setModel(new javax.swing.DefaultComboBoxModel(values));
        this.cmbMPSeq.setSelectedItem(initialSelectedValue);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator3 = new javax.swing.JSeparator();
        jPanel5 = new javax.swing.JPanel();
        filler10 = new javax.swing.Box.Filler(new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 32767));
        jLabel6 = new javax.swing.JLabel();
        filler12 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jPanel1 = new javax.swing.JPanel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 32767));
        jLabel1 = new javax.swing.JLabel();
        cmbRegNumber = new javax.swing.JComboBox();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(30, 0), new java.awt.Dimension(30, 0), new java.awt.Dimension(30, 32767));
        jLabel2 = new javax.swing.JLabel();
        cmbMPTotal = new javax.swing.JComboBox();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 32767));
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 32767));
        jPanel2 = new javax.swing.JPanel();
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 32767));
        jLabel3 = new javax.swing.JLabel();
        cmbMPSeq = new javax.swing.JComboBox();
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(30, 0), new java.awt.Dimension(30, 0), new java.awt.Dimension(30, 32767));
        btnContinue1 = new javax.swing.JButton();
        filler11 = new javax.swing.Box.Filler(new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 32767));
        jSeparator4 = new javax.swing.JSeparator();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        jSeparator3.setBackground(new java.awt.Color(0, 102, 102));
        jSeparator3.setMaximumSize(new java.awt.Dimension(32767, 4));
        jSeparator3.setOpaque(true);
        jSeparator3.setPreferredSize(new java.awt.Dimension(0, 4));
        add(jSeparator3);

        jPanel5.setMaximumSize(new java.awt.Dimension(33066, 40));
        jPanel5.setPreferredSize(new java.awt.Dimension(299, 40));
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));
        jPanel5.add(filler10);

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setText("Step 4: select the variables to create the patients and tumors datasets");
        jPanel5.add(jLabel6);

        filler12.setBackground(new java.awt.Color(102, 255, 102));
        filler12.setForeground(new java.awt.Color(153, 153, 0));
        filler12.setOpaque(true);
        jPanel5.add(filler12);

        add(jPanel5);

        jPanel1.setMaximumSize(new java.awt.Dimension(32767, 60));
        jPanel1.setMinimumSize(new java.awt.Dimension(100, 40));
        jPanel1.setPreferredSize(new java.awt.Dimension(682, 40));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));
        jPanel1.add(filler4);

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel1.setText("Registry Number variable: ");
        jPanel1.add(jLabel1);

        cmbRegNumber.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cmbRegNumber.setMaximumSize(new java.awt.Dimension(32767, 32));
        jPanel1.add(cmbRegNumber);
        jPanel1.add(filler5);

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel2.setText("Multiple Primary Total variable: ");
        jPanel1.add(jLabel2);

        cmbMPTotal.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cmbMPTotal.setMaximumSize(new java.awt.Dimension(32767, 32));
        jPanel1.add(cmbMPTotal);
        jPanel1.add(filler6);
        jPanel1.add(filler7);

        add(jPanel1);

        jPanel2.setMaximumSize(new java.awt.Dimension(32767, 60));
        jPanel2.setMinimumSize(new java.awt.Dimension(100, 40));
        jPanel2.setPreferredSize(new java.awt.Dimension(682, 40));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));
        jPanel2.add(filler8);

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel3.setText("Multiple Primary Sequence variable:  ");
        jPanel2.add(jLabel3);

        cmbMPSeq.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cmbMPSeq.setMaximumSize(new java.awt.Dimension(32767, 32));
        jPanel2.add(cmbMPSeq);
        jPanel2.add(filler9);

        btnContinue1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnContinue1.setText("Continue to Step 5");
        btnContinue1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContinue1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnContinue1);
        jPanel2.add(filler11);

        add(jPanel2);

        jSeparator4.setBackground(new java.awt.Color(0, 102, 102));
        jSeparator4.setMaximumSize(new java.awt.Dimension(32767, 4));
        jSeparator4.setOpaque(true);
        jSeparator4.setPreferredSize(new java.awt.Dimension(0, 4));
        add(jSeparator4);
    }// </editor-fold>//GEN-END:initComponents

    private void btnContinue1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContinue1ActionPerformed
        this.controller.patientVariablesSelected((String) this.cmbRegNumber.getSelectedItem(),
                                                 (String) this.cmbMPTotal.getSelectedItem(), 
                                                 (String) this.cmbMPSeq.getSelectedItem());
    }//GEN-LAST:event_btnContinue1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnContinue1;
    private javax.swing.JComboBox cmbMPSeq;
    private javax.swing.JComboBox cmbMPTotal;
    private javax.swing.JComboBox cmbRegNumber;
    private javax.swing.Box.Filler filler10;
    private javax.swing.Box.Filler filler11;
    private javax.swing.Box.Filler filler12;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler8;
    private javax.swing.Box.Filler filler9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    // End of variables declaration//GEN-END:variables
}
