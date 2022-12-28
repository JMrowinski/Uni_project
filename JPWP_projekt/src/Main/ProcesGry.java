package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import GenerowanieSwiata.TileZarzadzanie;



public class ProcesGry extends JPanel implements Runnable{
	
	final int originalTileSize = 32;	//32x32 pixele
	final int scale = 4;
	public final int tileSize = originalTileSize * scale;	//128x128 po przeskalowaniu na ekran
	public final int maxScreenCol = 9;
	public final int maxScreenRow = 8;
	public final int screenWidth = tileSize * maxScreenCol;	//1152p
	public final int screenHeight = tileSize * maxScreenRow;	//1024p
	public int WewnetrznyZegar = 0;
	int test = 0;
	long timer;
	
	
	int FPS = 60;	//frames per second
	
	//WORLD SETTINGS
	public final int worldGeneratorHeight = 4000;
	public final int worldWidth = tileSize * maxScreenCol;
	public final int worldHeight = tileSize * worldGeneratorHeight;
	
	TileZarzadzanie TileZ = new TileZarzadzanie(this);
	Sterowanie Ster = new Sterowanie(this);	//import keyboard inputs
	Muzyka muzyka = new Muzyka();
	public Gracz Gracz = new Gracz(this, Ster);
	public UI ui = new UI(this);
	//public LosowanieRownan randomizer = new LosowanieRownan(this);
	Thread gameThread;
	
	
	
	//GAMESTATE
	public int StanGry;
	public final int Quiz = 6;
	public final int Opcje = 5;
	public final int Najlepszy_wynik = 4;
	public final int Porazka = 3;
	public final int Menu = 2;
	public final int Pauza = 1;
	public final int Gra = 0;
	
	public ProcesGry() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);	//lepszy rendering
		this.addKeyListener(Ster);		//sterowanie
		this.setFocusable(true);		//sterowanie
	}
	
	public void setupGry() {
		
	//	muzykaOdtworz(0);
		StanGry = Menu;
	//	TileZ.getTileImage();		//kiedys optymalizacja ladowania przed rozgrywka
	//	TileZ.Generuj();
	}
	
	
	public void startGameThread() {	
		gameThread = new Thread(this);
		gameThread.start();	//starts gameThread
		
	}
	
	
	//GAME LOOP
	@Override
	public void run() {		
		
		double OdstepOdczytu = 1000000000/FPS;
		double NastepnyOdczyt = System.nanoTime() + OdstepOdczytu;
		
		
		while(gameThread != null) {
			
			update();	//UPDATE information in game
			
			repaint();	//DRAW info based on UPDATE, call out paintComponent
			
			//Ustawnienie 60 klatek na sekunde odswiezania 
			try {
				
				double PozostaloDoOdczytu = NastepnyOdczyt - System.nanoTime();
				PozostaloDoOdczytu = PozostaloDoOdczytu/1000000;	//thread.sleep korzysta z mili nie nano
				
				if(PozostaloDoOdczytu < 0) {					//jesli przetwarzanie przekroczy czas do nastepnego odczytu to zerujemy aby zapobiegac zacinaniu sie gry
					PozostaloDoOdczytu = 0;
				}
				
				Thread.sleep((long) PozostaloDoOdczytu);		//pauza w oczekiwaniu na kolejna klatke gry
				
				NastepnyOdczyt += OdstepOdczytu;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
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
				
			}
		}
		if(StanGry == Pauza) {
			
		}

		

	}



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

public void muzykaOdtworz(int i) {
	
	muzyka.setFile(i);
	muzyka.play();
	muzyka.loop();
}

public void muzykaStop() {
	
	muzyka.stop();
	timer = muzyka.pre_pause();
}

public void efektDzwiekowy(int i) {
	
	muzyka.setFile(i);
	muzyka.play();
}

public void muzykaPauza(int i) {
	muzyka.setFile(i);
	muzyka.after_pause(timer);
}

}
