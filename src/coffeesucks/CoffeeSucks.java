 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeesucks;

import java.util.ArrayList;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.File;
import java.io.*;
import javax.swing.ImageIcon;
import java.util.Arrays;
import java.awt.geom.Line2D;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author Mike TERMINOLOGY GUIDE ROOM describes the current window being used
 * and drawn PARENTS are abstracts or interfaces.
 */
public class CoffeeSucks extends JPanel implements ActionListener, MouseListener {

    Dimension screenSize = new Dimension(470, 715); //set screen size (locked)
    int FPS = 60; //default is 30,  you can change it but it WILL effect your whole game
    private final Timer timer = new Timer(FPS, this);
    Image ROOM_BACKGROUND = null; //define this for your room, this is the drawn background and should match the dimensions above
    private long totalTime; //all related to framerate
    private long averageTime;
    private int frameCount;
    static String dir = System.getProperty("user.dir");
    ArrayList<GameObject> objs = new ArrayList<>();
    ArrayList<GameObject> titleobjs = new ArrayList<>();
    ArrayList<GameObject> lv2objs = new ArrayList<>();
    Font customFont; //Name all your fonts here, set them in the constructor
    Font bigFont;
    ArrayList<int[]> points = new ArrayList<int[]>();
    int[][] board = new int[5][5];
    int[][] original_board = new int[5][5];
    int[] moveTo = new int[2];
    boolean hasSelected = false;
    int numOfMirrors = 0;
    String snd_slide = (dir + "\\lasermaze\\slide.wav");
    boolean fired = false;
    boolean failCheck = false;
    String nextDir;
    int[] nextPoint;
    boolean spitDebug;
    int numLaser;
    boolean gameOver = false;
    boolean gameWon = false;
    JFrame currentFrame;
    String currentRoom = "";
    static CoffeeSucks mainObj;
    boolean hasPrompted = false;
    int score = 0;
    int numTargets = 0;
    Image s_tar;
    String pathtar = (dir + "\\lasermaze\\hitwin.png");
    String snd_win = (dir + "\\lasermaze\\victory.wav");
    String snd_sd = (dir + "\\lasermaze\\sd.wav");
    boolean easterSD = false;
    
