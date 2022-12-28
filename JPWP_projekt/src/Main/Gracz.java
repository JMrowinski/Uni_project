package Main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Gracz{
	
	public int worldX, worldY;
	public int speed;
	public int health;
	public int score;
	public boolean collisionON;
	public boolean quizCollision;
	int stoper;
	int odpowiedzi;
	int quizWskaznik;
	public Rectangle solidArea;
	
	public BufferedImage up1, up2, up3, up4, up2_dmg, up4_dmg;	//states for animation purposes
	public String direction;		//useful to determine which frame of animation to play
	
	public int spriteWalk= 0;	//used to alter frames in animation
	public int spriteNumWalk = 1;		//there are only 2 keyframes 1 and 2
	
	public int spriteRoll = 0;
	public int spriteNumRoll = 1;
	
	String pytanie;
	int wybierz_pole_tablicy;
	int numer_odpowiedzi_z_quizu;

	
	ProcesGry pg;
	Sterowanie Ster;
	
	public  int screenX;
	public  int screenY;
	
	public Gracz(ProcesGry pg, Sterowanie Ster) {
		
		this.pg = pg;
		this.Ster = Ster;
		
		
		
		solidArea = new Rectangle(0, 0, 128, 128);
		solidArea.x = 32;
		solidArea.y = 32;
		solidArea.width = 64;
		solidArea.height = 64;
		
		
		WartosciStartowe();
		WczytajZasobyGracza();
	
	}
	
	public void WartosciStartowe() {		//wartosci startowe dla gracz
		worldX = pg.tileSize * pg.maxScreenCol;
		worldY = pg.tileSize * pg.maxScreenRow + (pg.worldGeneratorHeight-pg.maxScreenRow-2)*pg.tileSize;
		speed = 5;
		health = 3;
		score = 0;
		direction = "up";	//idle frame
		collisionON = true;
		quizCollision = true;
		screenX = pg.maxScreenRow/2*pg.tileSize;
		screenY = pg.maxScreenCol/2*pg.tileSize + pg.tileSize;
		Ster.pozycja = 1;
	//	liczba_quizow = 0;
	}
	
	
	public void WczytajZasobyGracza() {
		
		try {		//importing animation frames from res folder
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/Gracz/walk1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/Gracz/walk2.png"));
			up3 = ImageIO.read(getClass().getResourceAsStream("/Gracz/walk3.png"));
			up4 = ImageIO.read(getClass().getResourceAsStream("/Gracz/walk4.png"));
			up2_dmg = ImageIO.read(getClass().getResourceAsStream("/Gracz/walk2_damage.png"));
			up4_dmg = ImageIO.read(getClass().getResourceAsStream("/Gracz/walk4_damage.png"));

			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void update() {
		
			if(Ster.wcisnietyLewy == true) {
				direction = "left";
				
			}
			else if(Ster.wcisnietyPrawy == true) {
				direction = "right";
				
			}
			else if(Ster.wcisnietyPrawy == false || Ster.wcisnietyLewy == false)
			{
				direction = "up";
			}
			
			if(collisionON == false) {
				direction = "collision";
			}

				
				
				//ZMIANA LINII
				worldY -= speed;
			if(Ster.pozycja == 0) {
				screenX = pg.maxScreenRow/2*pg.tileSize - pg.tileSize;
			}else if(Ster.pozycja == 1) {
				screenX = pg.maxScreenRow/2*pg.tileSize;
			}else if(Ster.pozycja == 2) {
				screenX = pg.maxScreenRow/2*pg.tileSize +pg.tileSize;
			}
				
			
			//ANIMACJA CHODZENIA
			spriteWalk++;
			if(spriteWalk > 5) {			//licznik to zmian klatek animacji
				if(spriteNumWalk == 1) {
					spriteNumWalk = 2;
				}
				else if(spriteNumWalk == 2) {
					spriteNumWalk = 3;
				}
				else if(spriteNumWalk == 3) {
					spriteNumWalk = 4;
				}
				else if(spriteNumWalk == 4) {
					spriteNumWalk = 1;
				}
				spriteWalk = 0;		
			}
			
			
			//ANIMACJA SKOKU W BOK
			spriteRoll++;
			if(spriteRoll > 2) {			//licznik to zmian klatek animacji, kiedys animacja inna niz chodzenia, moze zmienie
				if(spriteNumRoll == 1) {
					spriteNumRoll = 2;
				}
				else if(spriteNumRoll == 2) {
					spriteNumRoll = 3;
				}
				else if(spriteNumRoll == 3) {
					spriteNumRoll = 4;
				}
				else if(spriteNumRoll == 4) {
					spriteNumRoll = 1;
				}
				
				
				spriteRoll = 0;
			}
			
			
			//PRZESZKODY I KOLIZJA 
			int lewaKrawedz = screenX+solidArea.x;
			int prawaKrawedz = screenX+solidArea.x+solidArea.width;
			int gornaKrawedz = worldY+solidArea.y;
			
			int tileNum1, tileNum2, tileNum3;
			
			tileNum1 = pg.TileZ.mapTileNum[screenX/pg.tileSize][(gornaKrawedz-speed)/pg.tileSize]; // blok przed graczem
			tileNum2 = pg.TileZ.mapTileNum[(lewaKrawedz-speed)/pg.tileSize][gornaKrawedz/pg.tileSize];		//blok po lewej
			tileNum3 = pg.TileZ.mapTileNum[(prawaKrawedz-speed)/pg.tileSize][gornaKrawedz/pg.tileSize];		//blok po prawej
			if(pg.TileZ.tile[tileNum1].collison == 1 || pg.TileZ.tile[tileNum2].collison == 1 || pg.TileZ.tile[tileNum3].collison == 1) {
				if(collisionON == true) {
					health--;
					pg.muzykaStop();
					pg.efektDzwiekowy(1);
					
					collisionON = false;
					stoper = pg.WewnetrznyZegar;
				}
				

			}
			
			if(pg.WewnetrznyZegar - stoper >= 30 && collisionON == false) {
				collisionON = true;
				pg.muzyka.stop();
				pg.muzykaPauza(0);
				
			}
			
			if(pg.TileZ.tile[tileNum1].collison == 2 || pg.TileZ.tile[tileNum2].collison == 2 || pg.TileZ.tile[tileNum3].collison == 2) {
				if(quizCollision == true) {
					pg.muzykaStop();
					pg.StanGry = pg.Quiz;
					odpowiedzi = 0;
					quizWskaznik = 0;
					System.out.println(pg.TileZ.liczba_quizow);
					
					for(int i = 0; i<4; i++) {
						//System.out.println(pg.randomizer.pytania_odpowiedzi[i][0]);
					}

					if(odpowiedzi == 4) {
						pg.StanGry = pg.Gra;
					}
					
					quizCollision = false;
					stoper = pg.WewnetrznyZegar;
				}
				
			}
			
			if(pg.WewnetrznyZegar - stoper >= 30 && quizCollision == false) {
				quizCollision = true;
				pg.muzyka.stop();
				pg.muzykaPauza(0);
				
			}
			
			
			//WYNIK
			
			score = pg.WewnetrznyZegar/10;
					
			
	}
		


	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		switch(direction) {		//animowanie ruchow postaci, UP ZOSTAJE, DOWN USUNAC, ZNALEZC SPOSOB NA NIEZALEZNE PUSZCZANIE LEFT RIGHT
		case "up":
			if(spriteNumWalk == 1) {
				image = up1;
			}
			if(spriteNumWalk == 2) {
				image = up2;
			}
			if(spriteNumWalk == 3) {
				image = up3;
			}
			if(spriteNumWalk == 4) {
				image = up4;
			}
			break;
		case "left":
			if(spriteNumWalk == 1) {
				image = up1;
			}
			if(spriteNumWalk == 2) {
				image = up2;
			}
			if(spriteNumWalk == 3) {
				image = up3;
			}
			if(spriteNumWalk == 4) {
				image = up4;
			}
			break;
		case "right":
			if(spriteNumWalk == 1) {
				image = up1;
			}
			if(spriteNumWalk == 2) {
				image = up2;
			}
			if(spriteNumWalk == 3) {
				image = up3;
			}
			if(spriteNumWalk == 4) {
				image = up4;
			}
			break; 
		case "collision":
			if(spriteNumRoll == 1) {
				image = up1;
			}
			if(spriteNumRoll == 2) {
				image = up2_dmg;
			}
			if(spriteNumRoll == 3) {
				image = up3;
			}
			if(spriteNumRoll == 4) {
				image = up4_dmg;
			}
		}
		
		g2.drawImage(image, screenX, screenY, pg.tileSize, pg.tileSize, null);		//drawing using 2DGraphics, used for .draw(g2) function
	}
}
