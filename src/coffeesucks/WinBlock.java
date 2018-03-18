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

import java.awt.Image;

/**
 *
 * @author Mike
 */
public class WinBlock extends GameObject {

    Image s_tar;
    String pathtar = (dir + "\\lasermaze\\hitwin.png");
    Image s_R;
    String pathR = (dir + "\\lasermaze\\tarR.png");
    Image s_U;
    String pathU = (dir + "\\lasermaze\\tarU.png");
    Image s_D;
    String pathD = (dir + "\\lasermaze\\tarD.png");
    int ID;
    boolean HIT;
    boolean addPoint = false;
    boolean active = false;

    public WinBlock(int myX, int myY,int w,int h) {

        s_tar = new ImageIcon(pathtar).getImage();

        x = myX;
        y = myY;
        hitboxWidth = w;
        hitboxHeight = h;
        mask = new Rectangle2D.Double(x, y, hitboxWidth, hitboxHeight);
        smallmask =  new Rectangle2D.Double(x,y,hitboxWidth,hitboxHeight);
        startx = myX;
        starty = myY;
        //yoffset = 25;
    }

    public void clicked() {
    }

    public void rClicked() {
    }

    public String toString() {
        if (!HIT) {
            return "VIRGIN";
        } else {
            return "OOF";
        }
    }

    public void colEvent() {
        for (GameObject curr : cols) {
            if (active){
            if (curr instanceof Laser) {
                addPoint = true;
            }
            }
        }
        for (GameObject curr : cols) {
            if (curr instanceof MirrorBlock) {
                if (((MirrorBlock)curr).goal){
                    active = true;
                }
            }
        }
    }

    public int getID() {
        return ID;
    }

    public void setID(int i) {
        ID = i;
    }

    public boolean isHit() {
        return HIT;
    }

    public void setHit(boolean h) {
        HIT = h;
    }

}
