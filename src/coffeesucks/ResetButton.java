/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeesucks;

import java.awt.Image;
import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;

/**
 *
 * @author Mike, Zach, Luis, Cassie, Grace
 */
public class ResetButton extends GameObject 
{
    String bg_music = dir + "\\lasermaze\\bg.wav";
    Image s_firebutton;
    String pathFB = (dir + "\\lasermaze\\reset.png");
    String snd_reset = (dir+ "\\lasermaze\\reset.wav");
    
    /**
     * the brings it all b b b b b b ack
     * x y are the location
     * @param myX
     * @param myY 
     */
    public ResetButton(int myX, int myY)
    {
        s_firebutton = new ImageIcon(pathFB).getImage();
        sprite_index = s_firebutton;
        x = myX;
        y = myY;
        hitboxWidth = 64;
        hitboxHeight = 64;
        mask =  new Rectangle2D.Double(x,y,hitboxWidth,hitboxHeight);
        smallmask =  new Rectangle2D.Double(x,y,hitboxWidth,hitboxHeight);
    }
    
    /**
     * 
     */
    public void clicked()
    {
        playSound(snd_reset,false);
    }
    
    /**
     * 
     * @return 
     */
    public String toString(){return x+ " " + y;}
    
    /**
     * 
     */
    public void colEvent(){}
    
    /**
     * 
     */
    public void rClicked(){} 
}

