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
public class DoubleMirror extends GameObject 
{
    Image s_L;
    String pathL = (dir + "\\lasermaze\\doublemirrorL.png");
    Image s_Lb;
    String pathLb = (dir + "\\lasermaze\\splitLbw.png");
    Image s_R;
    String pathR = (dir + "\\lasermaze\\doublemirrorR.png");
    Image s_Rb;
    String pathRb = (dir + "\\lasermaze\\splitRbw.png");
    String snd_spin = (dir + "\\lasermaze\\spin.wav");
    String snd_lift = (dir + "\\lasermaze\\lift.wav");
    int ID = 0;
    boolean HIT = false;
    String myString;

    /**
     * 
     * @param myX
     * @param myY
     * @param d
     * @param l
     * @param myID 
     */
    public DoubleMirror(int myX, int myY, String d, boolean l, int myID) 
    {
        if (!l) 
        {

            s_L = new ImageIcon(pathL).getImage();
            s_R = new ImageIcon(pathR).getImage();
        } 
        else 
        {
            s_L = new ImageIcon(pathLb).getImage();
            s_R = new ImageIcon(pathRb).getImage();
        }
        sprite_index = s_L;

        if (d.equals("L")) 
        {
            sprite_index = s_L;
        } 
        else if (d.equals("R")) 
        {
            sprite_index = s_R;
        }

        x = myX;
        y = myY;
        hitboxWidth = 64;
        hitboxHeight = 64;
        mask = new Rectangle2D.Double(x, y, hitboxWidth, hitboxHeight);
        smallmask =  new Rectangle2D.Double(x,y,hitboxWidth,hitboxHeight);
        locked = l;
        startx = myX;
        starty = myY;
        //yoffset = 25;
        ID = myID;
        myString = d;
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

    public String toString() 
    {
        return "wut";
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
