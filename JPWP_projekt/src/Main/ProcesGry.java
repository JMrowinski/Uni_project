package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JPanel;

import GenerowanieSwiata.TileZarzadzanie;

/**
 * Główna klasa zarządzająca procesem gry. Odmierza ilość wykonywanych klatek oraz dostosowuje pod nie procesy.
 * Zawiera informacje o wymiarach pola gry oraz rysuje je na ekranie (dziedziczy po klasie JPanel).
 * @author Jakub Mrowiński
 *
 */

public class ProcesGry extends JPanel implements Runnable{
	
	/**Rozmiar pojedyńczego kafelka, 32 x 32 pixele*/
	final int originalTileSize = 32;
	/**Ile razy powiększony na ekranie zostanie każdy kafelek*/
	final int scale = 4;
	/**Rozmiar kafelka który zostanie narysowany na ekranie*/
	public final int tileSize = originalTileSize * scale;
	/**Maksymalna ilość kafelków w kolumnie*/
	public final int maxScreenCol = 9;
	/**Maksymalna ilośc kafelków w rzędzie*/
	public final int maxScreenRow = 8;
	/**Szerokość ekranu w pixelach, 1152p*/
	public final int screenWidth = tileSize * maxScreenCol;
	/**Wysokość ekranu w pixelach, 1024p*/
	public final int screenHeight = tileSize * maxScreenRow;
	/**Zmienna pomocnicza*/
	public int WewnetrznyZegar = 0;
	/**Zmienna odliczająca co ile przyspieszamy prędkość postaci*/
	int test = 0;
	/**Przechowuje informację o momencie zatrzymania muzyki*/
	long timer;
	
	/**Liczba odświeżania klatek występujących podczas jednej sekundy trwania programu*/
	int FPS = 60;	//frames per second
	
	
	/**Ustawienia generowania mapy*/
	public final int worldGeneratorHeight = 4000, worldWidth = tileSize * maxScreenCol, worldHeight = tileSize * worldGeneratorHeight;
	
	TileZarzadzanie TileZ = new TileZarzadzanie(this);
	Sterowanie Ster = new Sterowanie(this);	
	Muzyka muzyka = new Muzyka();
	public Gracz Gracz = new Gracz(this, Ster);
	public UI ui = new UI(this);
	Thread gameThread;
	
	
	
	/**Zmienne określające w jakim stanie aktualnie znajduje się gra*/
	public int StanGry, Quiz = 6, Opcje = 5, Najlepszy_wynik = 4, Porazka = 3, Menu = 2, Pauza = 1, Gra = 0;

	
	public ProcesGry() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);	//lepszy rendering
		this.addKeyListener(Ster);		//sterowanie
		this.setFocusable(true);		//sterowanie
	}
	
	/**Rozpoczęcie gry, ustawia stan gry na Menu*/
	public void setupGry() {
		
		StanGry = Menu;

	}
	
	/**Rozpoczyna thread gry*/
	public void startGameThread() {	
		gameThread = new Thread(this);
		gameThread.start();	//starts gameThread
		
	}
	
	
	/**Gameloop w którym wyznaczamy na ile powinniśmy zatrzymywać thread gry aby utrzymać 60 FPS i nie nadużywać podzespołów komputera z którego korzystamy*/
	@Override
	public void run() {		
		
		double OdstepOdczytu = 1000000000/FPS;
		double NastepnyOdczyt = System.nanoTime() + OdstepOdczytu;
		
		
		while(gameThread != null) {
			
			update();
			
			repaint();
			

			try {
				
				double PozostaloDoOdczytu = NastepnyOdczyt - System.nanoTime();
				PozostaloDoOdczytu = PozostaloDoOdczytu/1000000;
				
				if(PozostaloDoOdczytu < 0) {
					PozostaloDoOdczytu = 0;
				}
				
				Thread.sleep((long) PozostaloDoOdczytu);
				
				NastepnyOdczyt += OdstepOdczytu;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/**Funkcja zawarta w pętli gameloop w której zachodzą wszystkie aktualizacje programu z częstotliwością określoną przez licznik FPS*/
	public void update() {
		
		
		if(StanGry == Gra) {
			
			//muzyka.pre_pause();
			
			test++;
			WewnetrznyZegar++;
			if(test==60) {			
				test = 0;
				//System.out.println(FPS);
				if((WewnetrznyZegar/60)%10==0) {
					Gracz.speed++;
				}
			}
			
			Gracz.update();
			if(Gracz.health == 0) {
				//System.out.println("DED");
				
				if(Gracz.health == 0) {
					
					StanGry = 3;
				}
				
			}
		}

		

	}


/**Funkcja odpowiadająca za rysowanie poszególnych elementów w odpowiedniej kolejności na ekranie*/
public void paintComponent(Graphics g) {
	
	super.paintComponent(g);
	
	Graphics2D g2 = (Graphics2D)g;	//import Graphics2D zamiast Graphics
	
	if(StanGry == Menu) {
		
		ui.draw(g2);
		
		
	}else {
		TileZ.draw(g2);		//draws map based on tile.tileManager 
		
		Gracz.draw(g2);	//draws player based on entity.player
		
		ui.draw(g2);
	}
	


	g2.dispose();	//optymalizacja pamieci
}
/**
 * Zoptymalizowana funkcja odtwarzania muzyki
 * @param i
 */
public void muzykaOdtworz(int i) {
	
	muzyka.setFile(i);
	muzyka.play();
	muzyka.loop();
}

/**
 * Funkcja zatrzymująca muzykę oraz zapisująca moment w którym została zatrzymana
 */
public void muzykaStop() {
	
	muzyka.stop();
	timer = muzyka.pre_pause();
}

/**
 * Funkcja odpowiedzialna za odtwarzanie efektów dzwiękowych
 * @param i
 */
public void efektDzwiekowy(int i) {
	
	muzyka.setFile(i);
	muzyka.play();
}

/**
 * Funkcja odtwarzająca muzykę po pauzie
 * @param i
 */
public void muzykaPauza(int i) {
	muzyka.setFile(i);
	muzyka.after_pause(timer);
}

}
