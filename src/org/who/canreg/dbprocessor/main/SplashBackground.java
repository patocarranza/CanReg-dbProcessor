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

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author patri_000
 */
public class SplashBackground extends JPanel {
    
    private BufferedImage originalImage, image;    
    private volatile int width = 0, height = 0;
    
    public SplashBackground() {
        try {
            originalImage = ImageIO.read(getClass().getResource("splash.jpg"));
        } catch (IOException ex) {           
            ex.printStackTrace();
       } 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //This if seems weird and/or stupid or even unnecessary, but it has one important
        //purpose: to re-draw the image that we want to resize only when its necessary.
        //Here's the explanation:
        //  - This method (paintComponent()) is called ALL THE TIME by Swing, even if this
        //    panel hasn't changed visually at all. Just pretend that paintComponent() is called
        //    to create the FPS (frames per second), so imagine that it's called 30 times 
        //    per second (JUST PRETEND).
        //  - Considering the stated above, paintComponent() has to have a SUPER-LIGHT 
        //    implementation, otherwise the app will slowdown terribly.
        //  - We have an image of X size and we want to stick it in this panel as its 
        //    background, and we want the image to resize itself when we resize the 
        //    panel that contains it, and of course we DON'T want to loose image 
        //    quality when we do that. It is a VERY EXPENSIVE process to redimension 
        //    an image without loosing quality, so we must do it only when strictly
        //    necessary. This resizing is done by:
        //      Image img = originalImage.getScaledInstance(this.getSize().width, this.getSize().height, Image.SCALE_SMOOTH);
        //      image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        //      image.getGraphics().drawImage(img, 0, 0 , null);
        //  - Considering the expensive process stated above and the fact that paintComponent()
        //    is called all the time, we will only resize when the panel is resized. That is 
        //    exactly what this huge "if" does: checks if this panel has been resized, and if 
        //    so happens then resizes the image too. The "else" branch is accessed practically
        //    all of the time.
        if(this.width == 0 || this.height == 0 || this.getSize().width != this.width || this.getSize().height != this.height) {
            this.width = this.getSize().width;
            this.height =  this.getSize().height;
            Image img = originalImage.getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH);
            image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
            image.getGraphics().drawImage(img, 0, 0 , null);            
        }       
        g.drawImage(image, 0, 0, this.width, this.height, null);                
    }
   
}