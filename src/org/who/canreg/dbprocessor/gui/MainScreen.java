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

import java.awt.AWTEvent;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.plaf.LayerUI;
import org.who.canreg.dbprocessor.controllers.MainController;
import org.who.canreg.dbprocessor.main.AppContext;
import org.who.canreg.dbprocessor.main.Main;
import org.who.canreg.dbprocessor.utils.Utils;

/**
 * Main (and only) window. Contains a root panel to which 
 * all visual components attach. App exit, loading and resolution is
 * also handled from here.
 * @author Patricio Carranza, Beatriz Carballo
 * @version 1.00.000
 * last update: 23/02/2016
 */
public class MainScreen extends javax.swing.JFrame {

    //Contains a glassPane on which it draws the loading animation 
    //and catches all user interaction events in order to let the 
    //loading finish.
    private final WaitLayerUI layerUI;
    private MainController mainController;
    
    public MainScreen() {
        initComponents();        
        layerUI = new WaitLayerUI();
        
        JLayer<JPanel> jlayer = new JLayer<>(this.rootPanel, layerUI);
        jlayer.setSize(this.rootPanel.getSize());
        jlayer.setMaximumSize(this.rootPanel.getMaximumSize());
        jlayer.setMinimumSize(this.rootPanel.getMinimumSize());
        jlayer.setPreferredSize(this.rootPanel.getPreferredSize());
        this.add(jlayer);  
        
        this.setSize(Utils.screenSize);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setTitle(AppContext.appName);
        
        //When the user tries to make the window smaller, it'll shrink
        //to 1024x768
        final JFrame window = this;
        this.addWindowStateListener(new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {                
                if(e.getNewState() == JFrame.NORMAL)
                {   window.setSize(new Dimension(1024, 768));  }
            }
        });
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
    }
    
    public void setMainController(MainController controller) {
        this.mainController = controller;
    }
    
    public RootPanel getRootPanel() {
        return this.rootPanel;
    }
    
    public void startLoading() {        
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        layerUI.start();                
    }
    
    public void stopLoading() {
        setCursor(null);
        layerUI.stop();      
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rootPanel = new org.who.canreg.dbprocessor.gui.RootPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        ProcessDb = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));
        getContentPane().add(rootPanel);

        jMenu1.setText("File");

        ProcessDb.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        ProcessDb.setText("Select Database To Process");
        ProcessDb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProcessDbActionPerformed(evt);
            }
        });
        jMenu1.add(ProcessDb);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("About");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ProcessDbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProcessDbActionPerformed
        this.mainController.selectDatabaseDefinition();
    }//GEN-LAST:event_ProcessDbActionPerformed

    private class WaitLayerUI extends LayerUI<JPanel> implements ActionListener {
        private boolean mIsRunning;
        private boolean mIsFadingOut;
        private Timer mTimer;

        private int mAngle;
        private int mFadeCount = 0;
        
        //velocity of fading: higher value takes more iterations to fully enlight/turn off,
        //lower value takes less iterations.
        private final int mFadeLimit = 2;

        @Override
        public void paint(Graphics g, JComponent c) {
            int w = c.getWidth();
            int h = c.getHeight();

            // Paint the view.
            super.paint(g, c);

            if (!mIsRunning) {
              return;
            }

            Graphics2D g2 = (Graphics2D)g.create();

            float fade = (float)mFadeCount / (float)mFadeLimit;
            // Gray it out.
            Composite urComposite = g2.getComposite();
            float alpha = 0f;
            try {            
                alpha = .5f * fade;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                g2.fillRect(0, 0, w, h);
                g2.setComposite(urComposite);            
            } catch(IllegalArgumentException ex) { 
                System.err.println("\n\nAlpha composite value out of range = " + alpha);
                ex.printStackTrace();
                g2.dispose();
                return;
            }

            // Paint the wait indicator.
            int s = Math.min(w, h) / 10;
            int cx = w / 2;
            int cy = h / 2;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(s / 4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.setPaint(Color.white);
            g2.rotate(Math.PI * mAngle / 180, cx, cy);
            for (int i = 0; i < 12; i++) {
                float scale = (11.0f - (float)i) / 11.0f;
                g2.drawLine(cx + s, cy, cx + s * 2, cy);
                g2.rotate(-Math.PI / 6, cx, cy);
                try {                
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, scale * fade));
                } catch(IllegalArgumentException ex) {
                    System.err.println("\n\nAlpha222 composite value out of range.\n");
                    break;
                }
            }

            g2.dispose();
        }
        
        @Override 
        public void installUI(JComponent c) {
            super.installUI(c);
            JLayer jlayer = (JLayer)c;
            jlayer.setLayerEventMask(
              AWTEvent.MOUSE_EVENT_MASK |
              AWTEvent.MOUSE_MOTION_EVENT_MASK |
              AWTEvent.KEY_EVENT_MASK);
          }
          @Override 
          public void uninstallUI(JComponent c) {
            JLayer jlayer = (JLayer)c;
            jlayer.setLayerEventMask(0);
            super.uninstallUI(c);
          }
          @Override
          public void eventDispatched(AWTEvent e, JLayer l) {
            if(mIsRunning && e instanceof InputEvent) {
              ((InputEvent)e).consume();
            }
          }

        @Override
        public void actionPerformed(ActionEvent e) {
          if (mIsRunning) {
            //repaint();
            firePropertyChange("t", 0, 1);
            mAngle += 3;
            if (mAngle >= 360) 
                mAngle = 0;
            
            if (mIsFadingOut) {
               // if (--mFadeCount == 0) {                 
                    mIsRunning = false;
                    mTimer.stop();     
                //}                
            }
            else if (mFadeCount < mFadeLimit) 
                mFadeCount++;            
          }
        }

        public void start() {
              if (mIsRunning) {
                return;
              }

              // Run a thread for animation.
              mIsRunning = true;
              mIsFadingOut = false;
              mFadeCount = 0;
              int fps = 24;
              int tick = 1000 / fps;
              mTimer = new Timer(tick, this);
              mTimer.start();
        }

        public void stop() {
              mIsFadingOut = true;              
        }

        @Override
        public void applyPropertyChange(PropertyChangeEvent pce, JLayer l) {
          if ("t".equals(pce.getPropertyName())) 
            l.repaint();          
        }
    }    
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        Main.exitApp();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ProcessDb;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private org.who.canreg.dbprocessor.gui.RootPanel rootPanel;
    // End of variables declaration//GEN-END:variables
}
