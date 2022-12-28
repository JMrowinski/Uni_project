package Main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Muzyka {
	Clip clip;
	URL dzwiekurl[] = new URL[10];
	
	public Muzyka(){
		
		dzwiekurl[0] = getClass().getResource("/Muzyka/main.wav");
		dzwiekurl[1] = getClass().getResource("/Muzyka/hit.wav");
	}
	
	public void setFile(int i) {
		
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(dzwiekurl[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		}
		catch(Exception e) {
			
		}
		
	}
	
	public void play() {
		clip.start();
	}
	
	public long pre_pause() {
		long ClipTime;
		
		ClipTime = clip.getMicrosecondPosition();
		
		return ClipTime;
	}
	
	public void after_pause(long a) {
		clip.setMicrosecondPosition(a);
		clip.start();
	}
	
	public void loop() {
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}
}