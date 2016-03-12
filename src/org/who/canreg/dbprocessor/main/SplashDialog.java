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

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import javax.swing.*;
import javax.swing.plaf.LayerUI;

/*
 * Splash screen of this app. Current implementation is a JDIalog.
 * @author Patricio Carranza, Beatriz Carballo
 * @version 1.02.000
 * last update: 01/03/2015
 */
public class SplashDialog extends javax.swing.JDialog {
    
    private final WaitLayerUI layerUI;
    private final Timer stopper;
    
    public SplashDialog() {
        super(null, "", ModalityType.APPLICATION_MODAL); 
        initComponents();
                   
        JPanel loadingPanel = new javax.swing.JPanel();
        loadingPanel.setOpaque(false);
        loadingPanel.setPreferredSize(new java.awt.Dimension(35, 35));
        layerUI = new WaitLayerUI();   
        JLayer<JPanel> jlayer = new JLayer<>(loadingPanel, layerUI);
        jlayer.setSize(loadingPanel.getSize());
        jlayer.setMaximumSize(loadingPanel.getMaximumSize());
        jlayer.setMinimumSize(loadingPanel.getMinimumSize());
        jlayer.setPreferredSize(loadingPanel.getPreferredSize());
        containerPanel.add(jlayer);       
                
        stopper = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
              layerUI.stop();
            }
          });
        stopper.setRepeats(false);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        splashBackground1 = new org.who.canreg.dbprocessor.main.SplashBackground();
        containerPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(300, 200));
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        setName("aboutDialog"); // NOI18N
        setUndecorated(true);
        setResizable(false);

        javax.swing.GroupLayout splashBackground1Layout = new javax.swing.GroupLayout(splashBackground1);
        splashBackground1.setLayout(splashBackground1Layout);
        splashBackground1Layout.setHorizontalGroup(
            splashBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 590, Short.MAX_VALUE)
        );
        splashBackground1Layout.setVerticalGroup(
            splashBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 334, Short.MAX_VALUE)
        );

        containerPanel.setOpaque(false);
        containerPanel.setLayout(new javax.swing.BoxLayout(containerPanel, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splashBackground1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(containerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splashBackground1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap(228, Short.MAX_VALUE)
                    .addComponent(containerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(27, Short.MAX_VALUE)))
        );
        jLayeredPane1.setLayer(splashBackground1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(containerPanel, javax.swing.JLayeredPane.PALETTE_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel containerPanel;
    private javax.swing.JLayeredPane jLayeredPane1;
    private org.who.canreg.dbprocessor.main.SplashBackground splashBackground1;
    // End of variables declaration//GEN-END:variables
    
    public void startLoading() {
        layerUI.start();
    }
    
    public void stopLoading() {        
        stopper.start();
    }
    
    private class WaitLayerUI extends LayerUI<JPanel> implements ActionListener {
        private boolean mIsRunning;
        private boolean mIsFadingOut;
        private Timer mTimer;

        private int mAngle;
        private int mFadeCount;
        
        //velocity of fading: higher value takes more iterations to fully enlight/turn off,
        //lower value takes less iterations.
        private final int mFadeLimit = 2;

        @Override
        public void paint (Graphics g, JComponent c) {
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
          /*Composite urComposite = g2.getComposite();
          g2.setComposite(AlphaComposite.getInstance(
             AlphaComposite.SRC_OVER, .5f * fade));
          g2.fillRect(0, 0, w, h);
          g2.setComposite(urComposite);*/

          // Paint the wait indicator.
          int s = Math.min(w, h) / 4;
          int cx = w / 2;
          int cy = h / 2;
          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
              RenderingHints.VALUE_ANTIALIAS_ON);
          g2.setStroke(new BasicStroke(s / 4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
          g2.setPaint(Color.white);
          g2.rotate(Math.PI * mAngle / 180, cx, cy);
          for (int i = 0; i < 12; i++) {
            float scale = (11.0f - (float)i) / 11.0f;
            g2.drawLine(cx + s, cy, cx + s * 2, cy);
            g2.rotate(-Math.PI / 6, cx, cy);
            g2.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, scale * fade));
          }

          g2.dispose();
        }

        public void actionPerformed(ActionEvent e) {
          if (mIsRunning) {
            firePropertyChange("tick", 0, 1);
            mAngle += 3;
            if (mAngle >= 360) {
              mAngle = 0;
            }
            if (mIsFadingOut) {
              if (--mFadeCount == 0) {
                mIsRunning = false;
                mTimer.stop();
              }
            }
            else if (mFadeCount < mFadeLimit) {
              mFadeCount++;
            }
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
          if ("tick".equals(pce.getPropertyName())) 
            l.repaint();          
        }
    }

}
