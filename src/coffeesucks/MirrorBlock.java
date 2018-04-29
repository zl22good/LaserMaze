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
public class MirrorBlock extends GameObject 
{
    Image s_UL;
    String pathUL = (dir + "\\lasermaze\\mirrorUL.png");
    Image s_ULb;
    String pathULb = (dir + "\\lasermaze\\bwmirrorUL.png");
    Image s_UR;
    String pathUR = (dir + "\\lasermaze\\mirrorUR.png");
    Image s_URb;
    String pathURb = (dir + "\\lasermaze\\bwmirrorUR.png");
    Image s_DL;
    String pathDL = (dir + "\\lasermaze\\mirrorDL.png");
    Image s_DLb;
    String pathDLb = (dir + "\\lasermaze\\bwmirrorDL.png");
    Image s_DR;
    String pathDR = (dir + "\\lasermaze\\mirrorDR.png");
    Image s_DRb;
    String pathDRb = (dir + "\\lasermaze\\bwmirrorDR.png");
    String snd_spin = (dir + "\\lasermaze\\spin.wav");
    String snd_lift = (dir + "\\lasermaze\\lift.wav");
    int ID = 0;
    boolean HIT = false;
    String myString;
    boolean goal = false;
    int goalDir = 0;
    boolean addPoint = false;
    
    Image retL;
    Image retD;
    Image retU;
    Image retR;
    String pathRL = (dir + "\\lasermaze\\tarL.png");
    String pathRD = (dir + "\\lasermaze\\tarD.png");
    String pathRU = (dir + "\\lasermaze\\tarU.png");
    String pathRR = (dir + "\\lasermaze\\tarR.png");

    /**
     * reflects stuff. same as others, we have a direction and x an y
     * however, since these are what win, we also have a 
     * win direction
     */
    public MirrorBlock(){}

    /**
     * 
     * @param myX
     * @param myY
     * @param d
     * @param l
     * @param myID
     * @param isWin
     * @param winDir 
     */
    public MirrorBlock(int myX, int myY, String d, boolean l,int myID, 
            boolean isWin, int winDir) 
    {
        if (!l)
        {
            s_UL = new ImageIcon(pathUL).getImage();
            s_UR = new ImageIcon(pathUR).getImage();
            s_DL = new ImageIcon(pathDL).getImage();
            s_DR = new ImageIcon(pathDR).getImage();
        }
        else
        {
            s_DR = new ImageIcon(pathDRb).getImage();
            s_UR= new ImageIcon(pathURb).getImage();
            s_DL = new ImageIcon(pathDLb).getImage();
            s_UL = new ImageIcon(pathULb).getImage();
        }
        sprite_index = s_UL;
        
        if (d.equals("UR")) 
        {
            sprite_index = s_UR;
        } 
        else if (d.equals("DR")) 
        {
            sprite_index = s_DR;
            
        } 
        else if (d.equals("UL"))
        {
            sprite_index = s_UL;
        } 
        else if (d.equals("DL")) 
        {
            sprite_index = s_DL;
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
        myString = d;
        
        goal = isWin;
       
        retL = new ImageIcon(pathRL).getImage();
        retR = new ImageIcon(pathRR).getImage();
        retU = new ImageIcon(pathRU).getImage();
        retD = new ImageIcon(pathRD).getImage();
        
        if (sprite_index == s_DL)
        {
            goalDir = 2;
        }
        if (sprite_index == s_DR)
        {
            goalDir = 1;
        }
        if (sprite_index == s_UR)
        {
            goalDir = 1;
        }
        if (sprite_index == s_UL)
        {
            goalDir = 2;
        }
        
        if (goal)
        {
            if (goalDir ==1)
            {
                topSpr = retL;
            }
            if (goalDir ==2)
            {
                topSpr = retR;
            }
            if (goalDir ==3)
            {
                topSpr = retU;
            }
            if (goalDir ==4)
            {
                topSpr = retD;
            }
        }
    }

    public void clicked() 
    {
        if (!inTransit) 
        {
            if (!selected) 
            {
               if (!goal)
               {
                    playSound(snd_spin, false);

                    if (sprite_index == s_DR) 
                    {
                        sprite_index = s_DL;
                    } 
                    else if (sprite_index == s_DL) 
                    {
                        sprite_index = s_UL;
                    } 
                    else if (sprite_index == s_UL) 
                    {
                        sprite_index = s_UR;
                    } 
                    else if (sprite_index == s_UR) 
                    {
                        sprite_index = s_DR;
                    }
                }
               
                if (sprite_index == s_DL)
                {
                     goalDir = 2;
                }
                if (sprite_index == s_DR)
                {
                     goalDir = 1;
                }
                if (sprite_index == s_UR)
                {
                     goalDir = 1;
                }
                if (sprite_index == s_UL)
                {
                     goalDir = 2;
                }
        
                if (goal)
                {
                    if (goalDir ==1)
                    {
                        topSpr = retL;
                    }
                    if (goalDir ==2)
                    {
                        topSpr = retR;
                    }
                    if (goalDir ==3)
                    {
                        topSpr = retU;
                    }
                    if (goalDir ==4)
                    {
                        topSpr = retD;
                    }
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
        String s = "";
        if (HIT)
        s = "HIT";
        else
        s ="NOPE";
        return s;
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
        if (sprite_index == s_UL)
        {
            return 1;
        }
        if (sprite_index == s_UR)
        {
            return 2;
        }
        if (sprite_index == s_DL)
        {
            return 3;
        }
        if (sprite_index == s_DR)
        {
            return 4;
        }
        
        return 10;
    }
}
