package com.wrona.comparePictures;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class MenuCompare extends JMenu {
	public static final int FIRST_PICTURE = 1, SECOND_PICTURE = 2;
	private BufferedImage imageFirst, imageSecond, imageFinal;

	public MenuCompare(String string) {
		super(string);
	}

	/**
	 * Open image file
	 * 
	 * @param value
	 *            File's path
	 */

	public void openFile(int value) {
		JFileChooser chooser = new JFileChooser();

		// UWAGA TUTAJ
		String extensions[] = ImageIO.getReaderFileSuffixes();
		chooser.setFileFilter(new FileNameExtensionFilter("Image files",
				extensions));

		int resultChooser = chooser.showOpenDialog(this);
		if (resultChooser != JFileChooser.APPROVE_OPTION)
			return;

		File file = chooser.getSelectedFile();
		BufferedImage bufferedImage = null;

		try {
			bufferedImage = ImageIO.read(file);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e);
		}

		if (value == MenuCompare.FIRST_PICTURE) {
			imageFirst = bufferedImage;
			new FramePreview(bufferedImage, "Preview first image");
		}

		else if (value == MenuCompare.SECOND_PICTURE) {
			imageSecond = bufferedImage;
			new FramePreview(bufferedImage, "Preview second image");
		}
	}

	/**
	 * Compare two pictures if pixel is the same on both pictures the results
	 * color is white if pixel is completely different on both picture the
	 * results color is black otherwise use grayscale
	 * 
	 */
	public void compare() {
		if (imageFirst == null && imageSecond == null) {
			JOptionPane.showMessageDialog(null, "Select pictures");
			return;
		} else if (imageFirst == null) {
			JOptionPane.showMessageDialog(null, "Select first picture");
			return;

		} else if (imageSecond == null) {
			JOptionPane.showMessageDialog(null, "Select second picture");
			return;

		} else {
			ImageRunnable comparePictures = new ImageRunnable(imageFirst,
					imageSecond);
			comparePictures.run();
			imageFinal = comparePictures.getImageFinal();
			show();
		}

	}

	/**
	 * Save actual picture in file.
	 * 
	 * @param formatName
	 *            file's format
	 */
	public void saveFile(final String formatName) {
		if (imageFinal == null) {
			JOptionPane.showMessageDialog(this, "Firstly compare two pictures");
			return;
		}

		Iterator<ImageWriter> iter = ImageIO
				.getImageWritersByFormatName(formatName);
		ImageWriter writer = iter.next();
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		String[] extensions = writer.getOriginatingProvider().getFileSuffixes();
		chooser.setFileFilter(new FileNameExtensionFilter("Image files",
				extensions));
		int r = chooser.showSaveDialog(this);
		if (r != JFileChooser.APPROVE_OPTION)
			return;
		File filePath = chooser.getSelectedFile();
		String filePathString = filePath.getAbsolutePath();
		filePathString = filePathString + "." + formatName;
		File f = new File(filePathString);

		try {
			ImageOutputStream imageOut = ImageIO.createImageOutputStream(f);
			writer.setOutput(imageOut);
			writer.write(new IIOImage(imageFinal, null, null));

		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e);
		}
	}

	/**
	 * Make a set of preferred name of formats' image file
	 *
	 * @return set of names formats
	 */
	public static Set<String> getWriterFormats() {
		Set<String> writerFormats = new TreeSet<>();
		Set<String> formatNames = new TreeSet<>(Arrays.asList(ImageIO
				.getWriterFormatNames()));
		while (formatNames.size() > 0) {
			String name = formatNames.iterator().next();
			Iterator<ImageWriter> iter = ImageIO
					.getImageWritersByFormatName(name);
			ImageWriter writer = iter.next();

			String[] names = writer.getOriginatingProvider().getFormatNames();
			String format = names[0];

			if (format.equals(format.toLowerCase()))
				format = format.toUpperCase();

			writerFormats.add(format);
			formatNames.removeAll(Arrays.asList(names));
		}
		writerFormats.remove("BMP");
		writerFormats.remove("JPG");
		writerFormats.remove("JPEG");
		writerFormats.remove("WBMP");
		return writerFormats;
	}

	/**
	 * Show final image
	 */
	public void show() {
		if (imageFinal == null) {
			JOptionPane.showMessageDialog(null, "Firstly compare pictures");
			return;
		} else {
			new FramePreview(imageFinal, "FINAL IMAGE");
		}
	}

	public void clear() {
		imageFirst = null;
		imageSecond = null;
		imageFinal = null;

		JOptionPane.showMessageDialog(null, "Pictures are deleted");
	}
}
