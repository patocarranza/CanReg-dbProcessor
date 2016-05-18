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

import java.util.Collection;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import org.who.canreg.dbprocessor.controllers.MainController;

/**
 *
 * @author patri_000
 */
public class S2_VariablesSelectionPanel extends javax.swing.JPanel {

    private MainController controller;
    
    public S2_VariablesSelectionPanel() {
        initComponents();
        this.leftList.setModel(new javax.swing.DefaultListModel<String>());
        this.rightList.setModel(new javax.swing.DefaultListModel<String>());
    }   
    
    public void setMainController(MainController controller) {
        this.controller = controller;
    }
    
    void refresh() {
        this.leftList.setModel(new javax.swing.DefaultListModel<String>());
        this.rightList.setModel(new javax.swing.DefaultListModel<String>());
        this.cmbUpdateDate.setModel(new javax.swing.DefaultComboBoxModel());
        this.cmbMPCode.setModel(new javax.swing.DefaultComboBoxModel());
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 32767));
        jLabel6 = new javax.swing.JLabel();
        filler10 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jPanel4 = new javax.swing.JPanel();
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 32767));
        jLabel5 = new javax.swing.JLabel();
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 32767));
        jPanel3 = new javax.swing.JPanel();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 32767));
        jLabel3 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 32767));
        jLabel1 = new javax.swing.JLabel();
        cmbUpdateDate = new javax.swing.JComboBox();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(30, 0), new java.awt.Dimension(30, 0), new java.awt.Dimension(30, 32767));
        jLabel2 = new javax.swing.JLabel();
        cmbMPCode = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        leftList = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        rightList = new javax.swing.JList();
        btnPassRight = new javax.swing.JButton();
        btnPassLeft = new javax.swing.JButton();
        btnContinue = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(926, 612));
        setPreferredSize(new java.awt.Dimension(926, 612));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel5.setMaximumSize(new java.awt.Dimension(33066, 40));
        jPanel5.setPreferredSize(new java.awt.Dimension(299, 40));
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));
        jPanel5.add(filler9);

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setText("Step 2: select variables to be compared");
        jPanel5.add(jLabel6);

        filler10.setBackground(new java.awt.Color(102, 255, 102));
        filler10.setForeground(new java.awt.Color(153, 153, 0));
        filler10.setOpaque(true);
        jPanel5.add(filler10);

        add(jPanel5);

        jPanel4.setMaximumSize(new java.awt.Dimension(32767, 130));
        jPanel4.setMinimumSize(new java.awt.Dimension(100, 40));
        jPanel4.setPreferredSize(new java.awt.Dimension(682, 60));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));
        jPanel4.add(filler7);

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel5.setText("<html>In this next step we have to check if the data in each MP Patient is the same (for example Vital status, DOB, names, etc). Please pick which variables have to be compared to know if the MP patient has the same data in each CanReg4 case. We ask you this because one of the major problems during the CR4 migration wizard was that CR5 does not know which data is updated and which data is not updated. </html>");
        jPanel4.add(jLabel5);
        jPanel4.add(filler8);

        add(jPanel4);

        jPanel3.setMaximumSize(new java.awt.Dimension(32767, 120));
        jPanel3.setMinimumSize(new java.awt.Dimension(100, 40));
        jPanel3.setPreferredSize(new java.awt.Dimension(682, 60));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));
        jPanel3.add(filler6);

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel3.setText("<html>Update date: the date the registry was updated</html>");
        jPanel3.add(jLabel3);
        jPanel3.add(filler1);

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel4.setText("<html>Multiple primary code variable: code generated by CanReg4 to match each multiple primary registry in the CanReg4 database</html>");
        jLabel4.setPreferredSize(new java.awt.Dimension(400, 44));
        jPanel3.add(jLabel4);

        add(jPanel3);

        jPanel1.setMaximumSize(new java.awt.Dimension(32767, 60));
        jPanel1.setMinimumSize(new java.awt.Dimension(100, 40));
        jPanel1.setPreferredSize(new java.awt.Dimension(682, 40));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));
        jPanel1.add(filler4);

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel1.setText("Update date variable: ");
        jPanel1.add(jLabel1);

        cmbUpdateDate.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cmbUpdateDate.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbUpdateDate.setMaximumSize(new java.awt.Dimension(32767, 32));
        jPanel1.add(cmbUpdateDate);
        jPanel1.add(filler5);

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel2.setText("Multiple primary code variable: ");
        jPanel1.add(jLabel2);

        cmbMPCode.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cmbMPCode.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbMPCode.setMaximumSize(new java.awt.Dimension(32767, 32));
        jPanel1.add(cmbMPCode);

        add(jPanel1);

        jScrollPane1.setViewportView(leftList);

        jScrollPane2.setViewportView(rightList);

        btnPassRight.setText("-->");
        btnPassRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPassRightActionPerformed(evt);
            }
        });

        btnPassLeft.setText("<--");
        btnPassLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPassLeftActionPerformed(evt);
            }
        });

        btnContinue.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnContinue.setText("Continue to Step 3");
        btnContinue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContinueActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPassRight)
                    .addComponent(btnPassLeft))
                .addGap(46, 46, 46)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnContinue)
                .addGap(41, 41, 41))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(btnPassRight)
                        .addGap(54, 54, 54)
                        .addComponent(btnPassLeft))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnContinue)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane2)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents

    public void setUpdateDateCombo(String[] values, String initialSelectedValue) {
        ((DefaultComboBoxModel)this.cmbUpdateDate.getModel()).removeAllElements();
        this.cmbUpdateDate.setModel(new javax.swing.DefaultComboBoxModel(values));
        this.cmbUpdateDate.setSelectedItem(initialSelectedValue);
    }
    
    public void setMPCodeComboValues(String[] values, String initialSelectedValue) {
        ((DefaultComboBoxModel)this.cmbMPCode.getModel()).removeAllElements();
        this.cmbMPCode.setModel(new javax.swing.DefaultComboBoxModel(values));
        this.cmbMPCode.setSelectedItem(initialSelectedValue);
    }
    
    public void setLeftListValues(String[] values) {
        ((DefaultListModel<String>)this.leftList.getModel()).removeAllElements();
        ((DefaultListModel<String>)this.rightList.getModel()).removeAllElements();
        for(String val : values)
            ((DefaultListModel<String>)this.leftList.getModel()).addElement(val);
    }
    
    private void btnPassRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPassRightActionPerformed
        String var = (String) this.leftList.getSelectedValue();
        if(var != null) {
            ((DefaultListModel<String>)this.rightList.getModel()).addElement(var);
            ((DefaultListModel<String>)this.leftList.getModel()).removeElement(var);
        }
    }//GEN-LAST:event_btnPassRightActionPerformed

    private void btnPassLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPassLeftActionPerformed
        String var = (String) this.rightList.getSelectedValue();
        if(var != null) {
            ((DefaultListModel<String>)this.leftList.getModel()).addElement(var);
            ((DefaultListModel<String>)this.rightList.getModel()).removeElement(var);
        }
    }//GEN-LAST:event_btnPassLeftActionPerformed

    private void btnContinueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContinueActionPerformed
        if(this.cmbMPCode.getSelectedItem() == this.cmbUpdateDate.getSelectedItem()) {
            JOptionPane.showMessageDialog(null, "Update date variable and Multiple primary code variable must be different.", 
                                                 "Error", JOptionPane.ERROR_MESSAGE);
        } else {                
            ((DefaultListModel<String>)this.rightList.getModel()).removeElement(this.cmbMPCode.getSelectedItem());
            ((DefaultListModel<String>)this.rightList.getModel()).removeElement(this.cmbUpdateDate.getSelectedItem());
            this.controller.G_processDatabase(this.cmbUpdateDate.getSelectedItem(), 
                                            this.cmbMPCode.getSelectedItem(), 
                                            ((DefaultListModel<String>)this.rightList.getModel()).elements());
        }
    }//GEN-LAST:event_btnContinueActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnContinue;
    private javax.swing.JButton btnPassLeft;
    private javax.swing.JButton btnPassRight;
    private javax.swing.JComboBox cmbMPCode;
    private javax.swing.JComboBox cmbUpdateDate;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler10;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler8;
    private javax.swing.Box.Filler filler9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList leftList;
    private javax.swing.JList rightList;
    // End of variables declaration//GEN-END:variables

} 
