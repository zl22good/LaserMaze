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
 * draws an expolsion at x y
 */
public class Explosion extends GameObject
{
    Image s_boom;
    String pathFB = (dir + "\\lasermaze\\boom.png");
    String snd_boom = (dir+ "\\lasermaze\\explode.wav");
    
    /**
     * 
     * @param myX
     * @param myY 
     */
    public Explosion(int myX, int myY)
    {
        s_boom = new ImageIcon(pathFB).getImage();
        sprite_index = s_boom;
        x = myX;
        y = myY;
        hitboxWidth = 64;
        hitboxHeight = 64;
        mask =  new Rectangle2D.Double(x,y,hitboxWidth,hitboxHeight);
        smallmask =  new Rectangle2D.Double(x,y,hitboxWidth,hitboxHeight);
        playSound(snd_boom,false);
    }
    
    /**
     * 
     */
    public void clicked()
    {
    }
    
    /**
     * 
     * @return 
     */
    public String toString(){return x+ " " + y;}
    
    /**
     * 
     */
    public void colEvent()
    {
        if (visible) 
        {
            for (GameObject curr : cols) 
            {
                if (curr instanceof WinBlock) 
                {
                    visible = false;
                }
            }
        }
    }
    
    /**
     * 
     */
    public void rClicked(){}
}
