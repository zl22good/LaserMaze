/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeesucks;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Image;

/**
 *
 * @author Mike, Zach, Luis, Cassie, Grace
 */
public class LaserBlock extends GameObject 
{
    Image s_U;
    String pathU = (dir + "\\lasermaze\\laserU.png");
    Image s_Ub;
    String pathUb = (dir + "\\lasermaze\\laserUbw.png");
     Image s_R;
    String pathR = (dir + "\\lasermaze\\laserR.png");
     Image s_Rb;
    String pathRb = (dir + "\\lasermaze\\laserRbw.png");
     Image s_D;
    String pathD = (dir + "\\lasermaze\\laserD.png");
    Image s_Db;
    String pathDb = (dir + "\\lasermaze\\laserDbw.png");
     Image s_L;
    String pathL = (dir + "\\lasermaze\\laserL.png");
    Image s_Lb;
    String pathLb = (dir + "\\lasermaze\\laserLbw.png");
    String snd_spin = (dir + "\\lasermaze\\spin.wav");
    String snd_lift = (dir + "\\lasermaze\\lift.wav");
    int ID = 0;
    
    /**
     * fires a lazer, x and y give it a location and d is direction
     * @param myX
     * @param myY
     * @param d
     * @param l
     * @param myID 
     */
    public LaserBlock(int myX, int myY, String d,boolean l, int myID)
    {
        if (!l)
        {
            s_L = new ImageIcon(pathL).getImage();
            s_R = new ImageIcon(pathR).getImage();
            s_D = new ImageIcon(pathD).getImage();
            s_U = new ImageIcon(pathU).getImage();
        }
        else
        {
            s_L = new ImageIcon(pathLb).getImage();
            s_R = new ImageIcon(pathRb).getImage();
            s_D = new ImageIcon(pathDb).getImage();
            s_U = new ImageIcon(pathUb).getImage();
        }
        
        if (d.equals("U")) 
        {
            sprite_index = s_U;
        } 
        else if (d.equals("D")) 
        {
            sprite_index = s_D; 
        } 
        else if (d.equals("L")) 
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
        ID = myID;
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

                if (sprite_index == s_D) 
                {
                    sprite_index = s_L;
                } 
                else if (sprite_index == s_L) 
                {
                    sprite_index = s_U;
                } 
                else if (sprite_index == s_U) 
                {
                    sprite_index = s_R;
                } 
                else if (sprite_index == s_R) 
                {
                    sprite_index = s_D;
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
        if (sprite_index == s_D)
        {
            return "D";
        }
        if (sprite_index == s_U)
        {
            return "U";
        }
        if (sprite_index == s_L)
        {
            return "L";
        }
        if (sprite_index == s_R)
        {
            return "R";
        }
        return "NULL";
    }
    
    /**
     * 
     */
    public void colEvent(){}
    
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
    public int myNumber()
    {
        if (sprite_index == s_U)
        {
            return 13;
        }
        if (sprite_index == s_R)
        {
            return 12;
        }
        if (sprite_index == s_L)
        {
            return 11;
        }
        if (sprite_index == s_D)
        {
            return 14;
        }
        return 10;
    }
}
