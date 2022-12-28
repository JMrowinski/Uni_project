package Main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Future title");
		
		ProcesGry ProcesGry = new ProcesGry();
		window.add(ProcesGry);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		ProcesGry.setupGry();
		ProcesGry.startGameThread();

	}

}
