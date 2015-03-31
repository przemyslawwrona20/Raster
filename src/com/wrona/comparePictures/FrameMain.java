package com.wrona.comparePictures;

import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class FrameMain extends JFrame {

	private JMenuBar menuBar;
	private JMenu fileMenu;
	private MenuCompare comparatorMenu;
	private JMenuItem closeItem;
	private JMenuItem openFirstDataItem, openSecondDataItem, compareDataItem,
			showItem, clearItem, saveDataItem;

	private static final int DEFAULT_HEIGHT = 400;
	private static final int DEFAULT_WIDTH = 400;

	public FrameMain() {
		fileMenu = new JMenu("FILE");

		closeItem = new JMenuItem("Close");
		closeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});
		fileMenu.add(closeItem);

		comparatorMenu = new MenuCompare("COMPARATOR");

		openFirstDataItem = new JMenuItem("FIRST PICTURE");
		openFirstDataItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				comparatorMenu.openFile(MenuCompare.FIRST_PICTURE);
			}
		});
		comparatorMenu.add(openFirstDataItem);

		openSecondDataItem = new JMenuItem("SECOND PICTURE");
		openSecondDataItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				comparatorMenu.openFile(MenuCompare.SECOND_PICTURE);
			}
		});
		comparatorMenu.add(openSecondDataItem);
		comparatorMenu.addSeparator();

		compareDataItem = new JMenuItem("COMPARE");
		compareDataItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				comparatorMenu.compare();
			}
		});
		comparatorMenu.add(compareDataItem);

		showItem = new JMenuItem("SHOW");
		showItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				comparatorMenu.show();
			}
		});
		comparatorMenu.add(showItem);

		clearItem = new JMenuItem("CLEAR");
		clearItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				comparatorMenu.clear();
			}
		});
		comparatorMenu.add(clearItem);

		comparatorMenu.addSeparator();

		saveDataItem = new JMenuItem("SAVE");
		saveDataItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				comparatorMenu.saveFile("PNG");

			}
		});
		saveDataItem.setToolTipText("Save in default format PNG");
		comparatorMenu.add(saveDataItem);

		JMenu formats = new JMenu("SAVE AS");

		for (String format : MenuCompare.getWriterFormats()) {
			JMenuItem formatItem = new JMenuItem(format);
			formatItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					comparatorMenu.saveFile(format);
				}
			});
			formats.add(formatItem);
		}

		comparatorMenu.add(formats);

		menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(comparatorMenu);
		setJMenuBar(menuBar);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("COMPARE PICTURES");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setVisible(true);

	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		FrameMain mainFrame = new FrameMain();
	}

}
