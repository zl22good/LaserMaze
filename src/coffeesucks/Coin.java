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
/**
 *
 * @author Mike
 */
public class Coin extends GameObject{
    
    Image s_coin;
    
    public Coin(int myX,int myY){
        String path= (dir + "\\art\\sprites\\coin.gif");
        s_coin = new ImageIcon(path).getImage();
        sprite_index = s_coin;
        x= myX;
        y= myY;
        hitboxHeight = 200;
        hitboxWidth = 200;
       mask =  new Rectangle2D.Double(x,y,hitboxWidth,hitboxHeight);
    }
    public void colEvent(){};
    public String toString(){return "null";}
    public void clicked(){}
    public void rClicked(){}
}
