/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeesucks;

import java.awt.*;
import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import javax.sound.sampled.*;
import java.io.*;

/**
 *
 * @author Mike
 */
abstract class GameObject {

    int x = 0;
    int y = 0;
    int gravity;
    double vspeed = 0;
    double hspeed = 0;
    Image sprite_index = null;
    boolean inTransit = false;
    int nextx;
    int nexty;
    int hitboxWidth;
    int hitboxHeight;
    public String dir = System.getProperty("user.dir");
    //double scale = 1;
    boolean colUp = false;
    boolean colDown = false;
    boolean colRight = false;
    boolean colLeft = false;
    ArrayList<GameObject> cols = new ArrayList<GameObject>();
    ArrayList<GameObject> smallcols = new ArrayList<GameObject>();
    boolean visible = true;
    String debugData;
    int widthX;
    int heightY;
    Rectangle2D.Double mask;
    Rectangle2D.Double smallmask;
    Boolean hasPlayedSFX = false; //useful for checking to not keep playing sounds until another event sets it
    boolean locked = false;
    int mySpeed;
    boolean selected = false;
    int startx;
    int starty;
    int xoffset = 0;
    int yoffset = 0;
    boolean HIT;
    AudioInputStream nowplaying;
    Image topSpr;

    public void jumpX(int newX) {
        x = newX;
    }

    public void jumpY(int newY) {
        y = newY;
    }

    public void setVSpeed(double spd) {
        vspeed = spd;
    }

    public void setHSpeed(double spd) {
        hspeed = spd;
    }

    public void setSpriteIndex(Image spr) {
        sprite_index = spr;
        BufferedImage mySprite = ((BufferedImage) sprite_index);
        hitboxWidth = mySprite.getWidth();
        hitboxHeight = mySprite.getHeight();
    }

    /**
     * <p>
     * Make sure if your locations are odd you use an odd speed, or likewise use
     * an even
     */
    public void moveTowardsPoint(int newx, int newy, int speed) {
        nextx = newx;
        nexty = newy;
        inTransit = true;
        mySpeed = speed;

        if (x < newx) {

            hspeed = speed;
        }
        if (x > newx) {

            hspeed = speed * -1;
        }
        if (y < newy) {

            vspeed = speed;
        }
        if (y > newy) {

            vspeed = speed * -1;
        }
    }

    public void tripCheck() {
        if (inTransit){
        if ((x >= nextx-4) && (x <= nextx+4)) {
            hspeed = 0;
            selected = false;
        }
        if ((y >= nexty-4) && (y <= nexty+4)) {
            vspeed = 0;
            selected = false;
        }
        if (((y >= nexty-4) && (y <= nexty+4)) && ((x >= nextx-4) && (x <= nextx+4))){
            inTransit = false;
        }
        }
        
    }

    public boolean hitBoxCheck(int x1, int y1) {
        if (((x1 > x) && (x1 < (x + hitboxWidth))) && ((y1 > y) && (y1 < (y + hitboxHeight)))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isMoving() {
        return inTransit;
    }

    /**
     * this is what occurs every frame for the object
     */
    public void step() {
        
        //make moves
        if (this.vspeed != 0) {
            this.jumpY(this.y += this.vspeed);
        }
        if (this.hspeed != 0) {
            this.jumpX(this.x += this.hspeed);
        }

        //check if a destination has been reached
        this.tripCheck();
        //check collisions
        this.colEvent();
        debugData = (x + ", " + y + ", " + (x+hitboxWidth) + ", " + (y+hitboxHeight) + ", " + hitboxWidth + ", " + hitboxHeight );
        mask =  new Rectangle2D.Double(x+xoffset,y+yoffset,hitboxWidth,hitboxHeight);
        int newX = x+(hitboxWidth/2);
        int newY = y+(hitboxHeight/2);
        smallmask = new Rectangle2D.Double(x+28,y+28,10,10);
        
    }

    public ArrayList<GameObject> collisionCheck(ArrayList<GameObject> feedme) {
        cols.clear();// start fresh every frame
        for (GameObject curr : feedme) {
        if(mask.intersects(curr.mask)) 
            if(!cols.contains(curr))
            cols.add(curr);
        }
        return cols;
    }
public ArrayList<GameObject> smallCollisionCheck(ArrayList<GameObject> feedme) {
        smallcols.clear();// start fresh every frame
        for (GameObject curr : feedme) {
        if(mask.intersects(curr.mask)) 
            if(!smallcols.contains(curr))
            smallcols.add(curr);
        }
        return smallcols;
    }
    
    public abstract void colEvent();
    
    public void switchVisible(){
        if (visible)
            visible = false;
        else
            visible = true;
    }
    
    public void playSound(String soundfile, boolean loop){
        try
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundfile).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            nowplaying = audioInputStream;
            if (!loop)
            clip.start();
            else
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch (Exception ex){
        }   
    }
    
    public void stopSound(String soundfile){
        try{
        nowplaying.close();
        }
        catch (Exception ex){
        }  
    }
    
    public abstract String toString();

    public abstract void clicked();
    public abstract void rClicked();
    
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    
    public double getHSpeed(){
        return hspeed;
    }
    public double getVSpeed(){
        return vspeed;
    }
    /*
    public BufferedImage scaleProcess(BufferedImage imageToScale, int dWidth, int dHeight) {
        BufferedImage scaledImage = null;
        if (imageToScale != null) {
            scaledImage = new BufferedImage(dWidth, dHeight, imageToScale.getType());
            Graphics2D graphics2D = scaledImage.createGraphics();
            graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
            graphics2D.dispose();
        }
        return scaledImage;
    }
   
    public void setScale(double s) {
        BufferedImage bi = (BufferedImage)sprite_index;
        while (scale < s){
        scale+=.1;
        int w = ((int)(bi.getWidth()*scale));
        int h = ((int)(bi.getHeight()*scale));
        sprite_index = scaleProcess(bi,w,h);
        }
        
    }
    
    //TOP RIGHT CORNER CHECKS
        if (((curr.x+curr.hitboxWidth) >= x) && ((curr.x+curr.hitboxWidth) <= (x+hitboxWidth))){ //right side of curr is within the x range of our object  
            if (((curr.y) > y) && ((curr.y) < (y+hitboxHeight))) //it's also within the y of our object
                cols.add(curr);
        }
        //TOP LEFT CORNER CHECKS
        else if (((curr.x) >= x) && ((curr.x) <= (x+hitboxWidth))){ //left side of curr is within the x range of our object  
            if (((curr.y) > y) && ((curr.y) < (y+hitboxHeight))) //it's also within the y of our object
                cols.add(curr);
        }
        //BOTTOM LEFT CORNER CHECKS
        else if (((curr.x) >= x) && ((curr.x) <= (x+hitboxWidth))){ //left side of curr is within the x range of our object  
            if (((curr.y+curr.hitboxHeight) > y) && ((curr.y+curr.hitboxHeight) < (y+hitboxHeight))) //it's also within the y of our object
                cols.add(curr);
        }
        //BOTTOM RIGHT CORNER CHECKS
        else if (((curr.x+curr.hitboxWidth) >= x) && ((curr.x+curr.hitboxWidth) <= (x+hitboxWidth))){ //left side of curr is within the x range of our object  
            if (((curr.y+curr.hitboxHeight) > y) && ((curr.y+curr.hitboxHeight) < (y+hitboxHeight))) //it's also within the y of our object
                cols.add(curr);
        }
        //else{
        //for (int i = 0; i<cols.size();i++){
        //    if (cols.get(i) == curr){
        //        cols.remove(curr);
        //    }
        //}
        //}
            
     */
}
