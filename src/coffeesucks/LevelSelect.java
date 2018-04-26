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
public class LevelSelect extends GameObject 
{
    String snd_select = dir + "\\lasermaze\\select.wav";
    String snd_menu = dir + "\\lasermaze\\menu.wav";
    Image s_card1;
    String pathc1 = (dir + "\\lasermaze\\card1.png");
    Image s_card2;
    String pathc2 = (dir + "\\lasermaze\\card2.png");
    int level;
    
    /**
     * 
     * @param myX
     * @param myY
     * @param levelNum 
     */
    public LevelSelect(int myX, int myY,int levelNum)
    { 
        s_card1 = new ImageIcon(pathc1).getImage();
        s_card2 = new ImageIcon(pathc2).getImage();
        if (levelNum == 1)
        {
            sprite_index = s_card1;
        }
        if (levelNum == 2)
        {
            sprite_index = s_card2;
        }
        x = myX;
        y = myY;
        hitboxWidth = 276;
        hitboxHeight = 423;
        mask =  new Rectangle2D.Double(x,y,hitboxWidth,hitboxHeight);
        level = levelNum;
    }
    
    /**
     * 
     */
    public void clicked()
    {
        playSound(snd_select,false);
        stopSound(snd_select);
    }
    
    /**
     * 
     * @return 
     */
    public String toString(){return ""+ level;}
    
    /**
     * 
     */
    public void colEvent(){}
    
    /**
     * 
     */
    public void rClicked(){}
}
