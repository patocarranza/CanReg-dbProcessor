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
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 *
 * @author patri_000
 */
public class SourcePanel extends javax.swing.JPanel {

    private JComboBox[] combos;
    private JLabel[] labels;
    private int visibleCombos;
    
    public SourcePanel() {
        initComponents();
        combos = new JComboBox[]{cmbVar1, cmbVar2, cmbVar3, cmbVar4, cmbVar5, cmbVar6, 
                                 cmbVar7, cmbVar8, cmbVar9, cmbVar10, cmbVar11, cmbVar12};
        labels = new JLabel[]{lblVar1, lblVar2, lblVar3, lblVar4, lblVar5, lblVar6,
                              lblVar7, lblVar8, lblVar9, lblVar10, lblVar11, lblVar12};
    }
    
    void setCombosValues(String[] values) {
        for(JComboBox combo : combos) {
            //((DefaultComboBoxModel)this.cmbUpdateDate.getModel()).removeAllElements();
            combo.setModel(new javax.swing.DefaultComboBoxModel(values));
            combo.setSelectedIndex(-1);
        }                
    }

    void setAmmountOfVisibleCombos(int ammount) {
        this.visibleCombos = ammount;
        for(int i = ammount; i < combos.length; i++) {
            combos[i].setVisible(false);
            labels[i].setVisible(false);
        }        
    }
    
    void setLabels(String... label) {
        try {
            for(int i = 0; i < labels.length; i++)
                labels[i].setText(label[i]);            
        } catch(ArrayIndexOutOfBoundsException ex) {
            return;
        }
    }
    
    void refresh() {
        for(int i = 0; i < labels.length; i++)
            labels[i].setText("empty");
        for(int i = 0; i < combos.length; i++)
            ((DefaultComboBoxModel)this.combos[i].getModel()).removeAllElements();            
    }
    
