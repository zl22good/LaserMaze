/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeesucks;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import javax.swing.ImageIcon;

/**
 *
 * @author Mike
 */
public class Block extends GameObject {
    
    Image s_block;
    
     public Block(int myX,int myY){
       
        String path= (dir + "\\art\\sprites\\block.gif");
        s_block = new ImageIcon(path).getImage();
        sprite_index = s_block;
        x= myX;
        y= myY;
        hitboxHeight = 100; 
        hitboxWidth = 100;
       mask =  new Rectangle2D.Double(x,y,hitboxWidth,hitboxHeight);
       smallmask =  new Rectangle2D.Double(x,y,hitboxWidth,hitboxHeight);
    }
     public void colEvent(){
         
     }
     public String toString(){return "null";}
     public void clicked(){}
     public void rClicked(){}
}
