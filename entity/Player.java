package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{

	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		//subtract half tile length from screenX and Y
		screenX = gp.screenWidth/2 - (gp.tileSize / 2);
		screenY = gp.screenHeight/2 - (gp.tileSize / 2);
		
		solidArea = new Rectangle(8, 16, 32, 32);
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		worldX = gp.tileSize * 2;
		worldY = gp.tileSize * 2;
		speed = 4;
		direction = "down";
	}
	
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/frogWalkfront.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/frogWalkfront2.png"));
			lft1 = ImageIO.read(getClass().getResourceAsStream("/player/left.png"));
			lft2 = ImageIO.read(getClass().getResourceAsStream("/player/walkLeft.png"));
			rt1 = ImageIO.read(getClass().getResourceAsStream("/player/right.png"));
			rt2 = ImageIO.read(getClass().getResourceAsStream("/player/walkRight.png"));
			dwn1 = ImageIO.read(getClass().getResourceAsStream("/player/back1.png"));
			dwn2 = ImageIO.read(getClass().getResourceAsStream("/player/back2.png"));

		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() { //method gets called 60x per sec
		
		if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
			
			//in every frame this gets called & increases counter by 1 & if it hits 10 it changes the sprite 
			if(keyH.upPressed == true) {
				direction = "up";
			}
			
			else if(keyH.leftPressed == true) {
				direction = "left";
			}
			
			else if(keyH.downPressed == true) {
				direction = "down";
			}
			
			else if(keyH.rightPressed == true) {
				direction = "right";
			}
			
			//CHECK TILE COLLISION
			collisionOn = false; // only check direction - based on it we let player move or not
			gp.cChecker.checkTile(this);
			
			//IF COLLSION IS FALSE, PLAYER CAN MOVE
			if(collisionOn == false) {
				switch(direction) {
				case "up":
					worldY -= speed; // moved these statements from update()
					break;
					
				case "down":
					worldY += speed;
					break;
					
				case "left":
					worldX -= speed;
					break;
					
				case "right":
					worldX += speed;
					break;
				}
			}
			
			spriteCounter++;
			if(spriteCounter > 12) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		//g2.setColor(Color.white);
		
		//g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			if(spriteNum == 1) {
				image = dwn1;
			}
			if(spriteNum == 2) {
				image = dwn2;
			}
			break;
		case "left":
			if(spriteNum == 1) {
				image = lft2;
			}
			if(spriteNum == 2) {
				image = lft1;
			}
			break;
		case "down":
			if(spriteNum == 1) {
				image = up1;
			}
			if(spriteNum == 2) {
				image = up2;
			}
			break;
		case "right":
			if(spriteNum == 1) {
				image = rt1;
			}
			if(spriteNum == 2) {
				image = rt2;
			}
			break;
		}
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null); // null is for image observer
		
		
	}
}
