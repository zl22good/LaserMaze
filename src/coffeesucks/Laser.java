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
import java.util.ArrayList;

/**
 *
 * @author Mike
 */
public class Laser extends GameObject {

    Image s_hori;
    String pathH = (dir + "\\lasermaze\\laserhori.png");
    Image s_vert;
    String pathV = (dir + "\\lasermaze\\laservert.png");
    
    Image s_vertoff;
    String pathVOFF = (dir + "\\lasermaze\\laserreflectrightup.png");
    String snd_reflect = (dir + "\\lasermaze\\reflect.wav");
    String snd_win = (dir + "\\lasermaze\\win.wav");
    int speed;
    boolean explode = false;
    int colX = 0;
    int colY = 0;

    public Laser(int myX, int myY, int H, int V, int s) {

        s_hori = new ImageIcon(pathH).getImage();
        s_vert = new ImageIcon(pathV).getImage();
        s_vertoff = new ImageIcon(pathVOFF).getImage();
        x = myX;
        y = myY;
        hspeed = H;
        vspeed = V;
        if (hspeed != 0) {
            sprite_index = s_hori;
        }
        if (vspeed != 0) {
            sprite_index = s_vert;
        }
        hitboxHeight = 10;
        hitboxWidth = 10;
        xoffset = 32;
        yoffset = 32;
        mask = new Rectangle2D.Double(x, y, hitboxWidth, hitboxHeight);
        smallmask = new Rectangle2D.Double(x, y, hitboxWidth, hitboxHeight);
        speed = s;
        startx = x;
        starty = y;
    }

    public void clicked() {

    }

    public void rClicked() {

    }

