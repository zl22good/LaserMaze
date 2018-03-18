/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

import java.awt.Image;
import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;

/**
 *
 * @author Mike
 */
public class MenuButton extends GameObject {

     String snd_select = dir + "\\lasermaze\\select.wav";
    Image s_button;
    String pathb1 = (dir + "\\lasermaze\\menu.png");

    
    public MenuButton(int myX,int myY){
    
        s_button = new ImageIcon(pathb1).getImage();
        sprite_index = s_button;
        x = myX;
        y = myY;
        hitboxWidth = 64;
        hitboxHeight = 64;
        mask =  new Rectangle2D.Double(x,y,hitboxWidth,hitboxHeight);
        smallmask =  new Rectangle2D.Double(x,y,hitboxWidth,hitboxHeight);
    }
    
    public void clicked(){
        playSound(snd_select,false);
    }
    public String toString(){return "";}
    public void colEvent(){}
    public void rClicked(){}
}
