package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Klasa odpowiedzialna za obsługę wejścia z klawiatury i sterowanie postacią oraz wyborami w menu
 * @author Jakub Mrowiński
 *
 */
public class Sterowanie implements KeyListener {
	
	ProcesGry pg;
	public boolean wcisnietyLewy, wcisnietyPrawy;
	public int pozycja = 1;
	Timer Opoznienie = new Timer();
	
	public Sterowanie(ProcesGry pg) {
		this.pg = pg;
	}
		
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		

		if(pg.StanGry == pg.Menu) {
			if(code == KeyEvent.VK_DOWN) {
				pg.ui.commandNum++;
				if(pg.ui.commandNum > 1) {
					pg.ui.commandNum = 0;
				}
			}
			if(code == KeyEvent.VK_UP) {
				pg.ui.commandNum--;
				if(pg.ui.commandNum < 0)
					pg.ui.commandNum = 1;
			}
			
			if(code == KeyEvent.VK_ENTER) {
				if(pg.ui.commandNum == 0) {
					

					pg.muzykaOdtworz(0);
					pg.TileZ.Generuj();
					pg.Gracz.WartosciStartowe();
					pg.WewnetrznyZegar = 0;
					pg.StanGry = pg.Gra;
					
				}
				if(pg.ui.commandNum == 1) {
					System.exit(0);
					
				}
			}
		}
		
		
		//PAUZA
		if(code == KeyEvent.VK_ESCAPE) {
				
			if(pg.StanGry == pg.Gra) {
				pg.StanGry = pg.Pauza;
				pg.muzykaStop();
			}else if(pg.StanGry == pg.Pauza) {
				pg.muzykaPauza(0);
				pg.StanGry = pg.Gra;
			}
		}
		
		//STEROWANIE PAUZA
		if(pg.StanGry == pg.Pauza) {
			
			if(code == KeyEvent.VK_DOWN) {
				pg.ui.commandNumP++;
				if(pg.ui.commandNumP > 1) {
					pg.ui.commandNumP = 0;
				}
			}
			if(code == KeyEvent.VK_UP) {
				pg.ui.commandNumP--;
				if(pg.ui.commandNumP < 0)
					pg.ui.commandNumP = 1;
			}
			
			if(code == KeyEvent.VK_ENTER) {
				if(pg.ui.commandNumP == 0) {
					pg.muzykaOdtworz(0);
					pg.TileZ.Generuj();
					pg.Gracz.WartosciStartowe();
					pg.WewnetrznyZegar = 0;
					pg.StanGry = pg.Gra;
				}

				if(pg.ui.commandNumP == 1) {
					pg.muzykaStop();
					
					pg.StanGry = pg.Menu;
				}
			}
		}
		
		

		if(pg.StanGry == pg.Gra) {
			
			if(code == KeyEvent.VK_LEFT) {
				wcisnietyLewy = true;
				if(pozycja == 1) {
					pozycja = 0;
				}
				else if(pozycja == 2) {
					pozycja = 1;
				}
				Opoznienie.schedule(new TimerTask() {
				    public void run() {
				    	wcisnietyLewy = false;
				    }
				}, 250);
				
			}
			
			if(code == KeyEvent.VK_RIGHT) {
				wcisnietyPrawy = true;
				if(pozycja == 0) {
					pozycja = 1;
				}
				else if(pozycja == 1) {
					pozycja = 2;
				}
				Opoznienie.schedule(new TimerTask() {
				    public void run() {
				    	wcisnietyPrawy = false;
				    }
				}, 250);
			}
		}
		
		

		
		if(pg.StanGry == pg.Quiz) {
			
			if(code == KeyEvent.VK_UP) {
				pg.Gracz.quizWskaznik = 1;
			}
			if(code == KeyEvent.VK_RIGHT) {
				pg.Gracz.quizWskaznik = 2;
			}
			if(code == KeyEvent.VK_DOWN) {
				pg.Gracz.quizWskaznik = 3;
			}
			if(code == KeyEvent.VK_LEFT) {
				pg.Gracz.quizWskaznik = 4;
			}
			
			if(code == KeyEvent.VK_ENTER) {
				
				if(pg.Gracz.quizWskaznik == 1) {
					System.out.println("1");
					if(pg.TileZ.pytania_odpowiedzi[pg.Gracz.licznik_quiz][1] != pg.TileZ.pytania_odpowiedzi[pg.Gracz.licznik_quiz][5]) {
						pg.Gracz.licznik_quiz++;

						pg.StanGry = pg.Gra;
						pg.Gracz.collisionON = false;
						pg.Gracz.health--;
					}else {
						pg.Gracz.odpowiedzi++;
						pg.Gracz.licznik_quiz++;
					}
				}
				
				if(pg.Gracz.quizWskaznik == 2) {
					System.out.println("2");
					if(pg.TileZ.pytania_odpowiedzi[pg.Gracz.licznik_quiz][2] != pg.TileZ.pytania_odpowiedzi[pg.Gracz.licznik_quiz][5]) {
						pg.Gracz.licznik_quiz++;

						pg.StanGry = pg.Gra;
						pg.Gracz.collisionON = false;
						pg.Gracz.health--;
					}else {
						pg.Gracz.odpowiedzi++;
						pg.Gracz.licznik_quiz++;
					}
				}
				
				if(pg.Gracz.quizWskaznik == 3) {
					System.out.println("3");
					if(pg.TileZ.pytania_odpowiedzi[pg.Gracz.licznik_quiz][3] != pg.TileZ.pytania_odpowiedzi[pg.Gracz.licznik_quiz][5]) {
						pg.Gracz.licznik_quiz++;

						pg.StanGry = pg.Gra;
						pg.Gracz.collisionON = false;
						pg.Gracz.health--;
					}else {
						pg.Gracz.odpowiedzi++;
						pg.Gracz.licznik_quiz++;
					}
				}
				
				if(pg.Gracz.quizWskaznik == 4) {
					System.out.println("4");
					if(pg.TileZ.pytania_odpowiedzi[pg.Gracz.licznik_quiz][4] != pg.TileZ.pytania_odpowiedzi[pg.Gracz.licznik_quiz][5]) {
						pg.Gracz.licznik_quiz++;

						pg.StanGry = pg.Gra;
						pg.Gracz.collisionON = false;
						pg.Gracz.health--;
					}else {
						pg.Gracz.odpowiedzi++;
						pg.Gracz.licznik_quiz++;
					}
					
				}
			}
			
			if(pg.Gracz.odpowiedzi == 4) {
				pg.StanGry = pg.Gra;
			}
			
		}
		

		
		if(pg.StanGry == pg.Porazka) {
			
			if(code == KeyEvent.VK_DOWN) {
				pg.ui.commandNumD++;
				if(pg.ui.commandNumD > 1) {
					pg.ui.commandNumD = 0;
				}
			}
			if(code == KeyEvent.VK_UP) {
				pg.ui.commandNumD--;
				if(pg.ui.commandNumD < 0)
					pg.ui.commandNumD = 1;
			}
			
			if(code == KeyEvent.VK_ENTER) {
				if(pg.ui.commandNumD == 0) {
					pg.muzykaOdtworz(0);
					pg.TileZ.Generuj();
					pg.Gracz.WartosciStartowe();
					pg.WewnetrznyZegar = 0;
					pg.StanGry = pg.Gra;
				}

				if(pg.ui.commandNumD == 1) {		
					pg.muzykaStop();
					
					pg.StanGry = pg.Menu;
				}
			}
			
		}
		

	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}
