package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

public class Sterowanie implements KeyListener {
	
	ProcesGry pg;
	public boolean wcisnietyLewy, wcisnietyPrawy; //do animacji
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
		
		//MENU
		if(pg.StanGry == pg.Menu) {
			if(code == KeyEvent.VK_DOWN) {
				pg.ui.commandNum++;
				if(pg.ui.commandNum > 3) {
					pg.ui.commandNum = 0;
				}
			}
			if(code == KeyEvent.VK_UP) {
				pg.ui.commandNum--;
				if(pg.ui.commandNum < 0)
					pg.ui.commandNum = 3;
			}
			
			if(code == KeyEvent.VK_ENTER) {
				if(pg.ui.commandNum == 0) {
					
					//USTAWIENIA DLA RESETU GRY
					pg.muzykaOdtworz(0);
					pg.TileZ.Generuj();
					pg.Gracz.WartosciStartowe();
					pg.WewnetrznyZegar = 0;
					pg.StanGry = 0;
					
				}
				if(pg.ui.commandNum == 1) {
					//dodac pozniej
				}
				if(pg.ui.commandNum == 2) {
					//dodac pozniej
				}
				if(pg.ui.commandNum == 3) {
					System.exit(0);
					
				}
			}
		}
		
		
		//PAUZA
		if(code == KeyEvent.VK_ESCAPE) {
				
			if(pg.StanGry == pg.Gra) {
				pg.StanGry = pg.Pauza;
				//pg.muzykaStop();
			}else if(pg.StanGry == pg.Pauza) {
				//pg.muzykaPauza(0);
				pg.StanGry = pg.Gra;
			}
		}
		
		//STEROWANIE PAUZA
		if(pg.StanGry == pg.Pauza) {
			
			if(code == KeyEvent.VK_DOWN) {
				pg.ui.commandNumP++;
				if(pg.ui.commandNumP > 2) {
					pg.ui.commandNumP = 0;
				}
			}
			if(code == KeyEvent.VK_UP) {
				pg.ui.commandNumP--;
				if(pg.ui.commandNumP < 0)
					pg.ui.commandNumP = 2;
			}
			
			if(code == KeyEvent.VK_ENTER) {
				if(pg.ui.commandNumP == 0) {		//RESTART	
					pg.TileZ.Generuj();
					pg.Gracz.WartosciStartowe();
					pg.WewnetrznyZegar = 0;
					pg.StanGry = 0;
				}
				if(pg.ui.commandNumP == 1) {		//OPCJE
					pg.StanGry = 5;
				}
				if(pg.ui.commandNumP == 2) {		//MENU
					pg.muzykaStop();
					
					pg.StanGry = 2;
				}
			}
		}
		
		
		//PODCZAS GRY
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
		
		
		//QUIZ STEROWANIE
		
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
					
				}
				
				if(pg.Gracz.quizWskaznik == 2) {
					
				}
				
				if(pg.Gracz.quizWskaznik == 3) {
					
				}
				
				if(pg.Gracz.quizWskaznik == 4) {
					
				}
			}
		}
		

	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}
