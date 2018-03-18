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
public class FireButton extends GameObject{
    String bg_music = dir + "\\lasermaze\\bg.wav";
    Image s_firebutton;
    String pathFB = (dir + "\\lasermaze\\firebutton.png");
    String snd_blip = (dir+ "\\lasermaze\\blip.wav");
    
    
    public FireButton(int myX, int myY){
        //playSound(bg_music,true);             //***************BGM*************
        s_firebutton = new ImageIcon(pathFB).getImage();
        sprite_index = s_firebutton;
        x = myX;
        y = myY;
        hitboxWidth = 64;
        hitboxHeight = 64;
        mask =  new Rectangle2D.Double(x,y,hitboxWidth,hitboxHeight);
    }
    
    public void clicked(){
        playSound(snd_blip,false);
    }
    public String toString(){return x+ " " + y;}
    public void colEvent(){}
    public void rClicked(){}
    
}
