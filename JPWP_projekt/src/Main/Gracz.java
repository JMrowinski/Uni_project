package Main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

/**
 * Klasa odpowiedzialna za przechowywanie parametrów gracza, animację oraz detekcję kolizji
 * @author Jakub Mrowiński
 */

public class Gracz{
	
	/**Wymiary generowanego świata*/
	public int worldX, worldY;
	/**Prędkość poruszania się postaci*/
	public int speed;
	/**Zdrowie postaci*/
	public int health;
	/**Wynik postaci*/
	public int score;
	/**Zmienna wykorzystywana przy wykrywaniu kolizji z przeszkodą*/
	public boolean collisionON;
	/**Zmienna wykorzystywana przy wykrywaniu kolizji z quizem*/
	public boolean quizCollision;
	//**Zmienne pomocnicze*/
	int stoper, odpowiedzi, quizWskaznik;
	/**Hitbox postaci*/
	public Rectangle solidArea;
	/** Wczytywanie klatek do animacji*/
	public BufferedImage up1, up2, up3, up4, up2_dmg, up4_dmg;
	/**Zmienna określająca stan w jakiej jest postać*/
	public String direction;		
	/**Zmienne potrzebne do odliczania ilości klatek*/
	public int spriteWalk= 0, spriteNumWalk = 1;
	/**Zmienna wykorzystywana do zliczania wystąpień pytań i wywoływania kolejnych z tabeli pytań*/
	public int licznik_quiz;
	
	ProcesGry pg;
	Sterowanie Ster;
	
	/** wymiary monitora*/
	public  int screenX, screenY;

	
	public Gracz(ProcesGry pg, Sterowanie Ster){
		
		this.pg = pg;
		this.Ster = Ster;
		

		/**
		 * Prostokąt symululujący hitbox postaci
		 */
		solidArea = new Rectangle(0, 0, 128, 128);
		solidArea.x = 32;
		solidArea.y = 32;
		solidArea.width = 64;
		solidArea.height = 64;
		
		
		WartosciStartowe();
		WczytajZasobyGracza();
	
	}
	
	/**
	 * Funkcja resetująca wszystkie wartości dla gracza w sytuacji rozpoczęcia nowej gry
	 */
	
	public void WartosciStartowe() {
		worldX = pg.tileSize * pg.maxScreenCol;
		worldY = pg.tileSize * pg.maxScreenRow + (pg.worldGeneratorHeight-pg.maxScreenRow-4)*pg.tileSize;
		speed = 5;
		health = 3;
		score = 0;
		direction = "up";
		collisionON = true;
		quizCollision = true;
		screenX = pg.maxScreenRow/2*pg.tileSize;
		screenY = pg.maxScreenCol/2*pg.tileSize + pg.tileSize;
		Ster.pozycja = 1;
		licznik_quiz = 0;
	}
	
	/**
	 * Funkcja wczytująca klatki do animacji ruchu postaci
	 */
	public void WczytajZasobyGracza() {
		
		try {
			
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
		
	
	/**
	 * Aktualizowanie informacji o graczu 60 razy na sekundę w klasie ProcesGry
	 */
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

				
				
				worldY -= speed;
			if(Ster.pozycja == 0) {
				screenX = pg.maxScreenRow/2*pg.tileSize - pg.tileSize;
			}else if(Ster.pozycja == 1) {
				screenX = pg.maxScreenRow/2*pg.tileSize;
			}else if(Ster.pozycja == 2) {
				screenX = pg.maxScreenRow/2*pg.tileSize +pg.tileSize;
			}
				
			

			spriteWalk++;
			if(spriteWalk > 5) {
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
					


					
					
					quizCollision = false;
					stoper = pg.WewnetrznyZegar;
				}
				
			}
			
			if(pg.WewnetrznyZegar - stoper >= 30 && quizCollision == false) {
				quizCollision = true;
				pg.muzyka.stop();
				pg.muzykaPauza(0);
				
			}
			
			

			
			score = pg.WewnetrznyZegar/10;
					
			
	}
		


	/**
	 * Funkcja rysująca animację poruszającej sie postaci
	 * @param g2
	 */
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		switch(direction) {
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
			if(spriteNumWalk == 1) {
				image = up1;
			}
			if(spriteNumWalk == 2) {
				image = up2_dmg;
			}
			if(spriteNumWalk == 3) {
				image = up3;
			}
			if(spriteNumWalk == 4) {
				image = up4_dmg;
			}
		}
		
		g2.drawImage(image, screenX, screenY, pg.tileSize, pg.tileSize, null);
	}
}
