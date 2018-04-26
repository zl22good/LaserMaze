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
public class EndGameText extends GameObject
{
    String bg_win = dir + "\\lasermaze\\victory.wav";
    String bg_lose = dir + "\\lasermaze\\lose.wav";
    Image s_win;
    Image s_lose;
    String pathW = (dir + "\\lasermaze\\win.png");
    String pathL = (dir + "\\lasermaze\\failed.png");
   
    /**
     * 
     */
    public EndGameText()
    {
        x= 0;
        y = 233;
        visible = false;
        s_win = new ImageIcon(pathW).getImage();
        s_lose = new ImageIcon(pathL).getImage();
        sprite_index = s_lose;
    }
    
    /**
     * 
     */
    public void clicked(){}
    
    /**
     * 
     * @return 
     */
    public String toString(){return x+ " " + y;}
    
    /**
     * 
     */
    public void colEvent(){}
    
    /**
     * 
     */
    public void rClicked(){}
    
    /**
     * 
     * @param s
     * @param win 
     */
    public void setVis(boolean s, boolean win)
    {
        visible = s;
        if (win)
        {
            sprite_index = s_win;
        }
        else
        {
            sprite_index = s_lose;
        }
    }
}