    public CoffeeSucks() throws FontFormatException, IOException {
        //custom font stuff
        String fontpath = dir + "\\lasermaze\\pdark.ttf";
        customFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontpath)).deriveFont(12f);
        bigFont = customFont.deriveFont(24f);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(fontpath)));

        //LASER MAZE STARTS HERE
        int[] p1 = new int[]{57, 222};
        int[] p2 = new int[]{129, 222};
        int[] p3 = new int[]{201, 222};
        int[] p4 = new int[]{274, 222};
        int[] p5 = new int[]{345, 222};
        int[] p6 = new int[]{57, 293};
        int[] p7 = new int[]{129, 293};
        int[] p8 = new int[]{201, 293};
        int[] p9 = new int[]{274, 293};
        int[] p10 = new int[]{345, 293};
        int[] p11 = new int[]{57, 366};
        int[] p12 = new int[]{129, 366};
        int[] p13 = new int[]{201, 366};
        int[] p14 = new int[]{274, 366};
        int[] p15 = new int[]{345, 366};
        int[] p16 = new int[]{57, 438};
        int[] p17 = new int[]{129, 438};
        int[] p18 = new int[]{201, 438};
        int[] p19 = new int[]{274, 438};
        int[] p20 = new int[]{345, 438};
        int[] p21 = new int[]{57, 509};
        int[] p22 = new int[]{129, 509};
        int[] p23 = new int[]{201, 509};
        int[] p24 = new int[]{274, 509};
        int[] p25 = new int[]{345, 509};

        points.add(p1);
        points.add(p2);
        points.add(p3);
        points.add(p4);
        points.add(p5);
        points.add(p6);
        points.add(p7);
        points.add(p8);
        points.add(p9);
        points.add(p10);
        points.add(p11);
        points.add(p12);
        points.add(p13);
        points.add(p14);
        points.add(p15);
        points.add(p16);
        points.add(p17);
        points.add(p18);
        points.add(p19);
        points.add(p20);
        points.add(p21);
        points.add(p22);
        points.add(p23);
        points.add(p24);
        points.add(p25);
        
        s_tar = new ImageIcon(pathtar).getImage();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = 0;
            }
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FontFormatException, IOException {
        CoffeeSucks game = new CoffeeSucks();
        String snd_menu = dir + "\\lasermaze\\menu.wav";
        //game.playSound(snd_menu,true);
        mainObj = game;
        game.titleScreen();

    }

    public void beginnerRoom() {

        JFrame frame = new JFrame("Laser Maze - powered by CoffeeSucksEngine 0.3");
        currentFrame = frame;
        currentRoom = "Beginner";
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //what do we do on close?
        frame.add(this); //makes the paintComponent add
        frame.setResizable(false); //locks size
        frame.requestFocus();
        frame.toFront();

        //add objects 
        FireButton firebutton = new FireButton(360, 625);
        ResetButton reset = new ResetButton(290, 625);
        MenuButton mb = new MenuButton(220, 625);

        /**
         * 0 is a blank spot 1 is UL mirror 2 is UR mirror 3 is DL mirror 4 is
         * DR mirror 11 is L laser 12 is R laser 13 is U laser 14 is D laser 20
         * is a win block
         */
        MirrorBlock mb1 = new MirrorBlock(points.get(18)[0], points.get(18)[1], "DR", true, 19,true, 1);
        WinBlock wb1 = new WinBlock(267, points.get(18)[1],10,64);
        //MirrorBlock mb3 = new MirrorBlock(points.get(19)[0], points.get(19)[1], "DR", false, 20,false,0);
        //board[4][3] = 4;
        //objs.add(mb3);
        board[3][3] = 4;
        

        DoubleMirror mb2 = new DoubleMirror(26, 108, "R", false, 19);
        objs.add(mb2);

        LaserBlock l1 = new LaserBlock(points.get(6)[0], points.get(6)[1], "D", true, 7);
        board[1][1] = 14;
        
        BoundingBox topbb = new BoundingBox(0, 180,2000,30);
        objs.add(topbb);
        
        BoundingBox botbb = new BoundingBox(0, 573,2000,30);
        objs.add(botbb);
        
        BoundingBox leftbb = new BoundingBox(34, 0,14,2000);
        objs.add(leftbb);
        
        BoundingBox rightbb = new BoundingBox(418, 0,14,2000);
        objs.add(rightbb);
        

        nextPoint = points.get(6);

        //adds spaces
        Spaces s1 = new Spaces(points.get(0)[0], points.get(0)[1], 1);
        Spaces s2 = new Spaces(points.get(1)[0], points.get(1)[1], 2);
        Spaces s3 = new Spaces(points.get(2)[0], points.get(2)[1], 3);
        Spaces s4 = new Spaces(points.get(3)[0], points.get(3)[1], 4);
        Spaces s5 = new Spaces(points.get(4)[0], points.get(4)[1], 5);
        Spaces s6 = new Spaces(points.get(5)[0], points.get(5)[1], 6);
        Spaces s7 = new Spaces(points.get(6)[0], points.get(6)[1], 7);
        Spaces s8 = new Spaces(points.get(7)[0], points.get(7)[1], 8);
        Spaces s9 = new Spaces(points.get(8)[0], points.get(8)[1], 9);
        Spaces s10 = new Spaces(points.get(9)[0], points.get(9)[1], 10);
        Spaces s11 = new Spaces(points.get(10)[0], points.get(10)[1], 11);
        Spaces s12 = new Spaces(points.get(11)[0], points.get(11)[1], 12);
        Spaces s13 = new Spaces(points.get(12)[0], points.get(12)[1], 13);
        Spaces s14 = new Spaces(points.get(13)[0], points.get(13)[1], 14);
        Spaces s15 = new Spaces(points.get(14)[0], points.get(14)[1], 15);
        Spaces s16 = new Spaces(points.get(15)[0], points.get(15)[1], 16);
        Spaces s17 = new Spaces(points.get(16)[0], points.get(16)[1], 17);
        Spaces s18 = new Spaces(points.get(17)[0], points.get(17)[1], 18);
        Spaces s19 = new Spaces(points.get(18)[0], points.get(18)[1], 19);
        Spaces s20 = new Spaces(points.get(19)[0], points.get(19)[1], 20);
        Spaces s21 = new Spaces(points.get(20)[0], points.get(20)[1], 21);
        Spaces s22 = new Spaces(points.get(21)[0], points.get(21)[1], 22);
        Spaces s23 = new Spaces(points.get(22)[0], points.get(22)[1], 23);
        Spaces s24 = new Spaces(points.get(23)[0], points.get(23)[1], 24);
        Spaces s25 = new Spaces(points.get(24)[0], points.get(24)[1], 25);

        objs.add(firebutton);
        objs.add(reset);

        objs.add(mb1);
        objs.add(wb1);
        objs.add(mb);

        objs.add(l1);

        objs.add(s1);
        objs.add(s2);
        objs.add(s3);
        objs.add(s4);
        objs.add(s5);
        objs.add(s6);
        objs.add(s7);
        objs.add(s8);
        objs.add(s9);
        objs.add(s10);
        objs.add(s11);
        objs.add(s12);
        objs.add(s13);
        objs.add(s14);
        objs.add(s15);
        objs.add(s16);
        objs.add(s17);
        objs.add(s18);
        objs.add(s19);
        objs.add(s20);
        objs.add(s21);
        objs.add(s22);
        objs.add(s23);
        objs.add(s24);
        objs.add(s25);

        //have after all board changes
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                original_board[i][j] = board[i][j];
            }
        }

        numTargets = 1;
        hasPrompted = false;
        score = 0;
        fired = false;

        //set background
        //frame.addKeyListener(p1); //every object with inputs needs this
        frame.addMouseListener(this);
        frame.setSize(screenSize); //sets size
        frame.setVisible(true);

        timer.start();
    }
    
     public void interRoom() {

        JFrame frame = new JFrame("Laser Maze - powered by CoffeeSucksEngine 0.3");
        currentFrame = frame;
        currentRoom = "Inter";
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //what do we do on close?
        frame.add(this); //makes the paintComponent add
        frame.setResizable(false); //locks size
        frame.requestFocus();
        frame.toFront();

        //add objects 
        FireButton firebutton = new FireButton(360, 625);
        ResetButton reset = new ResetButton(290, 625);
        MenuButton mb = new MenuButton(220, 625);

        /**
         * 0 is a blank spot 1 is UL mirror 2 is UR mirror 3 is DL mirror 4 is
         * DR mirror 11 is L laser 12 is R laser 13 is U laser 14 is D laser 20
         * is a win block
         */
        MirrorBlock mb1 = new MirrorBlock(points.get(4)[0], points.get(4)[1], "DR", true, 5,true, 1);
        WinBlock wb1 = new WinBlock(344, points.get(4)[1],10,64);
        board[4][0] = 4;
        
        DoubleMirror mb2 = new DoubleMirror(points.get(8)[0], points.get(8)[1], "R", true, 9);
        lv2objs.add(mb2);
        board[3][1] = 1;
        
        LaserBlock l1 = new LaserBlock(points.get(0)[0], points.get(0)[1], "R", true, 1);
        board[0][0] = 14;
        
        MirrorBlock mb3 = new MirrorBlock(26, 108, "DR", false, 19,true,1);
        lv2objs.add(mb3);
        
        SplitMirror mb4 = new SplitMirror(90, 108);
        lv2objs.add(mb4);
        
        WinBlock wb2 = new WinBlock(344, points.get(9)[1],10,64);
        lv2objs.add(wb2);
        
        BoundingBox topbb = new BoundingBox(0, 180,2000,30);
        lv2objs.add(topbb);
        
        BoundingBox botbb = new BoundingBox(0, 573,2000,30);
        lv2objs.add(botbb);
        
        BoundingBox leftbb = new BoundingBox(34, 0,14,2000);
        lv2objs.add(leftbb);
        
        BoundingBox rightbb = new BoundingBox(418, 0,14,2000);
        lv2objs.add(rightbb);
        
        nextPoint = points.get(0);

        //adds spaces
        Spaces s1 = new Spaces(points.get(0)[0], points.get(0)[1], 1);
        Spaces s2 = new Spaces(points.get(1)[0], points.get(1)[1], 2);
        Spaces s3 = new Spaces(points.get(2)[0], points.get(2)[1], 3);
        Spaces s4 = new Spaces(points.get(3)[0], points.get(3)[1], 4);
        Spaces s5 = new Spaces(points.get(4)[0], points.get(4)[1], 5);
        Spaces s6 = new Spaces(points.get(5)[0], points.get(5)[1], 6);
        Spaces s7 = new Spaces(points.get(6)[0], points.get(6)[1], 7);
        Spaces s8 = new Spaces(points.get(7)[0], points.get(7)[1], 8);
        Spaces s9 = new Spaces(points.get(8)[0], points.get(8)[1], 9);
        Spaces s10 = new Spaces(points.get(9)[0], points.get(9)[1], 10);
        Spaces s11 = new Spaces(points.get(10)[0], points.get(10)[1], 11);
        Spaces s12 = new Spaces(points.get(11)[0], points.get(11)[1], 12);
        Spaces s13 = new Spaces(points.get(12)[0], points.get(12)[1], 13);
        Spaces s14 = new Spaces(points.get(13)[0], points.get(13)[1], 14);
        Spaces s15 = new Spaces(points.get(14)[0], points.get(14)[1], 15);
        Spaces s16 = new Spaces(points.get(15)[0], points.get(15)[1], 16);
        Spaces s17 = new Spaces(points.get(16)[0], points.get(16)[1], 17);
        Spaces s18 = new Spaces(points.get(17)[0], points.get(17)[1], 18);
        Spaces s19 = new Spaces(points.get(18)[0], points.get(18)[1], 19);
        Spaces s20 = new Spaces(points.get(19)[0], points.get(19)[1], 20);
        Spaces s21 = new Spaces(points.get(20)[0], points.get(20)[1], 21);
        Spaces s22 = new Spaces(points.get(21)[0], points.get(21)[1], 22);
        Spaces s23 = new Spaces(points.get(22)[0], points.get(22)[1], 23);
        Spaces s24 = new Spaces(points.get(23)[0], points.get(23)[1], 24);
        Spaces s25 = new Spaces(points.get(24)[0], points.get(24)[1], 25);

        lv2objs.add(firebutton);
        lv2objs.add(reset);

        lv2objs.add(mb1);
        lv2objs.add(wb1);
        lv2objs.add(mb);

        lv2objs.add(l1);

        lv2objs.add(s1);
        lv2objs.add(s2);
        lv2objs.add(s3);
        lv2objs.add(s4);
        lv2objs.add(s5);
        lv2objs.add(s6);
        lv2objs.add(s7);
        lv2objs.add(s8);
        lv2objs.add(s9);
        lv2objs.add(s10);
        lv2objs.add(s11);
        lv2objs.add(s12);
        lv2objs.add(s13);
        lv2objs.add(s14);
        lv2objs.add(s15);
        lv2objs.add(s16);
        lv2objs.add(s17);
        lv2objs.add(s18);
        lv2objs.add(s19);
        lv2objs.add(s20);
        lv2objs.add(s21);
        lv2objs.add(s22);
        lv2objs.add(s23);
        lv2objs.add(s24);
        lv2objs.add(s25);

        //have after all board changes
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                original_board[i][j] = board[i][j];
            }
        }

        numTargets = 2;
        hasPrompted = false;
        score = 0;
        fired = false;

        //set background
        //frame.addKeyListener(p1); //every object with inputs needs this
        frame.addMouseListener(this);
        frame.setSize(screenSize); //sets size
        frame.setVisible(true);

        timer.start();
    }

     public void titleScreen() {

        JFrame title = new JFrame("Laser Maze - powered by CoffeeSucksEngine 0.3");
        currentFrame = title;
        currentRoom = "title";
        title.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //what do we do on close?
        title.add(this); //makes the paintComponent add
        title.setResizable(false); //locks size
        title.requestFocus();
        title.toFront();
        //set background
        
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = 0;
            }
        }
        
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                original_board[i][j] = 0;
            }
        }
        
        fired = false;
        
        LevelSelect ls1 = new LevelSelect(-10,170,1);
        titleobjs.add(ls1);
        
        LevelSelect ls2 = new LevelSelect(195,170,2);
        titleobjs.add(ls2);
        
        MetaEasterEgg ee1 = new MetaEasterEgg(400,580);
        titleobjs.add(ee1);
        
        //frame.addKeyListener(p1); //every object with inputs needs this
        title.addMouseListener(this);
        title.setSize(screenSize); //sets size
        title.setVisible(true);

        timer.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        long start = System.nanoTime();
        super.paintComponent(g);
        g.setFont(bigFont); // ******************SETS FONT FOR GAME**************
        int width = this.getWidth();
        int height = this.getHeight();
        g.setColor(Color.white); //BACKGROUND COLOR OF ROOM
        g.fillRect(0, 0, width, height);
        //the rest of this is frametime stuff, PUT STEPS HERE
        if (frameCount == FPS) {
            averageTime = totalTime / FPS;
            totalTime = 0;
            frameCount = 0;
        } else {
            totalTime += System.nanoTime() - start;
            step();
            frameCount++;
        }
        
        if (currentRoom.equals("Beginner")){
        //drawing specifc things
        BufferedImage imgBG = null;
        String pathBG = (dir + "\\lasermaze\\bg.png");
        Image coolBG = null;
        String pathcoolBG = (dir + "\\lasermaze\\codething.gif");
        try {
            imgBG = ImageIO.read(new File(pathBG));
            coolBG = new ImageIcon(pathcoolBG).getImage();
            setBackground(imgBG);
        } catch (IOException e) {
        }
        g.drawImage(coolBG, -30, 175, this);
        g.drawImage(ROOM_BACKGROUND, 0, 0, this);
        String ntar = numTargets + "";
        g.drawString(ntar, 410, 130);
        
        //String s = score + "";
        //g.drawString(s, 60, 60);
        
        g.drawString("BEGINNER", 42, 660);
        //the magic line. This draws every object in the objs array
        for (GameObject curr : objs) {
                if (curr instanceof WinBlock) {
                    //g.drawString(curr.toString(),20,20);
                }
        }
        Graphics2D g2 = (Graphics2D) g;
        
        for (GameObject curr : objs) {
            if (curr.visible) {
                g.drawImage(curr.sprite_index, curr.x, curr.y, this);
                g.drawImage(curr.topSpr, curr.x, curr.y, this);
                //g2.draw(curr.mask);

            }
        }
        
        //g.drawImage (s_tar,268,460,this); //******************************************CHEAP WAY OF DRAWING RETICLE BECAUSE I'M BORED******************
        
        }
        if (currentRoom.equals("Inter")){
        //drawing specifc things
        BufferedImage imgBG = null;
        String pathBG = (dir + "\\lasermaze\\bg.png");
        Image lv2BG = null;
        String pathcoolBG = (dir + "\\lasermaze\\level2bg.gif");
        try {
            imgBG = ImageIO.read(new File(pathBG));
            lv2BG = new ImageIcon(pathcoolBG).getImage();
            setBackground(imgBG);
        } catch (IOException e) {
        }
        g.drawImage(lv2BG, -30, 175, this);
        g.drawImage(ROOM_BACKGROUND, 0, 0, this);
        String ntar = numTargets + "";
        g.drawString(ntar, 410, 130);
        
        //String s = score + "";
        //g.drawString(s, 60, 60);
        
        g.drawString("HARD", 100, 660);
        //the magic line. This draws every object in the objs array
        for (GameObject curr : lv2objs) {
                if (curr instanceof SplitMirror) {
                   //g.drawString(curr.toString(),20,20);
                }
        }
        Graphics2D g2 = (Graphics2D) g;
        
        for (GameObject curr : lv2objs) {
            if (curr.visible) {
                g.drawImage(curr.sprite_index, curr.x, curr.y, this);
                g.drawImage(curr.topSpr, curr.x, curr.y, this);
                //g2.draw(curr.mask);

            }
        }
        
        //g.drawImage (s_tar,268,460,this); //******************************************CHEAP WAY OF DRAWING RETICLE BECAUSE I'M BORED******************
        
        }
        //********************TITLE SCREEN*****************************
        if (currentRoom.equals("title")){
        //drawing specifc things
        BufferedImage imgBG = null;
        String pathBG = (dir + "\\lasermaze\\bgblank.png");
        Image coolBG = null;
        String pathcoolBG = (dir + "\\lasermaze\\codething.gif");
        try {
            imgBG = ImageIO.read(new File(pathBG));
            coolBG = new ImageIcon(pathcoolBG).getImage();
            setBackground(imgBG);
        } catch (IOException e) {
        }
        g.drawImage(coolBG, -30, 175, this);
        g.drawImage(ROOM_BACKGROUND, 0, 0, this);
        g.drawString("WELCOME TO THE MAZE", 42, 660);
        //the magic line. This draws every object in the objs array
        for (GameObject curr : titleobjs) {
            if (curr.visible) {
                g.drawImage(curr.sprite_index, curr.x, curr.y, this);

            }
        }
        
        }
        /*
        if (fired) {
            for (GameObject curr : objs) {
                if (curr instanceof LaserBlock) {
                    if (curr.toString().equals("R")) {
                        int myID = ((LaserBlock) curr).getID();
                        nextPoint = getID(myID -1);
                        nextDir = "R";
                        g.setColor(Color.red);
                        Graphics2D g2 = (Graphics2D) g;
                        g2.setStroke(new BasicStroke(10));
                        
                        while (!failCheck){
                        int firstPointx = nextPoint[0];
                        int firstPointy = nextPoint[1];
                        findNext(nextPoint,nextDir);  
                        g2.draw(new Line2D.Float((float) firstPointx, (float) firstPointy, (float) nextPoint[0], (float) nextPoint[1]));
                        }
                    }
                }
            }
        }
         */
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.repaint();
    }

    public void setBackground(Image bg) {
        ROOM_BACKGROUND = bg;
    }

    public void step() {
        for (GameObject curr : objs) {
            curr.step();
            curr.collisionCheck(objs);
        }
        for (GameObject curr : lv2objs) {
            curr.step();
            curr.collisionCheck(lv2objs);
        }
        
        for (GameObject curr : objs) {
            if (curr instanceof Laser) {
        ((Laser)curr).smallCollisionCheck(objs);
             curr.collisionCheck(objs);
            }
        }
        for (GameObject curr : lv2objs) {
            if (curr instanceof Laser) {
        ((Laser)curr).smallCollisionCheck(lv2objs);
             curr.collisionCheck(lv2objs);
            }
        }
        
        for (GameObject curr : objs) {
            if (curr instanceof LaserBlock) {
                nextDir = curr.toString();
            }
        }
        for (GameObject curr : lv2objs) {
            if (curr instanceof LaserBlock) {
                nextDir = curr.toString();
            }
        }
        for (GameObject curr : objs) {
            if (curr instanceof WinBlock) {
                if (((WinBlock)curr).addPoint){
                    score++;
                    ((WinBlock)curr).addPoint = false;
                }
            }
        }
        for (GameObject curr : lv2objs) {
            if (curr instanceof WinBlock) {
                if (((WinBlock)curr).addPoint){
                    score++;
                    ((WinBlock)curr).addPoint = false;
                }
            }
        }
        
        for (GameObject curr : lv2objs) {
            if (curr instanceof SplitMirror) {

                   if (((SplitMirror)curr).createNew.equals("D")){
                       
                       lv2objs.add(new Laser(curr.x, curr.y+65, 0, 15, 15));
                    ((SplitMirror)curr).createNew = "";
                    
                } 
                   if (((SplitMirror)curr).createNew.equals("R")){
                 
                       lv2objs.add(new Laser(curr.x, curr.y,15, 0, 15));
                    ((SplitMirror)curr).createNew = "";
                    
                } 
                   if (((SplitMirror)curr).createNew.equals("L")){
                   
                       lv2objs.add(new Laser(curr.x, curr.y, -15, 0, 15));
                    ((SplitMirror)curr).createNew = "";
                    
                } 
                   if (((SplitMirror)curr).createNew.equals("U")){
                   
                       lv2objs.add(new Laser(curr.x, curr.y, 0, -15, 15));
                    ((SplitMirror)curr).createNew = "";
                    
                } 
                    
            
            }
        
        }
        for (GameObject curr : lv2objs) {
            if (curr instanceof Laser) {
              if (((Laser)curr).explode){
                  ((Laser)curr).explode = false;
                  lv2objs.add(new Explosion(((Laser)curr).colX, ((Laser)curr).colY));
              }  
            }
        }
        
        for (GameObject curr : objs) {
            if (curr instanceof Laser) {
              if (((Laser)curr).explode){
                  ((Laser)curr).explode = false;
                  objs.add(new Explosion(((Laser)curr).colX, ((Laser)curr).colY));
              }  
            }
        }
        
        if (currentRoom.equals("Beginner"))
            if (!hasPrompted)
        if (score == numTargets){
          if (!easterSD)
            playSound(snd_win,false);
            hasPrompted = true;
            
            JOptionPane.showMessageDialog(currentFrame, "YOU WIN!");  
          
        }
        if (currentRoom.equals("Inter"))
            if (!hasPrompted)
        if (score == numTargets){
          if (!easterSD)
            playSound(snd_win,false);
            hasPrompted = true;
            
            JOptionPane.showMessageDialog(currentFrame, "YOU WIN!");  
          
        }

        /*
        for (GameObject curr : objs) {
            boolean over = true;
            if (curr instanceof WinBlock) {
                if (((WinBlock) curr).HIT == false) {
                    over = false;
                }

            }
            gameWon = over;
        }
        for (GameObject curr : objs) {
            boolean over = true;
            if (curr instanceof Laser) {
                if (((Laser) curr).visible == false) {
                    over = false;
                }

            }
            gameOver = over;
        }
        
        if (gameOver){
            if (!hasPrompted){
                if (gameWon){
                    JOptionPane.showMessageDialog(currentFrame, "YOU WIN!");
                    hasPrompted = true;
                }
                else{
                    JOptionPane.showMessageDialog(currentFrame, "YOU LOSE!");
                    hasPrompted = true;
                }
            }
            
        }
        
        */
        
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON2) {
            toString();
        }
    }

    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Point p = new Point(x, y);

    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y  = e.getY() - 25;
        Point p = new Point(x, y);

        //TITLE
        for (GameObject curr : titleobjs) {
                if (curr instanceof LevelSelect) {
                    if (curr.mask.contains(p)) {
                        if (curr.toString().equals("1")){
                            for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 5; j++) {
                            board[i][j] = original_board[i][j];
                        }
                    }
                         curr.clicked();
                         currentFrame.dispose();
                         titleobjs.clear();
                         mainObj.beginnerRoom();   
                        }
                        if (curr.toString().equals("2")){
                            for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 5; j++) {
                            board[i][j] = original_board[i][j];
                        }
                    }
                         curr.clicked();
                         currentFrame.dispose();
                         titleobjs.clear();
                         mainObj.interRoom();   
                        }
                    }
                }
        }
         for (GameObject currx : titleobjs) {
                        if (currx instanceof MetaEasterEgg) {
                    if (currx.mask.contains(p)) {
                        if (!easterSD){
                    currx.clicked();
                        easterSD = true;
                         playSound(snd_sd,true); 
                    }
                    }
                }
        }
        
        //LEVEL 1
        //test for clicking
        for (GameObject currx : objs) {
                        if (currx instanceof MenuButton) {
                    if (currx.mask.contains(p)) {
                    currx.clicked();
                         currentFrame.dispose();
                         objs.clear();
                         mainObj.titleScreen();    
                    }
                }
        }
       

       
        if (!fired) {
            for (GameObject curr : objs) {
                if (curr instanceof MirrorBlock) {
                    if (curr.mask.contains(p)) {
                        if (curr.visible) {
                            if (e.getButton() == MouseEvent.BUTTON1) {
                                curr.clicked();
                            }
                            if (!hasSelected) {
                                if (e.getButton() == MouseEvent.BUTTON3) {
                                    for (GameObject curr2 : objs) {
                                        if (curr2 instanceof Spaces) {
                                            if (curr2.mask.contains(p)) {
                                                curr2.selected = true;
                                            }
                                        }
                                    }
                                    if (!curr.locked) {
                                        curr.rClicked();
                                        curr.selected = true;
                                        hasSelected = true;
                                    }
                                }
                            }

                        }

                    }
                }
                if (curr instanceof DoubleMirror) {
                    if (curr.mask.contains(p)) {
                        if (curr.visible) {
                            if (e.getButton() == MouseEvent.BUTTON1) {
                                curr.clicked();
                            }
                            if (!hasSelected) {
                                if (e.getButton() == MouseEvent.BUTTON3) {
                                    for (GameObject curr2 : objs) {
                                        if (curr2 instanceof Spaces) {
                                            if (curr2.mask.contains(p)) {
                                                curr2.selected = true;
                                            }
                                        }
                                    }
                                    if (!curr.locked) {
                                        curr.rClicked();
                                        curr.selected = true;
                                        hasSelected = true;
                                    }
                                }
                            }

                        }

                    }
                }
                
                if (curr instanceof SplitMirror) {
                    if (curr.mask.contains(p)) {
                        if (curr.visible) {
                            if (e.getButton() == MouseEvent.BUTTON1) {
                                curr.clicked();
                            }
                            if (!hasSelected) {
                                if (e.getButton() == MouseEvent.BUTTON3) {
                                    for (GameObject curr2 : objs) {
                                        if (curr2 instanceof Spaces) {
                                            if (curr2.mask.contains(p)) {
                                                curr2.selected = true;
                                            }
                                        }
                                    }
                                    if (!curr.locked) {
                                        curr.rClicked();
                                        curr.selected = true;
                                        hasSelected = true;
                                    }
                                }
                            }

                        }

                    }
                }

                if (curr instanceof LaserBlock) {
                    if (curr.mask.contains(p)) {
                        if (curr.visible) {
                            if (e.getButton() == MouseEvent.BUTTON1) {
                                curr.clicked();
                            }
                            if (!hasSelected) {
                                if (e.getButton() == MouseEvent.BUTTON3) {
                                    for (GameObject curr2 : objs) {
                                        if (curr2 instanceof Spaces) {
                                            if (curr2.mask.contains(p)) {
                                                curr2.selected = true;
                                            }
                                        }
                                    }
                                    if (!curr.locked) {
                                        curr.rClicked();
                                        curr.selected = true;
                                        hasSelected = true;
                                    }
                                }
                            }
                        }

                    }
                }
                
                if (curr instanceof FireButton) {
                    if (curr.mask.contains(p)) {
                        if (curr.visible) {
                            curr.clicked();
                            if (fired == false) {
                                for (GameObject check3 : objs) {
                                    if (check3 instanceof LaserBlock) {
                                        int thisSpeed = 15;
                                        if (((LaserBlock) check3).myNumber() == 11) {
                                            fired = true;
                                            objs.add(new Laser(nextPoint[0], nextPoint[1], -1 *thisSpeed, 0, thisSpeed));
                                        }
                                        if (((LaserBlock) check3).myNumber() == 12) {
                                            fired = true;
                                            objs.add(new Laser(nextPoint[0], nextPoint[1], thisSpeed, 0, thisSpeed));
                                        }
                                        if (((LaserBlock) check3).myNumber() == 13) {
                                            fired = true;
                                            objs.add(new Laser(nextPoint[0], nextPoint[1], 0, -1* thisSpeed, thisSpeed));
                                        }
                                        if (((LaserBlock) check3).myNumber() == 14) {
                                            fired = true;
                                            objs.add(new Laser(nextPoint[0], nextPoint[1], 0, thisSpeed, thisSpeed));
                                        }
                                    }
                                }

                            }
                            
                            
                        }
                    }
                }

                if (curr instanceof Spaces) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        if (curr.mask.contains(p)) {
                            if (board[((Spaces) curr).getID()[0]][((Spaces) curr).getID()[1]] < 1) {
                                if (hasSelected) {

                                    moveTo[0] = curr.x;
                                    moveTo[1] = curr.y;
                                    curr.clicked();

                                    for (GameObject check2 : objs) {
                                        if (check2 instanceof MirrorBlock) {
                                            if (check2.selected) {
                                                check2.moveTowardsPoint(curr.getX(), curr.getY(), 5);
                                                check2.selected = false;
                                                board[((Spaces) curr).getID()[0]][((Spaces) curr).getID()[1]] = ((MirrorBlock) check2).myNumber();
                                                check2.playSound(snd_slide, false);
                                                int s = ((Spaces) curr).getRawID();
                                                ((MirrorBlock) check2).setID(s);
                                                hasSelected = false;
                                                for (GameObject check3 : objs) {
                                                    if (check3 instanceof Spaces) {
                                                        if (check3.selected) {
                                                            board[((Spaces) check3).getID()[0]][((Spaces) check3).getID()[1]] = 0;
                                                            check3.selected = false;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (check2 instanceof DoubleMirror) {
                                            if (check2.selected) {
                                                check2.moveTowardsPoint(curr.getX(), curr.getY(), 5);
                                                check2.selected = false;
                                                board[((Spaces) curr).getID()[0]][((Spaces) curr).getID()[1]] = ((DoubleMirror) check2).myNumber();
                                                check2.playSound(snd_slide, false);
                                                int s = ((Spaces) curr).getRawID();
                                                ((DoubleMirror) check2).setID(s);
                                                hasSelected = false;
                                                for (GameObject check3 : objs) {
                                                    if (check3 instanceof Spaces) {
                                                        if (check3.selected) {
                                                            board[((Spaces) check3).getID()[0]][((Spaces) check3).getID()[1]] = 0;
                                                            check3.selected = false;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        
                                        if (check2 instanceof SplitMirror) {
                                            if (check2.selected) {
                                                check2.moveTowardsPoint(curr.getX(), curr.getY(), 5);
                                                check2.selected = false;
                                                board[((Spaces) curr).getID()[0]][((Spaces) curr).getID()[1]] = ((SplitMirror) check2).myNumber();
                                                check2.playSound(snd_slide, false);
                                                int s = ((Spaces) curr).getRawID();
                                                ((SplitMirror) check2).setID(s);
                                                hasSelected = false;
                                                for (GameObject check3 : objs) {
                                                    if (check3 instanceof Spaces) {
                                                        if (check3.selected) {
                                                            board[((Spaces) check3).getID()[0]][((Spaces) check3).getID()[1]] = 0;
                                                            check3.selected = false;
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    }

                                }
                                for (GameObject check3 : objs) {
                                    if (check3 instanceof LaserBlock) {
                                        if (check3.selected) {
                                            check3.moveTowardsPoint(curr.getX(), curr.getY(), 5);
                                            nextPoint[0] = curr.getX();
                                            nextPoint[1] = curr.getY();
                                            check3.selected = false;
                                            board[((Spaces) curr).getID()[0]][((Spaces) curr).getID()[1]] = ((LaserBlock) check3).myNumber();
                                            check3.playSound(snd_slide, false);
                                            hasSelected = false;
                                            for (GameObject check4 : objs) {
                                                if (check4 instanceof Spaces) {
                                                    if (check4.selected) {
                                                        board[((Spaces) check4).getID()[0]][((Spaces) check4).getID()[1]] = 0;
                                                        check4.selected = false;
                                                    }

                                                }
                                            }
                                        }

                                    }

                                }
                            }
                        }
                    }

                }
                //end of test code

            }

        }
        for (GameObject curr : objs) {
            if (curr instanceof ResetButton) {
                if (curr.mask.contains(p)) {
                    hasPrompted = false;
                    gameOver = false;
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 5; j++) {
                            board[i][j] = original_board[i][j];
                        }
                    }

                    if (curr.visible) {
                        score = 0;
                        curr.clicked();
                        fired = false;
                        for (GameObject curr2 : objs) {
                            if (curr2 instanceof MirrorBlock) {
                                curr2.moveTowardsPoint(curr2.startx, curr2.starty, 5);
                                ((MirrorBlock) curr2).setHit(false);
                            }
                            else if (curr2 instanceof DoubleMirror) {
                                curr2.moveTowardsPoint(curr2.startx, curr2.starty, 5);
                                ((DoubleMirror) curr2).setHit(false);
                            }
                            else if (curr2 instanceof SplitMirror) {
                                curr2.moveTowardsPoint(curr2.startx, curr2.starty, 5);
                                ((SplitMirror) curr2).setHit(false);
                            }
                            else if (curr2 instanceof LaserBlock) {
                                curr2.moveTowardsPoint(curr2.startx, curr2.starty, 5);

                            }
                            if (curr2 instanceof WinBlock) {
                                ((WinBlock) curr2).setHit(false);

                            } else if (curr2 instanceof EndGameText) {
                                curr2.switchVisible();

                            }
                        }
                        
                        for (GameObject curr3 : objs) {
                            if (curr3 instanceof Laser) {
                                curr3.jumpX(-99);
                                ((Laser)curr3).switchVisible();
                                curr3.hspeed = 0;
                                curr3.vspeed = 0;

                            }

                        }
                        for (GameObject curr4 : objs) {
                            if (curr4 instanceof Explosion) {
                                curr4.visible = false;
                        }
                    }
                    }
                }
            }
        }
        //LEVEL 1
        //test for clicking
        for (GameObject currx : lv2objs) {
                        if (currx instanceof MenuButton) {
                    if (currx.mask.contains(p)) {
                    currx.clicked();
                         currentFrame.dispose();
                         lv2objs.clear();
                         mainObj.titleScreen();    
                    }
                }
        }
       

       
        if (!fired) {
            for (GameObject curr : lv2objs) {
                if (curr instanceof MirrorBlock) {
                    if (curr.mask.contains(p)) {
                        if (curr.visible) {
                            if (e.getButton() == MouseEvent.BUTTON1) {
                                curr.clicked();
                            }
                            if (!hasSelected) {
                                if (e.getButton() == MouseEvent.BUTTON3) {
                                    for (GameObject curr2 : lv2objs) {
                                        if (curr2 instanceof Spaces) {
                                            if (curr2.mask.contains(p)) {
                                                curr2.selected = true;
                                            }
                                        }
                                    }
                                    if (!curr.locked) {
                                        curr.rClicked();
                                        curr.selected = true;
                                        hasSelected = true;
                                    }
                                }
                            }

                        }

                    }
                }
                if (curr instanceof DoubleMirror) {
                    if (curr.mask.contains(p)) {
                        if (curr.visible) {
                            if (e.getButton() == MouseEvent.BUTTON1) {
                                curr.clicked();
                            }
                            if (!hasSelected) {
                                if (e.getButton() == MouseEvent.BUTTON3) {
                                    for (GameObject curr2 : lv2objs) {
                                        if (curr2 instanceof Spaces) {
                                            if (curr2.mask.contains(p)) {
                                                curr2.selected = true;
                                            }
                                        }
                                    }
                                    if (!curr.locked) {
                                        curr.rClicked();
                                        curr.selected = true;
                                        hasSelected = true;
                                    }
                                }
                            }

                        }

                    }
                }
                
                if (curr instanceof SplitMirror) {
                    if (curr.mask.contains(p)) {
                        if (curr.visible) {
                            if (e.getButton() == MouseEvent.BUTTON1) {
                                curr.clicked();
                            }
                            if (!hasSelected) {
                                if (e.getButton() == MouseEvent.BUTTON3) {
                                    for (GameObject curr2 : lv2objs) {
                                        if (curr2 instanceof Spaces) {
                                            if (curr2.mask.contains(p)) {
                                                curr2.selected = true;
                                            }
                                        }
                                    }
                                    if (!curr.locked) {
                                        curr.rClicked();
                                        curr.selected = true;
                                        hasSelected = true;
                                    }
                                }
                            }

                        }

                    }
                }

                if (curr instanceof LaserBlock) {
                    if (curr.mask.contains(p)) {
                        if (curr.visible) {
                            if (e.getButton() == MouseEvent.BUTTON1) {
                                curr.clicked();
                            }
                            if (!hasSelected) {
                                if (e.getButton() == MouseEvent.BUTTON3) {
                                    for (GameObject curr2 : lv2objs) {
                                        if (curr2 instanceof Spaces) {
                                            if (curr2.mask.contains(p)) {
                                                curr2.selected = true;
                                            }
                                        }
                                    }
                                    if (!curr.locked) {
                                        curr.rClicked();
                                        curr.selected = true;
                                        hasSelected = true;
                                    }
                                }
                            }
                        }

                    }
                }
                
                if (curr instanceof FireButton) {
                    if (curr.mask.contains(p)) {
                        if (curr.visible) {
                            curr.clicked();
                            if (fired == false) {
                                for (GameObject check3 : lv2objs) {
                                    if (check3 instanceof LaserBlock) {
                                        int thisSpeed = 15;
                                        if (((LaserBlock) check3).myNumber() == 11) {
                                            fired = true;
                                            lv2objs.add(new Laser(nextPoint[0], nextPoint[1], -1 *thisSpeed, 0, thisSpeed));
                                        }
                                        if (((LaserBlock) check3).myNumber() == 12) {
                                            fired = true;
                                            lv2objs.add(new Laser(nextPoint[0], nextPoint[1], thisSpeed, 0, thisSpeed));
                                        }
                                        if (((LaserBlock) check3).myNumber() == 13) {
                                            fired = true;
                                            lv2objs.add(new Laser(nextPoint[0], nextPoint[1], 0, -1* thisSpeed, thisSpeed));
                                        }
                                        if (((LaserBlock) check3).myNumber() == 14) {
                                            fired = true;
                                            lv2objs.add(new Laser(nextPoint[0], nextPoint[1], 0, thisSpeed, thisSpeed));

                                        }
                                    }
                                }

                            }
                            
                            
                        }
                    }
                }

                if (curr instanceof Spaces) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        if (curr.mask.contains(p)) {
                            if (board[((Spaces) curr).getID()[0]][((Spaces) curr).getID()[1]] < 1) {
                                if (hasSelected) {

                                    moveTo[0] = curr.x;
                                    moveTo[1] = curr.y;
                                    curr.clicked();

                                    for (GameObject check2 : lv2objs) {
                                        if (check2 instanceof MirrorBlock) {
                                            if (check2.selected) {
                                                check2.moveTowardsPoint(curr.getX(), curr.getY(), 5);
                                                check2.selected = false;
                                                board[((Spaces) curr).getID()[0]][((Spaces) curr).getID()[1]] = ((MirrorBlock) check2).myNumber();
                                                check2.playSound(snd_slide, false);
                                                int s = ((Spaces) curr).getRawID();
                                                ((MirrorBlock) check2).setID(s);
                                                hasSelected = false;
                                                for (GameObject check3 : lv2objs) {
                                                    if (check3 instanceof Spaces) {
                                                        if (check3.selected) {
                                                            board[((Spaces) check3).getID()[0]][((Spaces) check3).getID()[1]] = 0;
                                                            check3.selected = false;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (check2 instanceof DoubleMirror) {
                                            if (check2.selected) {
                                                check2.moveTowardsPoint(curr.getX(), curr.getY(), 5);
                                                check2.selected = false;
                                                board[((Spaces) curr).getID()[0]][((Spaces) curr).getID()[1]] = ((DoubleMirror) check2).myNumber();
                                                check2.playSound(snd_slide, false);
                                                int s = ((Spaces) curr).getRawID();
                                                ((DoubleMirror) check2).setID(s);
                                                hasSelected = false;
                                                for (GameObject check3 : lv2objs) {
                                                    if (check3 instanceof Spaces) {
                                                        if (check3.selected) {
                                                            board[((Spaces) check3).getID()[0]][((Spaces) check3).getID()[1]] = 0;
                                                            check3.selected = false;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        
                                        if (check2 instanceof SplitMirror) {
                                            if (check2.selected) {
                                                check2.moveTowardsPoint(curr.getX(), curr.getY(), 5);
                                                check2.selected = false;
                                                board[((Spaces) curr).getID()[0]][((Spaces) curr).getID()[1]] = ((SplitMirror) check2).myNumber();
                                                check2.playSound(snd_slide, false);
                                                int s = ((Spaces) curr).getRawID();
                                                ((SplitMirror) check2).setID(s);
                                                hasSelected = false;
                                                for (GameObject check3 : lv2objs) {
                                                    if (check3 instanceof Spaces) {
                                                        if (check3.selected) {
                                                            board[((Spaces) check3).getID()[0]][((Spaces) check3).getID()[1]] = 0;
                                                            check3.selected = false;
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    }

                                }
                                for (GameObject check3 : lv2objs) {
                                    if (check3 instanceof LaserBlock) {
                                        if (check3.selected) {
                                            check3.moveTowardsPoint(curr.getX(), curr.getY(), 5);
                                            nextPoint[0] = curr.getX();
                                            nextPoint[1] = curr.getY();
                                            check3.selected = false;
                                            board[((Spaces) curr).getID()[0]][((Spaces) curr).getID()[1]] = ((LaserBlock) check3).myNumber();
                                            check3.playSound(snd_slide, false);
                                            hasSelected = false;
                                            for (GameObject check4 : lv2objs) {
                                                if (check4 instanceof Spaces) {
                                                    if (check4.selected) {
                                                        board[((Spaces) check4).getID()[0]][((Spaces) check4).getID()[1]] = 0;
                                                        check4.selected = false;
                                                    }

                                                }
                                            }
                                        }

                                    }

                                }
                            }
                        }
                    }

                }
                //end of test code

            }

        }
        for (GameObject curr : lv2objs) {
            if (curr instanceof ResetButton) {
                if (curr.mask.contains(p)) {
                    hasPrompted = false;
                    gameOver = false;
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 5; j++) {
                            board[i][j] = original_board[i][j];
                        }
                    }

                    if (curr.visible) {
                        score = 0;
                        curr.clicked();
                        fired = false;
                        for (GameObject curr2 : lv2objs) {
                            if (curr2 instanceof MirrorBlock) {
                                curr2.moveTowardsPoint(curr2.startx, curr2.starty, 5);
                                ((MirrorBlock) curr2).setHit(false);
                            }
                            else if (curr2 instanceof DoubleMirror) {
                                curr2.moveTowardsPoint(curr2.startx, curr2.starty, 5);
                                ((DoubleMirror) curr2).setHit(false);
                            }
                            
                            else if (curr2 instanceof SplitMirror) {
                                curr2.moveTowardsPoint(curr2.startx, curr2.starty, 5);
                                ((SplitMirror) curr2).setHit(false);
                            }
                            else if (curr2 instanceof LaserBlock) {
                                curr2.moveTowardsPoint(curr2.startx, curr2.starty, 5);

                            }
                            if (curr2 instanceof WinBlock) {
                                ((WinBlock) curr2).setHit(false);

                            } else if (curr2 instanceof EndGameText) {
                                curr2.switchVisible();

                            }
                        }
                        
                        for (GameObject curr3 : lv2objs) {
                            if (curr3 instanceof Laser) {
                                curr3.jumpX(-99);
                                ((Laser)curr3).switchVisible();
                                curr3.hspeed = 0;
                                curr3.vspeed = 0;

                            }
                            for (GameObject curr4 : lv2objs) {
                            if (curr4 instanceof Explosion) {
                                curr4.visible = false;
                        }
                    }
                }
            }
        }
    }
        }
    }
        

    public int[] getID(int ID) {
        int[] p = new int[2];

        if (ID == 1) {
            p[0] = 0;
            p[1] = 0;
            return p;
        }
        if (ID == 2) {
            p[0] = 1;
            p[1] = 0;
            return p;
        }
        if (ID == 3) {
            p[0] = 2;
            p[1] = 0;
            return p;
        }
        if (ID == 4) {
            p[0] = 3;
            p[1] = 0;
            return p;
        }
        if (ID == 5) {
            p[0] = 4;
            p[1] = 0;
            return p; 
        }
        if (ID == 6) {
            p[0] = 0;
            p[1] = 1;
            return p;
        }
        if (ID == 7) {
            p[0] = 1;
            p[1] = 1;
            return p;
        }
        if (ID == 8) {
            p[0] = 2;
            p[1] = 1;
            return p;
        }
        if (ID == 9) {
            p[0] = 3;
            p[1] = 1;
            return p;
        }
        if (ID == 10) {
            p[0] = 4;
            p[1] = 1;
            return p;
        }
        if (ID == 11) {
            p[0] = 0;
            p[1] = 2;
            return p;
        }
        if (ID == 12) {
            p[0] = 1;
            p[1] = 2;
            return p;
        }
        if (ID == 13) {
            p[0] = 2;
            p[1] = 2;
            return p;
        }
        if (ID == 14) {
            p[0] = 3;
            p[1] = 2;
            return p;
        }
        if (ID == 15) {
            p[0] = 4;
            p[1] = 2;
            return p;
        }
        if (ID == 16) {
            p[0] = 0;
            p[1] = 3;
            return p;
        }
        if (ID == 17) {
            p[0] = 1;
            p[1] = 3;
            return p;
        }
        if (ID == 18) {
            p[0] = 2;
            p[1] = 3;
            return p;
        }
        if (ID == 19) {
            p[0] = 3;
            p[1] = 3;
            return p;
        }
        if (ID == 20) {
            p[0] = 4;
            p[1] = 3;
            return p;
        }
        if (ID == 21) {
            p[0] = 0;
            p[1] = 4;
            return p;
        }
        if (ID == 22) {
            p[0] = 1;
            p[1] = 4;
            return p;
        }
        if (ID == 23) {
            p[0] = 2;
            p[1] = 4;
            return p;
        }
        if (ID == 24) {
            p[0] = 3;
            p[1] = 4;
            return p;
        }
        if (ID == 25) {
            p[0] = 4;
            p[1] = 4;
            return p;
        }
        return p;
    }

    public String toString() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.println(i + ", " + j + " " + board[i][j]);
            }
        }
        return "hello";
    }
    
    public void playSound(String soundfile, boolean loop){
        try
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundfile).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            if (!loop)
            clip.start();
            else
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch (Exception ex){
        }   
    }

    /* public void findNext(int[] p, String dir) {       
        if (board[p[0]][p[1]] == 1) {
            if (dir.equals("R")) {
                for (int i = p[1]; i >= 0; i--) { //check up
                    if (board[p[0]][i] != 0) {
                        int[] myHit = new int[2];
                        myHit[0] = p[0];
                        myHit[1] = i;
                        nextDir = "U";
                        nextPoint = myHit;
                        return;
                    }
                }
            }
        }
        if (board[p[0]][p[1]] == 1) {
            if (dir.equals("D")) {
                for (int i = p[0]; i >= 0; i--) { //check left
                    if (board[i][p[1]] != 0) {
                        int[] myHit = new int[2];
                        myHit[0] = i;
                        myHit[1] = p[1];
                        nextDir = "L";
                        nextPoint = myHit;
                        return;
                    }
                }
            }
        }
        if (board[p[0]][p[1]] == 2) {
            if (dir.equals("D")) {
                for (int i = p[0]; i < 5; i++) { //check right
                    if (board[i][p[1]] != 0) {
                        int[] myHit = new int[2];
                        myHit[0] = i;
                        myHit[1] = p[1];
                        nextDir = "R";
                        nextPoint = myHit;
                        return;
                    }
                }
            }
        }
        if (board[p[0]][p[1]] == 2) {
            if (dir.equals("L")) {
                for (int i = p[1]; i >= 0; i--) { //check up
                    if (board[p[0]][i] != 0) {
                        int[] myHit = new int[2];
                        myHit[0] = p[0];
                        myHit[1] = i;
                        nextDir = "U";
                        nextPoint = myHit;
                        return;
                    }
                }
            }
        }
        if (board[p[0]][p[1]] == 3) {
            if (dir.equals("R")) {
                for (int i = p[1]; i < 5; i++) { //check down
                    if (board[p[0]][i] != 0) {
                        int[] myHit = new int[2];
                        myHit[0] = p[0];
                        myHit[1] = i;
                        nextDir = "D";
                        nextPoint = myHit;
                        return;
                    }
                }
            }
        }
        if (board[p[0]][p[1]] == 3) {
            if (dir.equals("U")) {
                for (int i = p[0]; i >= 0; i--) { //check left
                    if (board[i][p[1]] != 0) {
                        int[] myHit = new int[2];
                        myHit[0] = i;
                        myHit[1] = p[1];
                        nextDir = "L";
                        nextPoint = myHit;
                        return;
                    }
                }
            }
        }
        if (board[p[0]][p[1]] == 4) {
            if (dir.equals("U")) {
                for (int i = p[0]; i < 5; i++) { //check right
                    if (board[i][p[1]] != 0) {
                        int[] myHit = new int[2];
                        myHit[0] = i;
                        myHit[1] = p[1];
                        nextDir = "R";
                        nextPoint = myHit;
                        return;
                    }
                }
            }
        }
        if (board[p[0]][p[1]] == 4) {
            if (dir.equals("L")) {
                for (int i = p[1]; i < 5; i++) { //check down
                    if (board[p[0]][i] != 0) {
                        int[] myHit = new int[2];
                        myHit[0] = p[0];
                        myHit[1] = i;
                        nextDir = "D";
                        nextPoint = myHit;
                        return;
                    }
                }
            }
        }
        failCheck = true;
        return;
    }
     */
}
