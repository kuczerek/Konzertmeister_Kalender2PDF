package czerwon.konzertmeisterTerminExport;

import javax.swing.*;
import java.awt.*;

public class Splash extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel imglabel;
	private ImageIcon img;
	private JProgressBar pbar;

	public Splash() {
		super("Kalender2PDF");
		setSize(404, 310);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setUndecorated(true);
		img = new ImageIcon(getClass().getResource("/ressources/splash.png"));
		imglabel = new JLabel(img);
		add(imglabel);
		setLayout(null);
		pbar = new JProgressBar();
		pbar.setMinimum(0);
		pbar.setMaximum(100);
		pbar.setStringPainted(true);
		pbar.setForeground(Color.LIGHT_GRAY);
		imglabel.setBounds(0, 0, 404, 310);
		add(pbar);
		pbar.setPreferredSize(new Dimension(310, 30));
		pbar.setBounds(0, 290, 404, 20);
	}
	
	public void setPbarValue(int i) {
		this.pbar.setValue(i);
	}
	
	public void setPbarString(String s) {
		this.pbar.setString(s);
	}
}