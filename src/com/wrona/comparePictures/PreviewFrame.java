package com.wrona.comparePictures;

import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class PreviewFrame extends JFrame {

	private BufferedImage image;

	public PreviewFrame(BufferedImage image, String title) {
		super();
		this.setImage(image);
		setTitle(title);

		setSize(400, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);

		Box box = Box.createVerticalBox();
		setContentPane(new JScrollPane(box));
		box.add(new JLabel(new ImageIcon(image)));
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
