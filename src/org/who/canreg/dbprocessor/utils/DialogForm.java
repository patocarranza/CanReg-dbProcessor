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

package org.who.canreg.dbprocessor.utils;

import javax.swing.JOptionPane;



/**
 * Util class for showing dialogs.
 * @author Patricio Carranza, Beatriz Carballo
 * @version 1.03.000
 * Last update: 01/23/2015
 */
public class DialogForm extends javax.swing.JDialog {


    public DialogForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Shows an error dialog. This methods mimics JOptionPane.showMessageDialog. The only differences
     * is that it uses this app's theme and sends a message to this app's log.
     * @param clazz
     * @param ex exception object thrown.
     * @param dialogMessage dialog to be shown to the user.
     * @param logMessage message to be recorded in the log. 
     */
   public static void errorMessage(Class clazz, java.lang.Exception ex, 
                                   String dialogMessage, String logMessage) {
        JOptionPane.showMessageDialog(null, dialogMessage, 
                                     "Error", JOptionPane.ERROR_MESSAGE);          
        if(ex != null)        
            ex = new Exception(logMessage, ex);
        LogManager.logHandleError(clazz, ex);
   }
   

   public static void message(String dialogMessage, String title) {
       JOptionPane.showMessageDialog(null, dialogMessage, 
                                     title, JOptionPane.PLAIN_MESSAGE);
   }
   
   public static void successMessage(String dialogMessage) {
       JOptionPane.showMessageDialog(null, dialogMessage, 
                                     "Success", JOptionPane.PLAIN_MESSAGE);
   }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
