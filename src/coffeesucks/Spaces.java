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
public class Spaces extends GameObject  {
    Image test;
    String snd_spin = (dir+ "\\lasermaze\\spin.wav");
    String testPath = (dir+ "\\lasermaze\\highlight.png");
    int ID;
    

    
public Spaces(int myX, int myY, int myID){
        x = myX;
        y = myY;
         test = new ImageIcon(testPath).getImage();
         sprite_index = test;
        hitboxWidth = 64;
        hitboxHeight = 64;
        mask =  new Rectangle2D.Double(x,y,hitboxWidth,hitboxHeight);
        smallmask =  new Rectangle2D.Double(x,y,hitboxWidth,hitboxHeight);
        visible = false;
        ID = myID;
            //yoffset = 25;
        
}
    
    public void clicked(){
    playSound(snd_spin,false);
    }
    
    public int[] getID(){
        int[] p = new int[2];
        
        if (ID == 1){
         p[0] = 0;
         p[1] = 0;
            return p ;
        }
        if (ID == 2){
         p[0] = 1;
         p[1] = 0;
            return p ;
        }
        if (ID == 3){
         p[0] = 2;
         p[1] = 0;
            return p ;
        }
        if (ID == 4){
         p[0] = 3;
         p[1] = 0;
            return p ;
        }
        if (ID == 5){
         p[0] = 4;
         p[1] = 0;
            return p ;
        }
        if (ID == 6){
         p[0] = 0;
         p[1] = 1;
            return p ;
        }
        if (ID == 7){
         p[0] = 1;
         p[1] = 1;
            return p ;
        }
        if (ID == 8){
         p[0] = 2;
         p[1] = 1;
            return p ;
        }
        if (ID == 9){
         p[0] = 3;
         p[1] = 1;
            return p ;
        }
        if (ID == 10){
         p[0] = 4;
         p[1] = 1;
            return p ;
        }
        if (ID == 11){
         p[0] = 0;
         p[1] = 2;
            return p ;
        }
        if (ID == 12){
         p[0] = 1;
         p[1] = 2;
            return p ;
        }
        if (ID == 13){
         p[0] = 2;
         p[1] = 2;
            return p ;
        }
        if (ID == 14){
         p[0] = 3;
         p[1] = 2;
            return p ;
        }
        if (ID == 15){
         p[0] = 4;
         p[1] = 2;
            return p ;
        }
        if (ID == 16){
         p[0] = 0;
         p[1] = 3;
            return p ;
        }
        if (ID == 17){
         p[0] = 1;
         p[1] = 3;
            return p ;
        }
        if (ID == 18){
         p[0] = 2;
         p[1] = 3;
            return p ;
        }
        if (ID == 19){
         p[0] = 3;
         p[1] = 3;
            return p ;
        }
        if (ID == 20){
         p[0] = 4;
         p[1] = 3;
            return p ;
        }
        if (ID == 21){
         p[0] = 0;
         p[1] = 4;
            return p ;
        }
        if (ID == 22){
         p[0] = 1;
         p[1] = 4;
            return p ;
        }
        if (ID == 23){
         p[0] = 2;
         p[1] = 4;
            return p ;
        }
        if (ID == 24){
         p[0] = 3;
         p[1] = 4;
            return p ;
        }
        if (ID == 25){
         p[0] = 4;
         p[1] = 4;
            return p ;
        }
        return p; 
    }
    
    public int getRawID(){
        return ID;
    }
    
    public void rClicked(){}
    public String toString(){return x+ " " + y;}
    public void colEvent(){
    
    }
       
}
