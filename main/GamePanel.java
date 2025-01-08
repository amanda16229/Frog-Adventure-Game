package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

	//screen settings - what the user sees
	
	public final int originalTileSize = 16; // 16x16 tile -default size
	public final int scale = 3;
	
	public final int tileSize = originalTileSize * scale; // 48x48 tile - must be public to access from other packages
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	
	//WORLD SETTINGS
	public final int maxWorldCol = 16; //his is 50x50 world
	public final int maxWorldRow = 13;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	
	//FPS
	int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	
	KeyHandler keyH = new KeyHandler(); //instatiate Key Handler 
	Thread gameThread;
	public CollisionChecker cChecker = new CollisionChecker(this);
	public Player player = new Player(this,keyH); //'this' passes GamePanel class
	public SuperObject obj[] = new SuperObject[10]; // prepare 10 slots for object, can display up to 10 at same time 
	//can instatiate objcts and place on map by creating method in GamePanel - in separate class bc were placing much more here
	public AssetSetter as = new AssetSetter(this);
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(false);
		this.addKeyListener(keyH);
		this.setFocusable(true); // GamePanel can be "focused" to receive key input
	}

	//instatiate game thread 
	//argument this = gamePanel graph to the constructor
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	//when we run the game it automaticaly calls this method
	//inside we will create game loop "Core of the game"
	
	/* @Override
	public void run() {
		
		double drawInterval = 1000000000/FPS; // 0.01666 seconds
		double nextDrawTime = System.nanoTime() + drawInterval;

		while(gameThread != null) {

			//long currentTime = System.nanoTime(); returns current val of running JVM's high res time source in ns
			
			// 1: Update info such as character positions
			update();
			
			//need restriction for when new screen displayed or else CPU will process ASAP causing character to move so fast it goes off screen
			
			
			
			// 2: draw screen w/ updated info
			repaint(); // calls paintComponent
			
			// loop wont do anything until sleep method is over
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime /= 1000000; //convert to ms
				
				if(remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long) remainingTime); // accepts as millisec
				
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	} */
	
	public void run() {
		
		double drawInterval = 1000000000/FPS; // 0.01666 seconds
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		//for checking FPS
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {

			currentTime = System.nanoTime(); // check current time
			
			delta += (currentTime - lastTime) / drawInterval; // determines how much time has passed
			
			timer += (currentTime - lastTime);
			
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				
				drawCount++; //for checking FPS
			}
			
			//for checking FPS
			if(timer >= 1000000000) {
				//System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
		
	}
	
	public void update() {
		
		//can we omit else?
		player.update();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g); 
		
		Graphics2D g2 = (Graphics2D) g;
		
		tileM.draw(g2); // draw tiles b4 players
		player.draw(g2); // w.out g2 we cannot draw anything
		
		
		g2.dispose(); //dispose of this graphics context and release any system resources its using
	}

}
