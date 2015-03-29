package com.wrona.comparePictures;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class FrameMain extends JFrame {

	private JMenuBar menuBar;
	private JMenu fileMenu;
	private MenuCompare comparatorMenu;
	private JMenuItem closeItem;
	private JMenuItem openFirstDataItem, openSecondDataItem, compareDataItem,
			saveDataItem;

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

		comparatorMenu.addSeparator();

		saveDataItem = new JMenuItem("SAVE");
		saveDataItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Set<String> writerFormats = MenuCompare.getWriterFormats();
				for (String str : writerFormats) {
					System.out.println(str);
				}
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
					System.out.println(format);

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
		setSize(400, 400);
		setVisible(true);

	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		FrameMain mainFrame = new FrameMain();
	}

}
