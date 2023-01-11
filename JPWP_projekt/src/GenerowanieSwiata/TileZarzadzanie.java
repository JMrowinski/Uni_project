package GenerowanieSwiata;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import javax.imageio.ImageIO;

import Main.ProcesGry;
/**
 * Klasa zarządzająca importowaniem kafelek i generowaniem zasobów potrzebnych dla gry. Większość zmiennych została pominięta w opisie ponieważ są typowo zmiennymi pomocniczymi
 * @author Jakub Mrowiński
 */


public class TileZarzadzanie {
	ProcesGry pg;
	/**Tablica do której wczytywane są tekstury bloków z folderu res/tiles*/
	public Tile[] tile;
	public int mapTileNum[][];
	public int liczba_quizow;
	/** Tablica przechowująca wygenerowane pytania do quizów matematycznych*/

	public String[][] pytania_odpowiedzi;
	
	public TileZarzadzanie(ProcesGry pg) {
		
		this.pg = pg;
		tile = new Tile[20];
		mapTileNum = new int[pg.maxScreenCol][pg.worldGeneratorHeight];
		
		getTileImage();
		Generuj();
		LosujRownania();
	}
	
	
/**
 * Wczytywanie tekstur do tablicy tekstur
 */
	
	public void getTileImage() {
		
		try {
			
			tile[19] = new Tile();
			tile[19].image = ImageIO.read(getClass().getResourceAsStream("/tiles/TEMP.png"));
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/cutdowntree.png"));
			
			tile[17] = new Tile();
			tile[17].image = ImageIO.read(getClass().getResourceAsStream("/tiles/bush.png"));
			
			
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/path_left.png"));
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/path_middle.png"));
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/path_right.png"));
			
			
			
			tile[6] = new Tile();
			tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/path_left1.png"));
			
			tile[7] = new Tile();
			tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/path_left2.png"));
			
			tile[8] = new Tile();
			tile[8].image = ImageIO.read(getClass().getResourceAsStream("/tiles/path_left3.png"));
			
			
			
			tile[9] = new Tile();
			tile[9].image = ImageIO.read(getClass().getResourceAsStream("/tiles/path_right1.png"));
			
			tile[10] = new Tile();
			tile[10].image = ImageIO.read(getClass().getResourceAsStream("/tiles/path_right2.png"));
			
			tile[11] = new Tile();
			tile[11].image = ImageIO.read(getClass().getResourceAsStream("/tiles/path_right3.png"));
			

			
			tile[12] = new Tile();
			tile[12].image = ImageIO.read(getClass().getResourceAsStream("/tiles/fallen_tree.png"));
			
			tile[13] = new Tile();
			tile[13].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stone.png"));
			tile[13].collison = 1;
			
			tile[14] = new Tile();
			tile[14].image = ImageIO.read(getClass().getResourceAsStream("/tiles/path_middle.png"));
			tile[14].collison = 2;				
					
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
/**
 * Generowanie losowe współrzędnych elementów na świecie wraz z przeszkodami do kwadratowej tablicy
 */
	
	public void Generuj() {
		int waga, quiz = 0;
		
		for(int i=0; i<pg.maxScreenCol; i++) {
			for(int j=0; j<pg.worldGeneratorHeight; j++) {  
				
				waga = (int)Math.floor(Math.random()*(100-0+1)+0);
				
				if(i==2) {
					if(waga>=0 && waga <=91) {
						mapTileNum[i][j]=3;
					}else if(waga>91 && waga<=94) {
						mapTileNum[i][j]=6;
					}else if(waga>94 && waga<=97) {
						mapTileNum[i][j]=7;
					}else if(waga >97 && waga<=100) {
						mapTileNum[i][j]=8;
					}
				}else if(i==3) {

					if(waga>=0 && waga <=20 && j>5 && j<3990) {
						if(mapTileNum[i][j] != 19 && mapTileNum[i][j-1] != 19 && mapTileNum[i][j+1] != 19 && mapTileNum[i+1][j] != 19
								&& mapTileNum[i+1][j+1] != 19 && mapTileNum[i+1][j-1] != 19) {

							mapTileNum[i][j-1] = 19;
							mapTileNum[i][j+1] = 19;
							mapTileNum[i+1][j] = 19;
							mapTileNum[i+1][j-1] = 19;
							mapTileNum[i+1][j+1] = 19;
							mapTileNum[i][j]=13;
						}else if(mapTileNum[i][j] != 19 && mapTileNum[i][j] != 13){
							mapTileNum[i][j]=4;
						}

						
					}else if(mapTileNum[i][j] != 19 && mapTileNum[i][j] != 13 ) {
						mapTileNum[i][j]=4;
					}
					
				}else if(i==4) {
					
					if(waga>=0 && waga <=30 && j>5 && j<3990) {
						if(mapTileNum[i][j] != 19 && mapTileNum[i][j-1] != 19 && mapTileNum[i][j+1] != 19 && mapTileNum[i+1][j] != 19
								&& mapTileNum[i+1][j+1] != 19 && mapTileNum[i+1][j-1] != 19 && mapTileNum[i-1][j] != 19 && mapTileNum[i-1][j-1] != 19 && mapTileNum[i-1][j+1] != 19) {

							mapTileNum[i][j-1] = 19;
							mapTileNum[i][j+1] = 19;
							mapTileNum[i+1][j] = 19;
							mapTileNum[i+1][j-1] = 19;
							mapTileNum[i+1][j+1] = 19;
							mapTileNum[i-1][j] = 19;
							mapTileNum[i-1][j+1] = 19;
							mapTileNum[i-1][j-1] = 19;
							mapTileNum[i][j]=13;
						}else if(mapTileNum[i][j] != 19 && mapTileNum[i][j] != 13) {
							mapTileNum[i][j]=4;
						}

						
					}else if(mapTileNum[i][j] != 19 && mapTileNum[i][j] != 13 ) {
						mapTileNum[i][j]=4;
					}
					
				}else if(i==5) {
					
					if(waga>=0 && waga <=80 && j>5 && j<3990) {
						if(mapTileNum[i][j] != 19 && mapTileNum[i][j-1] != 19 && mapTileNum[i][j+1] != 19 && 
								mapTileNum[i-1][j] != 19 && mapTileNum[i-1][j-1] != 19 && mapTileNum[i-1][j+1] != 19) {

							mapTileNum[i][j-1] = 19;
							mapTileNum[i][j+1] = 19;
							mapTileNum[i-1][j] = 19;
							mapTileNum[i-1][j+1] = 19;
							mapTileNum[i-1][j-1] = 19;
							mapTileNum[i][j]=13;
						}else if(mapTileNum[i][j] != 19 && mapTileNum[i][j] != 13) {
							mapTileNum[i][j]=4;
						}

						
					}else if(mapTileNum[i][j] != 19 && mapTileNum[i][j] != 13 ) {
						mapTileNum[i][j]=4;
					}
					
				}else if(i==6) {
					if(waga>=0 && waga <=91) {
						mapTileNum[i][j]=5;
					}else if(waga>91 && waga<=94) {
						mapTileNum[i][j]=9;
					}else if(waga>94 && waga<=97) {
						mapTileNum[i][j]=10;
					}else if(waga >97 && waga<=100) {
						mapTileNum[i][j]=11;
					}
				}else {	
					if(waga>=0 && waga <=60) {
						mapTileNum[i][j]=0;
					}else if(waga>60 && waga<=97) {
						mapTileNum[i][j]=1;
					}else if(waga>97 && waga<=99) {
						mapTileNum[i][j]=2;
					}else if(waga >99 && waga<=100) {
						mapTileNum[i][j]=17;
					} 
				}
				
			}
		}
		
		for(int i=0; i<pg.maxScreenCol; i++) {
			for(int j=0; j<pg.worldGeneratorHeight; j++) {  
				
				if(i == 3) {
					if(mapTileNum[i][j]==13 || mapTileNum[i+1][j]==13 || mapTileNum[i+2][j]==13) {
						quiz++;
					}
						
				}
				
				if(quiz == 20 && j<3990) {
					quiz = 0;
					liczba_quizow++;
					mapTileNum[i][j] = 12;
					mapTileNum[i+1][j] = 12;
					mapTileNum[i+2][j] = 12;
					mapTileNum[i][j+1] = 14;
					mapTileNum[i+1][j+1] = 14;
					mapTileNum[i+2][j+1] = 14;
				}
				
				if(mapTileNum[i][j]==19) {
					mapTileNum[i][j]=4;
				}
			}
		}
		
	}
	
	
/**
 * Rysowanie świata na podstawie wcześniej wygenerowanej tablicy, gdzie każda liczba ma przypisany blok tekstur
 * @param g2
 */
	
	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;

		
		while(worldCol < pg.maxScreenCol && worldRow < pg.worldGeneratorHeight) {
			
			int tileNum = mapTileNum[worldCol][worldRow];
			int worldX = worldCol * pg.tileSize;
			int worldY = worldRow * pg.tileSize;
			int screenX = worldX - pg.Gracz.worldX + pg.Gracz.screenX;
			int screenY = worldY - pg.Gracz.worldY + pg.Gracz.screenY;
			
			if(worldX + pg.tileSize > pg.Gracz.worldX - pg.Gracz.worldX &&
			   worldX - pg.tileSize < pg.Gracz.worldX + pg.Gracz.worldX &&
		       worldY + pg.tileSize > pg.Gracz.worldY - pg.Gracz.screenY &&
			   worldY - pg.tileSize < pg.Gracz.worldY + pg.Gracz.screenY) {
			
				
			g2.drawImage(tile[tileNum].image, worldX, screenY, pg.tileSize, pg.tileSize, null);

			}
			
			worldCol++;		
			
			if(worldCol == pg.maxScreenCol) {		
				worldCol = 0;
				worldRow++;
			}
		}
		
	}
	
	/**
	 * Funkcja losująca zbiór równań wraz z odpowiedziami, które mogą być przekazane dalej
	 */

	public void LosujRownania() {
		

	    Random rand = new Random();
	    int poziomtrudnosci = 10;
	    int granica1 = poziomtrudnosci;	
	    int granica2 = poziomtrudnosci;
	    String symbol = "";
	    int znak;
	    int x, y;
	    int pom1, pom2, pom3;
	    int wynik = 0;
	    
	    
	    
	    String a, b, wynik1, wynik2, wynik3, wynik4, formula = "";
	    String[] wyniki_losowe;
	    wyniki_losowe = new String[5];



	    
    	

	    
	    	pytania_odpowiedzi = new String[liczba_quizow*4][6];
	    	int pole = 0;
	    	String podtrzymanie;
	    	
	    	
	    	for(int i = 0; i<liczba_quizow*4; i++) {
	    		
	    		
	    	    
	    	    
	            x = (int)Math.floor(Math.random()*(granica1-1+1)+1);
	            y = (int)Math.floor(Math.random()*(granica2-1+1)+1);
	            
	            znak = rand.nextInt(4);
	            
	            
	            
	            if(znak == 0) {
	            	symbol = "+";
	            	wynik = x + y;
	            }else if(znak == 1)
	            {
	            	symbol = "-";
	            	wynik = x - y;
	            }else if(znak == 2)
	            {
	            	symbol = "*";
	            	wynik = x * y;
	            }else if(znak == 3)
	            {
	            	y = (int)Math.floor(Math.random()*(granica2/2-1+1)+1);
	            	wynik = (int)Math.floor(Math.random()*(granica1/2-1+1)+1);
	            	x = wynik * y; 
	            	symbol = "/";
	            	
	            }
	            
	            
	        		
	        	do
	        	{
	        		pom1 = (int)Math.floor(Math.random()*(wynik-(-wynik)+5)+(-wynik));
	            	pom2 = (int)Math.floor(Math.random()*(wynik-(-wynik)+2)+(-wynik));
	            	pom3 = (int)Math.floor(Math.random()*(wynik-(-wynik)+10)+(-wynik));
	        	}
	        	while( pom1 == wynik || pom2 == wynik || pom3 == wynik || pom1 == pom2 || pom1 == pom3 || pom2 == pom3);
	        	
	        	
	        	
	        	
	        	
	        	a = String.valueOf(x);
	        	b = String.valueOf(y);
	        	wynik1 = String.valueOf(wynik);
	        	wynik2 = String.valueOf(pom1);
	        	wynik3 = String.valueOf(pom2);
	        	wynik4 = String.valueOf(pom3);
	        	formula = a + " " + symbol + " " + b + " = ?";
	        	wyniki_losowe[1] = wynik1;
	        	wyniki_losowe[2] = wynik2;
	        	wyniki_losowe[3] = wynik3;
	        	wyniki_losowe[4] = wynik4;
	        	wyniki_losowe[0] = formula;
	        	

	    		

	    		pole = (int)Math.floor(Math.random()*(4-1+1)+1);
	    		for(int j = 0; j<5; j++) {
	    			if(j == 0) {
	    				pytania_odpowiedzi[i][j] = wyniki_losowe[j];
	    			}
	    			pytania_odpowiedzi[i][j] = wyniki_losowe[j];
	    				
	    		}
	    		
	    		podtrzymanie = pytania_odpowiedzi[i][pole];
				pytania_odpowiedzi[i][pole] = wyniki_losowe[1];
				pytania_odpowiedzi[i][1] = podtrzymanie;	
				pytania_odpowiedzi[i][5] = wyniki_losowe[1];
	    	}
	    	
	    	for(int k=0; k<liczba_quizow*4; k++) {

	    	}
	    	

	}
}