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
 * @author Mike
 */
public class BoundingBox extends GameObject{
   
    boolean HIT;
    int ID ;
    
    public BoundingBox(int x1, int y1, int xw, int yh){
        HIT = false;
        x = x1;
        y = y1;

        hitboxWidth = xw;
        hitboxHeight = yh;
        mask = new Rectangle2D.Double(x, y, hitboxWidth, hitboxHeight);
    }
    
     public void clicked() {}
          
         
     public void rClicked() {
    }

    public String toString() {
        return x + " " + y;
    }
    
     public void colEvent() {
    }
    
    public int getID(){
        return ID;
    }
    
    public void setID(int i){
        ID = i;
    }
    
    public boolean isHit(){
        return HIT;
    }
    
    public void setHit(boolean h){
        HIT = h;
    }
}
