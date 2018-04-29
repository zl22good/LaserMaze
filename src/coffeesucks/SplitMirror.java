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
public class SplitMirror extends GameObject
{
    Image s_L;
    String pathL = (dir + "\\lasermaze\\splitL.png");
    Image s_R;
    String pathR = (dir + "\\lasermaze\\splitR.png");
    String snd_spin = (dir + "\\lasermaze\\spin.wav");
    String snd_lift = (dir + "\\lasermaze\\lift.wav");
    int ID = 0;
    boolean HIT = false;
    String myString;
    String createNew = "";
    
    /**
     * s mirror that splits the beam
     * x and y are location
     * @param myX
     * @param myY 
     */
    public SplitMirror(int myX, int myY)
    {
        s_L = new ImageIcon(pathL).getImage();
        s_R = new ImageIcon(pathR).getImage();
       
        sprite_index = s_L;

        x = myX;
        y = myY;
        hitboxWidth = 64;
        hitboxHeight = 64;
        mask = new Rectangle2D.Double(x, y, hitboxWidth, hitboxHeight);
        smallmask =  new Rectangle2D.Double(x,y,hitboxWidth,hitboxHeight);
        startx = myX;
        starty = myY;
    }

    /**
     * 
     */
    public void clicked() 
    {
        if (!inTransit) 
        {
            if (!selected) 
            {
                playSound(snd_spin, false);

                if (sprite_index == s_R) 
                {
                    sprite_index = s_L;
                } 
                else if (sprite_index == s_L) 
                {
                    sprite_index = s_R;
                }
            }
        }
    }

    /**
     * 
     */
    public void rClicked() 
    {
        playSound(snd_lift, false);
    }

    /**
     * 
     * @return 
     */
    public String toString() 
    {
       if (HIT)
       {
           return "oof " + createNew; 
       }
       else
       {
           return "wait no " + createNew;
       }
    }

    /**
     * 
     */
    public void colEvent() 
    {
        for (GameObject curr : cols) 
        {
            if (curr instanceof Laser) 
            {
                HIT = true;
            }
        }
    }

    /**
     * 
     * @return 
     */
    public int getID() 
    {
        return ID;
    }

    /**
     * 
     * @param i 
     */
    public void setID(int i) 
    {
        ID = i;
    }

    /**
     * 
     * @return 
     */
    public boolean isHit() 
    {
        return HIT;
    }

    /**
     * 
     * @param h 
     */
    public void setHit(boolean h) 
    {
        HIT = h;
    }

    /**
     * 
     * @return 
     */
    public int myNumber() 
    {
        if (sprite_index == s_L) 
        {
            return 1;
        }
        if (sprite_index == s_R) 
        {
            return 2;
        }
        return 0;
    }
}
