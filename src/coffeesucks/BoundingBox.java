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
public class BoundingBox extends GameObject
{
    boolean HIT;
    int ID ;
    
    /**
     * 
     * @param x1
     * @param y1
     * @param xw
     * @param yh 
     */
    public BoundingBox(int x1, int y1, int xw, int yh)
    {
        HIT = false;
        x = x1;
        y = y1;

        hitboxWidth = xw;
        hitboxHeight = yh;
        mask = new Rectangle2D.Double(x, y, hitboxWidth, hitboxHeight);
    }
    
    /**
     * 
     */
     public void clicked() {}
         
     /**
      * 
      */
     public void rClicked() 
     {
     }

     /**
      * 
      * @return 
      */
    public String toString() 
    {
        return x + " " + y;
    }
    
    /**
     * 
     */
    public void colEvent() 
    {
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
}
