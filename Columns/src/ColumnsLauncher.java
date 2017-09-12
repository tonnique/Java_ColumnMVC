import javax.swing.JFrame;

import View.ColumnsFrame;

import java.awt.*;


public class ColumnsLauncher {

	public static void main(String[] args) {

//		ColumnsFrame frame = new ColumnsFrame();
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				ColumnsFrame frame = new ColumnsFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}
