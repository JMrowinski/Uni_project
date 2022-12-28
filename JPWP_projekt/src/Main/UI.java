package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class UI {
	
	ProcesGry pg;
	Font Arcade, Symbols;
	BufferedImage hp3, hp2, hp1, hp0, hpdmg;
	int counter;
	Graphics2D g2;
	public int commandNum = 0;
	public int commandNumP = 0;
	
	
	
	public UI(ProcesGry pg) {
		this.pg = pg;
			
		try {
			InputStream is = getClass().getResourceAsStream("/Reszta/ARCADECLASSIC.TTF");
			Arcade = Font.createFont(Font.TRUETYPE_FONT, is);
			InputStream is2 = getClass().getResourceAsStream("/Reszta/kongtext.ttf");
			Symbols = Font.createFont(Font.TRUETYPE_FONT, is2);
		} catch (FontFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		counter = 0;
		try {		//importing animation frames from res folder
			
			hp3 = ImageIO.read(getClass().getResourceAsStream("/Reszta/3health.png"));
			hp2 = ImageIO.read(getClass().getResourceAsStream("/Reszta/2health.png"));
			hp1 = ImageIO.read(getClass().getResourceAsStream("/Reszta/1health.png"));
			hp0 = ImageIO.read(getClass().getResourceAsStream("/Reszta/0health.png"));
			hpdmg = ImageIO.read(getClass().getResourceAsStream("/Reszta/health_damage.png"));

			
		}catch(IOException e) {
			e.printStackTrace();
		}

	}
	

	
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(Arcade);
		g2.setColor(Color.white);
		
		if(pg.StanGry == pg.Gra) {
			UIgra();
		}
		
		if(pg.StanGry == pg.Pauza) {
			UIpauza();
		}
		
		if(pg.StanGry == pg.Menu) {
			UIintro();
		}
		
		if(pg.StanGry == pg.Opcje) {
			UIopcje();
		}
		
		if(pg.StanGry == pg.Quiz) {
			UIquiz();
		}
	}
	
	public void UIintro() {
		
		//TLO
		g2.setColor(new Color (168, 181, 174));
		g2.fillRect(0, 0, pg.screenWidth, pg.screenHeight);
		
		//NAPISY TYTUŁOWE
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96));
		String text = "No  cap";
		int length = (int)g2.getFontMetrics().getStringBounds( text , g2).getWidth();
		int width = (int)g2.getFontMetrics().getStringBounds(text, g2).getHeight();
		int x = pg.screenWidth/2 - length/2;
		int y = pg.tileSize*2;
		
		//CIEN
		g2.setColor(new Color(151, 164, 157));
		g2.drawString(text, x+5, y+5);
		
		//GLOWNY NAPIS
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24));
		String text2 = "Best   math   learning   tool";
		int x2 = pg.screenWidth/2 - length + length/4;
		int y2 = pg.tileSize*2 - width + width/4;
		
		//CIEN
		g2.setColor(new Color(151, 164, 157));
		g2.drawString(text2, x2+3, y2+3);
		
		//MNIEJSZY NAPIS
		g2.setColor(Color.white);
		g2.drawString(text2, x2, y2);
		
		
		//MENU
		
		
		//NEW GAME
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32));
		text = "New  game";
		y += pg.tileSize;
		g2.setColor(new Color(151, 164, 157));
		g2.drawString(text, x+3, y+3);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.setFont(Symbols.deriveFont(Font.BOLD, 20));
			g2.setColor(new Color(151, 164, 157));
			g2.drawString(">", x-pg.tileSize/5+3, y+3);
			g2.setColor(Color.white);
			g2.drawString(">", x-pg.tileSize/5, y);
		}
		
		//NEW GAME
		g2.setFont(Arcade.deriveFont(Font.BOLD, 32));
		text = "High  score";
		y += pg.tileSize/2;
		g2.setColor(new Color(151, 164, 157));
		g2.drawString(text, x+3, y+3);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.setFont(Symbols.deriveFont(Font.BOLD, 20));
			g2.setColor(new Color(151, 164, 157));
			g2.drawString(">", x-pg.tileSize/5+3, y+3);
			g2.setColor(Color.white);
			g2.drawString(">", x-pg.tileSize/5, y);
		}
		
		//OPCJE
		g2.setFont(Arcade.deriveFont(Font.BOLD, 32));
		text = "Options";
		y += pg.tileSize/2;
		g2.setColor(new Color(151, 164, 157));
		g2.drawString(text, x+3, y+3);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		if(commandNum == 2) {
			g2.setFont(Symbols.deriveFont(Font.BOLD, 20));
			g2.setColor(new Color(151, 164, 157));
			g2.drawString(">", x-pg.tileSize/5+3, y+3);
			g2.setColor(Color.white);
			g2.drawString(">", x-pg.tileSize/5, y);
		}
		
		//QUIT
		g2.setFont(Arcade.deriveFont(Font.BOLD, 32));
		text = "Quit";
		y += pg.tileSize/2;
		g2.setColor(new Color(151, 164, 157));
		g2.drawString(text, x+3, y+3);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		if(commandNum == 3) {
			g2.setFont(Symbols.deriveFont(Font.BOLD, 20));
			g2.setColor(new Color(151, 164, 157));
			g2.drawString(">", x-pg.tileSize/5+3, y+3);
			g2.setColor(Color.white);
			g2.drawString(">", x-pg.tileSize/5, y);
		}
		
	}
	
	public void UIgra() {
		
		if(pg.Gracz.health == 2 && pg.Gracz.collisionON == false) {
			if(counter == 0) {
				g2.drawImage(hp2, 20, -10, pg.tileSize*2, pg.tileSize, null);
				counter = 1;
			}else if(counter == 1) {
				g2.drawImage(hpdmg, 20, -10, pg.tileSize*2, pg.tileSize, null);
				counter = 0;
			}	
		}else if(pg.Gracz.health == 1 && pg.Gracz.collisionON == false) {
			if(counter == 0) {
				g2.drawImage(hp1, 20, -10, pg.tileSize*2, pg.tileSize, null);
				counter = 1;
			}else if(counter == 1) {
				g2.drawImage(hpdmg, 20, -10, pg.tileSize*2, pg.tileSize, null);
				counter = 0;
			}
		}else if(pg.Gracz.health == 0 && pg.Gracz.collisionON == false) {
			if(counter == 0) {
				g2.drawImage(hp2, 20, -10, pg.tileSize*2, pg.tileSize, null);
				counter = 1;
			}else if(counter == 1) {
				g2.drawImage(hpdmg, 20, -10, pg.tileSize*2, pg.tileSize, null);
				counter = 0;
			}
		}else if(pg.Gracz.health == 3) {
			g2.drawImage(hp3, 20, -10, pg.tileSize*2, pg.tileSize, null);
		}else if(pg.Gracz.health == 2) {
			g2.drawImage(hp2, 20, -10, pg.tileSize*2, pg.tileSize, null);
		}else if(pg.Gracz.health == 1) {
			g2.drawImage(hp1, 20, -10, pg.tileSize*2, pg.tileSize, null);
		}else if(pg.Gracz.health == 0) {
			g2.drawImage(hp0, 20, -10, pg.tileSize*2, pg.tileSize, null);
		}
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48));
		g2.setColor(Color.white);
		String text = "Score    ";
		int length = (int)g2.getFontMetrics().getStringBounds( text , g2).getWidth();
		int width = (int)g2.getFontMetrics().getStringBounds(text, g2).getHeight();
		int x = pg.screenWidth/2 - length/2 - length/8;
		int y = pg.tileSize/2;
		g2.setColor(new Color(0, 0, 0, 50));
		g2.drawString(text+pg.Gracz.score, x+3, y+3);
		g2.setColor(Color.white);
		g2.drawString(text+pg.Gracz.score, x, y);
		//int length = (int)g2.getFontMetrics().getStringBounds( text , g2).getWidth();
		
		
	}
	
	public void UIpauza() {
		
		g2.setColor(new Color (0, 0, 0, 80));
		g2.fillRect(0, 0, pg.screenWidth, pg.screenHeight);
		
		//NAPIS PAUSE
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96));
		g2.setColor(Color.white);
		String text = "PAUSED";
		int length = (int)g2.getFontMetrics().getStringBounds( text , g2).getWidth();
		int width = (int)g2.getFontMetrics().getStringBounds(text, g2).getHeight();
		int x = pg.screenWidth/2 - length/2;
		int y = pg.tileSize;
		g2.setColor(new Color(0, 0, 0, 50));
		g2.drawString(text, x+3, y+3);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		
		//MENU
		
		
		//RESTART
		g2.setFont(Arcade.deriveFont(Font.BOLD, 32));
		text = "Restart";
		y += pg.tileSize;
		g2.setColor(new Color(0, 0, 0, 50));
		g2.drawString(text, x+3, y+3);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		if(commandNumP == 0) {
			g2.setFont(Symbols.deriveFont(Font.BOLD, 20));
			g2.setColor(new Color(0, 0, 0, 50));
			g2.drawString(">", x-pg.tileSize/5+3, y+3);
			g2.setColor(Color.white);
			g2.drawString(">", x-pg.tileSize/5, y);
		}
		
		//OPCJE
		g2.setFont(Arcade.deriveFont(Font.BOLD, 32));
		text = "Options";
		y += pg.tileSize/2;
		g2.setColor(new Color(0, 0, 0, 50));
		g2.drawString(text, x+3, y+3);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		if(commandNumP == 1) {
			g2.setFont(Symbols.deriveFont(Font.BOLD, 20));
			g2.setColor(new Color(0, 0, 0, 50));
			g2.drawString(">", x-pg.tileSize/5+3, y+3);
			g2.setColor(Color.white);
			g2.drawString(">", x-pg.tileSize/5, y);
		}
		
		//MENU
		g2.setFont(Arcade.deriveFont(Font.BOLD, 32));
		text = "Main  menu";
		y += pg.tileSize/2;
		g2.setColor(new Color(0, 0, 0, 50));
		g2.drawString(text, x+3, y+3);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		if(commandNumP == 2) {
			g2.setFont(Symbols.deriveFont(Font.BOLD, 20));
			g2.setColor(new Color(0, 0, 0, 50));
			g2.drawString(">", x-pg.tileSize/5+3, y+3);
			g2.setColor(Color.white);
			g2.drawString(">", x-pg.tileSize/5, y);
		}
		
		
		
		
		
	}

	public void UIopcje() {
		
		g2.setColor(new Color (168, 181, 174));
		g2.fillRect(0, 0, pg.screenWidth, pg.screenHeight);
		
	}

	public void UIquiz() {
		
		//TŁO
		
		g2.setColor(new Color (0, 0, 0, 80));
		g2.fillRect(0, 0, pg.screenWidth, pg.screenHeight);
		
		
		
		//RAMKI
		
		//cien
		g2.setColor(new Color(0, 0, 0, 50));
		g2.fillRect(pg.screenWidth/4-pg.tileSize/2+3, pg.screenHeight/3+3, pg.tileSize, pg.tileSize);
		g2.fillRect(pg.screenWidth*3/4-pg.tileSize/2+3, pg.screenHeight/3+3, pg.tileSize, pg.tileSize);
		g2.fillRect(pg.screenWidth/2-pg.tileSize/2+3, pg.screenHeight/3+2*pg.tileSize+3, pg.tileSize, pg.tileSize);
		g2.fillRect(pg.screenWidth/2-pg.tileSize/2+3, pg.screenHeight/3-2*pg.tileSize+3, pg.tileSize, pg.tileSize);
		g2.fillRect(pg.screenWidth/2-pg.tileSize+3,  pg.screenHeight/3+3, pg.tileSize*2, pg.tileSize);
		
		//ramka
		g2.setColor(new Color (228, 219, 160));
		g2.fillRect(pg.screenWidth/4-pg.tileSize/2, pg.screenHeight/3, pg.tileSize, pg.tileSize);
		g2.fillRect(pg.screenWidth*3/4-pg.tileSize/2, pg.screenHeight/3, pg.tileSize, pg.tileSize);
		g2.fillRect(pg.screenWidth/2-pg.tileSize/2, pg.screenHeight/3+2*pg.tileSize, pg.tileSize, pg.tileSize);
		g2.fillRect(pg.screenWidth/2-pg.tileSize/2, pg.screenHeight/3-2*pg.tileSize, pg.tileSize, pg.tileSize);
		g2.fillRect(pg.screenWidth/2-pg.tileSize,  pg.screenHeight/3, pg.tileSize*2, pg.tileSize);

		//wypelnienie
		g2.setColor(new Color (241, 234, 182));
		g2.fillRect(pg.screenWidth/4-pg.tileSize/2+5, pg.screenHeight/3+5, pg.tileSize-10, pg.tileSize-10);
		g2.fillRect(pg.screenWidth*3/4-pg.tileSize/2+5, pg.screenHeight/3+5, pg.tileSize-10, pg.tileSize-10);
		g2.fillRect(pg.screenWidth/2-pg.tileSize/2+5, pg.screenHeight/3+2*pg.tileSize+5, pg.tileSize-10, pg.tileSize-10);
		g2.fillRect(pg.screenWidth/2-pg.tileSize/2+5, pg.screenHeight/3-2*pg.tileSize+5, pg.tileSize-10, pg.tileSize-10);
		g2.fillRect(pg.screenWidth/2-pg.tileSize+5,  pg.screenHeight/3+5, pg.tileSize*2-10, pg.tileSize-10);
		
		//WSKAZNIK
		
		
		if(pg.Gracz.quizWskaznik == 1) {
			g2.setColor(Color.white);
			g2.fillRect(pg.screenWidth/2-pg.tileSize/2, pg.screenHeight/3-2*pg.tileSize, pg.tileSize, pg.tileSize);
			g2.setColor(new Color (241, 234, 182));
			g2.fillRect(pg.screenWidth/2-pg.tileSize/2+5, pg.screenHeight/3-2*pg.tileSize+5, pg.tileSize-10, pg.tileSize-10);
		}
		if(pg.Gracz.quizWskaznik == 2) {
			g2.setColor(Color.white);
			g2.fillRect(pg.screenWidth*3/4-pg.tileSize/2, pg.screenHeight/3, pg.tileSize, pg.tileSize);
			g2.setColor(new Color (241, 234, 182));
			g2.fillRect(pg.screenWidth*3/4-pg.tileSize/2+5, pg.screenHeight/3+5, pg.tileSize-10, pg.tileSize-10);
		}
		if(pg.Gracz.quizWskaznik == 3) {
			g2.setColor(Color.white);
			g2.fillRect(pg.screenWidth/2-pg.tileSize/2, pg.screenHeight/3+2*pg.tileSize, pg.tileSize, pg.tileSize);
			g2.setColor(new Color (241, 234, 182));
			g2.fillRect(pg.screenWidth/2-pg.tileSize/2+5, pg.screenHeight/3+2*pg.tileSize+5, pg.tileSize-10, pg.tileSize-10);
		}
		if(pg.Gracz.quizWskaznik == 4) {
			g2.setColor(Color.white);
			g2.fillRect(pg.screenWidth/4-pg.tileSize/2, pg.screenHeight/3, pg.tileSize, pg.tileSize);
			g2.setColor(new Color (241, 234, 182));
			g2.fillRect(pg.screenWidth/4-pg.tileSize/2+5, pg.screenHeight/3+5, pg.tileSize-10, pg.tileSize-10);
		}
		
		
		
		
		
		
		//NAPISY
		
		
		
		
		

	}
	
}