    public String toString() {
        String r = "v " + vspeed + " H " + hspeed;
        return r;
    }

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
        debugData = (x + ", " + y + ", " + (x + hitboxWidth) + ", " + (y + hitboxHeight) + ", " + hitboxWidth + ", " + hitboxHeight);
        mask = new Rectangle2D.Double(x + xoffset, y + yoffset, hitboxWidth, hitboxHeight);
        int newX = x + (hitboxWidth / 2);
        int newY = y + (hitboxHeight / 2);
        smallmask = new Rectangle2D.Double(x + 28, y + 28, 10, 10);
    }

    public void colEvent() {
        if (visible) {
            for (GameObject curr : smallcols) {
                if (curr instanceof WinBlock) {
                    if (((WinBlock) curr).active) {
                        explode = false;
                        playSound(snd_win, false);
                        jumpX(-999);
                        vspeed = 0;
                        hspeed = 0;
                    }
                }

                /* if (curr instanceof WinBlock) {
                    if (((WinBlock) curr).isHit() == false) {
                        
                        if (((WinBlock) curr).whichDir() == 1) {
                            if (hspeed > 0){
                                playSound(snd_win, false);
                                visible = false;
                            
                            }
                        }
                        if (((WinBlock) curr).whichDir() == 2) {
                            if (hspeed < 0){
                            playSound(snd_win, false);
                                visible = false;
                            
                            }
                        }
                        if (((WinBlock) curr).whichDir() == 3) {
                            if (vspeed > 0){
                            playSound(snd_win, false);
                                visible = false;
                            
                            }
                        }
                        if (((WinBlock) curr).whichDir() == 4) {
                            if (vspeed < 0){
                            playSound(snd_win, false);
                                visible = false;
                            
                            }
                        }
                    }
                    }
                 */
                if (curr instanceof MirrorBlock) {
                    if (((MirrorBlock) curr).isHit() == false) {
                        playSound(snd_reflect, false);
                        if (((MirrorBlock) curr).myNumber() == 1) {
                            if (hspeed > 0) {
                                setHSpeed(0);
                                sprite_index = s_vertoff;

                                setVSpeed(-1 * speed);
                            } else if (vspeed > 0) {
                                setHSpeed(-1 * speed);
                                sprite_index = s_hori;

                                setVSpeed(0);
                            } else {
                                setHSpeed(0);
                                explode = true;
                    colX = x;
                    colY = y;
                                jumpX(-100);
                                visible = false;
                                setVSpeed(0);
                            }
                        }
                        if (((MirrorBlock) curr).myNumber() == 2) {
                            if (hspeed < 0) {
                                setHSpeed(0);
                                sprite_index = s_vert;

                                setVSpeed(-1 * speed);
                            } else if (vspeed > 0) {
                                setHSpeed(speed);
                                sprite_index = s_hori;

                                setVSpeed(0);
                            } else {
                                setHSpeed(0);
                                explode = true;
                    colX = x;
                    colY = y;
                                jumpX(-100);
                                visible = false;
                                setVSpeed(0);
                            }
                        }
                        if (((MirrorBlock) curr).myNumber() == 3) {
                            if (hspeed > 0) {
                                setHSpeed(0);
                                sprite_index = s_vertoff;

                                setVSpeed(speed);
                            } else if (vspeed < 0) {
                                setHSpeed(-1 * speed);
                                sprite_index = s_hori;

                                setVSpeed(0);
                            } else {
                                setHSpeed(0);
                                explode = true;
                    colX = x;
                    colY = y;
                                jumpX(-100);
                                visible = false;
                                setVSpeed(0);
                            }
                        }
                        if (((MirrorBlock) curr).myNumber() == 4) {
                            if (hspeed < 0) {
                                setHSpeed(0);
                                sprite_index = s_vert;

                                setVSpeed(speed);
                            } else if (vspeed < 0) {
                                setHSpeed(speed);
                                sprite_index = s_hori;

                                setVSpeed(0);
                            } else {
                                setHSpeed(0);
                                explode = true;
                    colX = x;
                    colY = y;
                                jumpX(-100);
                                visible = false;
                                setVSpeed(0);
                            }
                        }

                    }
                }

                if (curr instanceof DoubleMirror) {
                    if (((DoubleMirror) curr).isHit() == false) {
                        playSound(snd_reflect, false);
                        if (((DoubleMirror) curr).myNumber() == 1) {
                            if (hspeed > 0) {
                                setHSpeed(0);
                                sprite_index = s_vertoff;

                                setVSpeed(1 * speed);
                            } else if (hspeed < 0) {
                                setHSpeed(0);
                                sprite_index = s_vert;

                                setVSpeed(-1 * speed);
                            } else if (vspeed < 0) {
                                setHSpeed(-1 * speed);
                                sprite_index = s_hori;

                                setVSpeed(0);
                            } else if (vspeed > 0) {
                                setHSpeed(1 * speed);
                                sprite_index = s_hori;

                                setVSpeed(0);
                            } else {
                                setHSpeed(0);
                                explode = true;
                    colX = x;
                    colY = y;
                                jumpX(-100);
                                visible = false;
                                setVSpeed(0);
                            }
                        }
                        if (((DoubleMirror) curr).myNumber() == 2) {
                            if (hspeed > 0) {
                                setHSpeed(0);
                                sprite_index = s_vertoff;

                                setVSpeed(-1 * speed);
                            } else if (hspeed < 0) {
                                setHSpeed(0);
                                sprite_index = s_vert;

                                setVSpeed(1 * speed);
                            } else if (vspeed < 0) {
                                setHSpeed(1 * speed);
                                sprite_index = s_hori;

                                setVSpeed(0);
                            } else if (vspeed > 0) {
                                setHSpeed(-1 * speed);
                                sprite_index = s_hori;

                                setVSpeed(0);
                            } else {
                                setHSpeed(0);
                                explode = true;
                    colX = x;
                    colY = y;
                                jumpX(-100);
                                visible = false;
                                setVSpeed(0);
                            }
                        }
                    }
                }
                if (curr instanceof SplitMirror) {
                    if (((SplitMirror) curr).isHit() == false) {
                        playSound(snd_reflect, false);
                        if (this.vspeed > 0) {
                            ((SplitMirror) curr).createNew = "D";
                        }
                        if (this.vspeed < 0) {
                            ((SplitMirror) curr).createNew = "U";
                        }
                        if (this.hspeed > 0) {
                            ((SplitMirror) curr).createNew = "R";
                        }
                        if (this.hspeed < 0) {
                            ((SplitMirror) curr).createNew = "L";
                        }
                        if (((SplitMirror) curr).myNumber() == 1) {
                            if (hspeed > 0) {
                                setHSpeed(0);
                                sprite_index = s_vertoff;

                                setVSpeed(1 * speed);
                            } else if (hspeed < 0) {
                                setHSpeed(0);
                                sprite_index = s_vert;

                                setVSpeed(-1 * speed);
                            } else if (vspeed < 0) {
                                setHSpeed(-1 * speed);
                                sprite_index = s_hori;

                                setVSpeed(0);
                            } else if (vspeed > 0) {
                                setHSpeed(1 * speed);
                                sprite_index = s_hori;

                                setVSpeed(0);
                            } else {
                                setHSpeed(0);
                                explode = true;
                    colX = x;
                    colY = y;
                                jumpX(-100);
                                visible = false;
                                setVSpeed(0);
                            }
                        }
                        if (((SplitMirror) curr).myNumber() == 2) {
                            if (hspeed > 0) {
                                setHSpeed(0);
                                sprite_index = s_vertoff;

                                setVSpeed(-1 * speed);
                            } else if (hspeed < 0) {
                                setHSpeed(0);
                                sprite_index = s_vert;

                                setVSpeed(1 * speed);
                            } else if (vspeed < 0) {
                                setHSpeed(1 * speed);
                                sprite_index = s_hori;

                                setVSpeed(0);
                            } else if (vspeed > 0) {
                                setHSpeed(-1 * speed);
                                sprite_index = s_hori;

                                setVSpeed(0);
                            } else {
                                setHSpeed(0);
                                explode = true;
                    colX = x;
                    colY = y;
                                jumpX(-100);
                                visible = false;
                                setVSpeed(0);
                            }
                        }

                    }
                }
                if (curr instanceof BoundingBox) {
                    setHSpeed(0);
                    explode = true;
                    colX = x;
                    colY = y;
                    jumpX(-100);
                    visible = false;
                    setVSpeed(0);
                }
            }
        }
    }
}

