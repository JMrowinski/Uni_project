package Main;

import  java.util.Random;
public class LosowanieRownan {

    Random rand = new Random();
    int poziomtrudnosci = 10;
    int granica1 = poziomtrudnosci;		//lepiej zeby oba byly parzyste, kiedys z innej funkcji skalowanie wzgledem czasu
    int granica2 = poziomtrudnosci;
    public String symbol;
    int znak;
    int x, y;
    int pom1, pom2, pom3;
    public int wynik;
    public String[][] pytania_odpowiedzi;
    
    
    public String a, b, wynik1, wynik2, wynik3, wynik4, formula;
    public String[] wyniki_losowe;
    
    ProcesGry pg;

    
    public LosowanieRownan(ProcesGry pg) {
    	this.pg = pg;

    	pytania_odpowiedzi = new String[pg.TileZ.liczba_quizow][5];
    	int pole = 0;
    	String podtrzymanie;
    	
    	for(int i = 0; i<pg.TileZ.liczba_quizow; i++) {
    		obliczenia();
    		konwersja();
    		pole = (int)Math.floor(Math.random()*(4-1+1)+1);
    		for(int j = 0; j<5; j++) {
    			if(j == 0) {
    				System.out.println(pole);
    				pytania_odpowiedzi[i][j] = wyniki_losowe[j];
    			}
    			pytania_odpowiedzi[i][j] = wyniki_losowe[j];
    				
    		}
    		
    		podtrzymanie = pytania_odpowiedzi[i][pole];
			pytania_odpowiedzi[i][pole] = wyniki_losowe[1];
			pytania_odpowiedzi[i][1] = podtrzymanie;
    	}
    	
    	
    	
    }

    
    public void obliczenia() {
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
        	x = wynik * y; // generowanie x/y w taki sposob aby liczba zawsze byla podzielna calkowita
        	symbol = "/";
        	
        }
        
    		//wyliczenie pozostalych 3 odp
    	do
    	{
    		pom1 = (int)Math.floor(Math.random()*(wynik-(-wynik)+1)+(-wynik));
        	pom2 = (int)Math.floor(Math.random()*(wynik-(-wynik)+1)+(-wynik));
        	pom3 = (int)Math.floor(Math.random()*(wynik-(-wynik)+1)+(-wynik));
    	}
    	while( pom1 == wynik || pom2 == wynik || pom3 == wynik || pom1 == pom2 || pom1 == pom3 || pom2 == pom3);
    	
    }

    
    
    public void konwersja() {
    	wyniki_losowe = new String[5];
    	a = String.valueOf(x);
    	b = String.valueOf(y);
    	wynik1 = String.valueOf(wynik);
    	wynik2 = String.valueOf(pom1);
    	wynik3 = String.valueOf(pom2);
    	wynik4 = String.valueOf(pom3);
    	wyniki_losowe[1] = wynik1;
    	wyniki_losowe[2] = wynik2;
    	wyniki_losowe[3] = wynik3;
    	wyniki_losowe[4] = wynik4;
    	wyniki_losowe[0] = formula;
    	formula = a + " " + symbol + " " + b + " = ?";

    }

}