    String[] getSelectedValues() {
        String[] selectedValues = new String[visibleCombos];
        int counter = 0;
        for(JComboBox combo : this.combos) {
            if(combo.isVisible() && combo.getSelectedIndex() != -1) {
                selectedValues[counter] = (String) combo.getSelectedItem();
                counter++;
            }                
        }
        return selectedValues;
    }
   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jLabel1 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        filler10 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 25));
        lblVar1 = new javax.swing.JLabel();
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 25));
        cmbVar1 = new javax.swing.JComboBox();
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 25));
        lblVar2 = new javax.swing.JLabel();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 25));
        cmbVar2 = new javax.swing.JComboBox();
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 25));
        lblVar3 = new javax.swing.JLabel();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 25));
        cmbVar3 = new javax.swing.JComboBox();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 25));
        jPanel4 = new javax.swing.JPanel();
        filler11 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 25));
        lblVar4 = new javax.swing.JLabel();
        filler12 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 25));
        cmbVar4 = new javax.swing.JComboBox();
        filler13 = new javax.swing.Box.Filler(new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 25));
        lblVar5 = new javax.swing.JLabel();
        filler14 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 25));
        cmbVar5 = new javax.swing.JComboBox();
        filler15 = new javax.swing.Box.Filler(new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 25));
        lblVar6 = new javax.swing.JLabel();
        filler16 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 25));
        cmbVar6 = new javax.swing.JComboBox();
        filler17 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 25));
        jPanel5 = new javax.swing.JPanel();
        filler18 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 25));
        lblVar7 = new javax.swing.JLabel();
        filler19 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 25));
        cmbVar7 = new javax.swing.JComboBox();
        filler20 = new javax.swing.Box.Filler(new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 25));
        lblVar8 = new javax.swing.JLabel();
        filler21 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 25));
        cmbVar8 = new javax.swing.JComboBox();
        filler22 = new javax.swing.Box.Filler(new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 25));
        lblVar9 = new javax.swing.JLabel();
        filler23 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 25));
        cmbVar9 = new javax.swing.JComboBox();
        filler24 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 25));
        jPanel6 = new javax.swing.JPanel();
        filler25 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 25));
        lblVar10 = new javax.swing.JLabel();
        filler26 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 25));
        cmbVar10 = new javax.swing.JComboBox();
        filler27 = new javax.swing.Box.Filler(new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 25));
        lblVar11 = new javax.swing.JLabel();
        filler28 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 25));
        cmbVar11 = new javax.swing.JComboBox();
        filler29 = new javax.swing.Box.Filler(new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 25));
        lblVar12 = new javax.swing.JLabel();
        filler30 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 25));
        cmbVar12 = new javax.swing.JComboBox();
        filler31 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 25));
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 15), new java.awt.Dimension(0, 15), new java.awt.Dimension(32767, 15));

        setMaximumSize(new java.awt.Dimension(780, 150));
        setMinimumSize(new java.awt.Dimension(780, 80));
        setPreferredSize(new java.awt.Dimension(780, 150));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel1.setMaximumSize(new java.awt.Dimension(32828, 25));
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 25));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));
        jPanel1.add(filler2);

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel1.setText("Source");
        jPanel1.add(jLabel1);
        jPanel1.add(filler1);

        add(jPanel1);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel3.setMaximumSize(new java.awt.Dimension(98503, 30));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));
        jPanel3.add(filler10);

        lblVar1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblVar1.setText("jLabel2");
        jPanel3.add(lblVar1);
        jPanel3.add(filler7);

        cmbVar1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbVar1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbVar1.setMaximumSize(new java.awt.Dimension(32767, 25));
        jPanel3.add(cmbVar1);
        jPanel3.add(filler8);

        lblVar2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblVar2.setText("jLabel2");
        jPanel3.add(lblVar2);
        jPanel3.add(filler6);

        cmbVar2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbVar2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbVar2.setMaximumSize(new java.awt.Dimension(32767, 25));
        jPanel3.add(cmbVar2);
        jPanel3.add(filler9);

        lblVar3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblVar3.setText("jLabel2");
        jPanel3.add(lblVar3);
        jPanel3.add(filler5);

        cmbVar3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbVar3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbVar3.setMaximumSize(new java.awt.Dimension(32767, 25));
        jPanel3.add(cmbVar3);
        jPanel3.add(filler4);

        jPanel2.add(jPanel3);

        jPanel4.setMaximumSize(new java.awt.Dimension(98503, 30));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));
        jPanel4.add(filler11);

        lblVar4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblVar4.setText("jLabel2");
        jPanel4.add(lblVar4);
        jPanel4.add(filler12);

        cmbVar4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbVar4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbVar4.setMaximumSize(new java.awt.Dimension(32767, 25));
        jPanel4.add(cmbVar4);
        jPanel4.add(filler13);

        lblVar5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblVar5.setText("jLabel2");
        jPanel4.add(lblVar5);
        jPanel4.add(filler14);

        cmbVar5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbVar5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbVar5.setMaximumSize(new java.awt.Dimension(32767, 25));
        jPanel4.add(cmbVar5);
        jPanel4.add(filler15);

        lblVar6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblVar6.setText("jLabel2");
        jPanel4.add(lblVar6);
        jPanel4.add(filler16);

        cmbVar6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbVar6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbVar6.setMaximumSize(new java.awt.Dimension(32767, 25));
        jPanel4.add(cmbVar6);
        jPanel4.add(filler17);

        jPanel2.add(jPanel4);

        jPanel5.setMaximumSize(new java.awt.Dimension(98503, 30));
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));
        jPanel5.add(filler18);

        lblVar7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblVar7.setText("jLabel2");
        jPanel5.add(lblVar7);
        jPanel5.add(filler19);

        cmbVar7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbVar7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbVar7.setMaximumSize(new java.awt.Dimension(32767, 25));
        jPanel5.add(cmbVar7);
        jPanel5.add(filler20);

        lblVar8.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblVar8.setText("jLabel2");
        jPanel5.add(lblVar8);
        jPanel5.add(filler21);

        cmbVar8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbVar8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbVar8.setMaximumSize(new java.awt.Dimension(32767, 25));
        jPanel5.add(cmbVar8);
        jPanel5.add(filler22);

        lblVar9.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblVar9.setText("jLabel2");
        jPanel5.add(lblVar9);
        jPanel5.add(filler23);

        cmbVar9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbVar9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbVar9.setMaximumSize(new java.awt.Dimension(32767, 25));
        jPanel5.add(cmbVar9);
        jPanel5.add(filler24);

        jPanel2.add(jPanel5);

        jPanel6.setMaximumSize(new java.awt.Dimension(98503, 30));
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));
        jPanel6.add(filler25);

        lblVar10.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblVar10.setText("jLabel2");
        jPanel6.add(lblVar10);
        jPanel6.add(filler26);

        cmbVar10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbVar10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbVar10.setMaximumSize(new java.awt.Dimension(32767, 25));
        jPanel6.add(cmbVar10);
        jPanel6.add(filler27);

        lblVar11.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblVar11.setText("jLabel2");
        jPanel6.add(lblVar11);
        jPanel6.add(filler28);

        cmbVar11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbVar11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbVar11.setMaximumSize(new java.awt.Dimension(32767, 25));
        jPanel6.add(cmbVar11);
        jPanel6.add(filler29);

        lblVar12.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblVar12.setText("jLabel2");
        jPanel6.add(lblVar12);
        jPanel6.add(filler30);

        cmbVar12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbVar12.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbVar12.setMaximumSize(new java.awt.Dimension(32767, 25));
        jPanel6.add(cmbVar12);
        jPanel6.add(filler31);

        jPanel2.add(jPanel6);

        add(jPanel2);
        add(filler3);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cmbVar1;
    private javax.swing.JComboBox cmbVar10;
    private javax.swing.JComboBox cmbVar11;
    private javax.swing.JComboBox cmbVar12;
    private javax.swing.JComboBox cmbVar2;
    private javax.swing.JComboBox cmbVar3;
    private javax.swing.JComboBox cmbVar4;
    private javax.swing.JComboBox cmbVar5;
    private javax.swing.JComboBox cmbVar6;
    private javax.swing.JComboBox cmbVar7;
    private javax.swing.JComboBox cmbVar8;
    private javax.swing.JComboBox cmbVar9;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler10;
    private javax.swing.Box.Filler filler11;
    private javax.swing.Box.Filler filler12;
    private javax.swing.Box.Filler filler13;
    private javax.swing.Box.Filler filler14;
    private javax.swing.Box.Filler filler15;
    private javax.swing.Box.Filler filler16;
    private javax.swing.Box.Filler filler17;
    private javax.swing.Box.Filler filler18;
    private javax.swing.Box.Filler filler19;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler20;
    private javax.swing.Box.Filler filler21;
    private javax.swing.Box.Filler filler22;
    private javax.swing.Box.Filler filler23;
    private javax.swing.Box.Filler filler24;
    private javax.swing.Box.Filler filler25;
    private javax.swing.Box.Filler filler26;
    private javax.swing.Box.Filler filler27;
    private javax.swing.Box.Filler filler28;
    private javax.swing.Box.Filler filler29;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler30;
    private javax.swing.Box.Filler filler31;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler8;
    private javax.swing.Box.Filler filler9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblVar1;
    private javax.swing.JLabel lblVar10;
    private javax.swing.JLabel lblVar11;
    private javax.swing.JLabel lblVar12;
    private javax.swing.JLabel lblVar2;
    private javax.swing.JLabel lblVar3;
    private javax.swing.JLabel lblVar4;
    private javax.swing.JLabel lblVar5;
    private javax.swing.JLabel lblVar6;
    private javax.swing.JLabel lblVar7;
    private javax.swing.JLabel lblVar8;
    private javax.swing.JLabel lblVar9;
    // End of variables declaration//GEN-END:variables
     
}
