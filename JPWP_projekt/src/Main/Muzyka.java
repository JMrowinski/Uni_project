package Main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Klasa obsługująca import, rozpoczynanie, pauzowanie i stopowanie muzyki i dzwięków
 * @author Jakub Mrowiński
 *
 */
public class Muzyka {
	Clip clip;
	/**Tablica przechowująca zasoby dzwiękowe*/
	URL dzwiekurl[] = new URL[10];
	
	public Muzyka(){
		/**Autorem muzyki jest: Music by sinneschlösen from Pixabay*/
		dzwiekurl[0] = getClass().getResource("/Muzyka/main.wav");
		dzwiekurl[1] = getClass().getResource("/Muzyka/hit.wav");
	}
	
	/**
	 * Funkcja mająca na celu odtworzenie pliku dzwiękowego, wybieramy plik za pomocą parametru
	 * @param i
	 */
	public void setFile(int i) {
		
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(dzwiekurl[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		}
		catch(Exception e) {
			
		}
		
	}
	/**
	 * Odtwarzanie dzwięku
	 */
	public void play() {
		clip.start();
	}
	/**
	 * Zapisanie momentu w którym pauzujemy dzwięk
	 */
	public long pre_pause() {
		long ClipTime;
		
		ClipTime = clip.getMicrosecondPosition();
		
		return ClipTime;
	}
	
	/**
	 * Odtworzenie dzwięku na podstawie momentu zapisanego w mikrosekundach
	 * @param a
	 */
	public void after_pause(long a) {
		clip.setMicrosecondPosition(a);
		clip.start();
	}
	/**
	 * Zapętlenie muzyki
	 */
	public void loop() {
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	/**
	 * Zatrzymanie odtwarzania dzwięku
	 */
	public void stop() {
		clip.stop();
	}
}