/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeesucks;
import java.awt.*;
import java.awt.image.*;
import javax.swing.ImageIcon;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import javax.imageio.ImageIO;
import java.io.*;
/**
 *
 * @author Mike
 */
public class Mario extends GameObject implements KeyListener {
    
    Image s_marioRun_right = null;
    Image s_marioRun_left = null;
    int gravity = 1;
    int coins = 0;
    String snd_coin = (dir+ "\\art\\sfx\\smw_coin.wav");
    String snd_run = (dir+ "\\art\\sfx\\yahoo.wav");
    
    public Mario(Image spr,int myX,int myY){
        String pathMario = (dir + "\\art\\sprites\\mario.gif");
        s_marioRun_right = new ImageIcon(pathMario).getImage();
        String pathMarioL = (dir + "\\art\\sprites\\marioleft.gif");
        s_marioRun_left = new ImageIcon(pathMarioL).getImage();
        sprite_index = spr;
        x= myX;
        y= myY;
        hitboxHeight = 256;
        hitboxWidth = 256;
        
    }
    
    public Mario(int myX,int myY){
       
//sets image for 
        String pathMario = (dir + "\\art\\sprites\\mario.gif");
        s_marioRun_right = new ImageIcon(pathMario).getImage();
        String pathMarioL = (dir + "\\art\\sprites\\marioleft.gif");
        s_marioRun_left = new ImageIcon(pathMarioL).getImage();
        sprite_index = s_marioRun_right;
        x= myX;
        y= myY;
        hitboxHeight = 256;
        hitboxWidth = 256;
        mask =  new Rectangle2D.Double(x,y,hitboxWidth,hitboxHeight);
    }
    
    public void keyPressed( KeyEvent e ) { 
        
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            sprite_index = s_marioRun_right;
            hspeed = 7;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT){
            sprite_index = s_marioRun_left;
            hspeed = -7;
        }
        else if (e.getKeyCode() == KeyEvent.VK_SHIFT){
            if (Math.abs(hspeed) < 15){
            if (!hasPlayedSFX){
                playSound(snd_run,false);
                hasPlayedSFX = true;
            }
            hspeed = hspeed * 2;
            }
        }
       
    }

    public void keyReleased( KeyEvent e ) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            hspeed = 0;
        }
         if (e.getKeyCode() == KeyEvent.VK_LEFT){
             hspeed = 0;
        }
         else if (e.getKeyCode() == KeyEvent.VK_SHIFT){
            hspeed = hspeed / 2;
            hasPlayedSFX = false;
        }
    }

    public void keyTyped( KeyEvent e ) {
       
    }
        @Override
    public void colEvent(){
        for (GameObject curr:cols){
            if (curr instanceof Coin)
            {
                if (curr.visible)
                {
                    curr.switchVisible();
                    playSound(snd_coin,false);
                    coins++;
                }
            }
        }
    }
    
    public String toString(){
        String c = "Coins: " + coins;
        return c;
    }
    
    public void clicked(){}
    
    public void rClicked(){}
    
}
