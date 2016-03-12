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
package org.who.canreg.dbprocessor.main;


import java.beans.*;
import javax.swing.*;
import org.who.canreg.dbprocessor.controllers.MainController;
import org.who.canreg.dbprocessor.gui.MainScreen;
import org.who.canreg.dbprocessor.r.RExecutor;
import org.who.canreg.dbprocessor.utils.DialogForm;

/**
 * Entry point of the app. Sets the look and feel, starts the
 * required services and launches the GUI.
 * @author Patricio Carranza, Beatriz Carballo
 * @version 1.00.000
 * last update: 23/02/2016
 */
public class Main {        
    
    private static MainController mainController;
    private static MainScreen mainScreen;    
    
    public static void main(String[] args) {             
       //Set the look and feel
       try {            
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
        } catch (ClassNotFoundException ex) {            
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        }                                 
               
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });    
    }             
    
    //Splash screen is a JDialog, and app startup is being performed  
    //on a SwingWorker thread. This splash screen is built completely with Swing components, 
    //so we can do whatever change and dynamic behaviour we want.
    private static void createAndShowGUI() {
        final SplashDialog dialog = new SplashDialog();
                
        SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>(){
            @Override
            protected Void doInBackground() {
                try {
                    dialog.startLoading();

                    //Utils.killOrphanRServeProcesses();

                    //initialize the visual backbone (the frame that holds everything)
                    mainScreen = new MainScreen();

                    //We initialize the MainController. This is where the RServe process will be created        
                    mainController = new MainController(mainScreen.getRootPanel());  
                    
                    mainScreen.setMainController(mainController);

                    return null;
                } catch(Exception ex) {                     
                    DialogForm.errorMessage(this.getClass(), ex, "There has been a critical error trying to start this app.\n"
                                                        + "Please contact " + AppContext.techSupportMail + " to solve this problem.", 
                                            "Error");      
                    exitApp();
                    return null;
                }
            }
        };                                  

        mySwingWorker.addPropertyChangeListener(new PropertyChangeListener() {
           @Override
           public void propertyChange(PropertyChangeEvent evt) {
              if (evt.getPropertyName().equals("state")) {
                 if (evt.getNewValue() == SwingWorker.StateValue.DONE) {                             
                    dialog.stopLoading();
                    dialog.dispose();  
                    mainScreen.setVisible(true);                    
                 }
              }
           }
        });
        
        //swing worker must be executed BEFORE dialog is set visible, otherwise
        //code is unreachable (SplashDialog is a JDialog and takes control, 
        //and no other code will be executed until that SplashDialog is closed).
        mySwingWorker.execute();    
                
        dialog.pack();       
        dialog.setLocationRelativeTo(mainScreen);        
        dialog.setVisible(true);   
    }
    
    public static void startLoading() {
        mainScreen.startLoading();
    }
    
    public static void stopLoading() {
        mainScreen.stopLoading();
    }    
    
    public static void exitApp() {
        try { RExecutor.shutdownRserve(); }         
        catch(Exception ex) { /*Not important*/}
        System.exit(0);
    }        
}
