package com.wrona.comparePictures;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class ImageRunnable implements Runnable {
	BufferedImage imageFirst, imageSecond, imageFinal;

	public ImageRunnable(BufferedImage imageFirst, BufferedImage imageSecond) {
		super();
		this.imageFirst = imageFirst;
		this.imageSecond = imageSecond;
	}

	@Override
	public void run() {
		int height, weight;
		if (imageFirst.getHeight() > imageSecond.getHeight())
			height = imageFirst.getHeight();
		else
			height = imageSecond.getHeight();

		if (imageFirst.getWidth() > imageSecond.getWidth())
			weight = imageFirst.getWidth();
		else
			weight = imageSecond.getWidth();

		imageFinal = new BufferedImage(weight, height,
				BufferedImage.TYPE_INT_ARGB);

		WritableRaster rasterFirst = imageFirst.getRaster();
		WritableRaster rasterSecond = imageSecond.getRaster();
		WritableRaster rasterFinal = imageFinal.getRaster();

		for (int i = 0; i < weight; i++) {
			for (int j = 0; j < height; j++) {
				int sampleFirst[] = new int[4];
				int sampleSecond[] = new int[4];
				try {
					rasterFirst.getPixel(i, j, sampleFirst);
				} catch (ArrayIndexOutOfBoundsException ex) {
					sampleFirst[0] = 0;
					sampleFirst[1] = 0;
					sampleFirst[2] = 0;
					sampleFirst[3] = 0;
				}
				try {
					rasterSecond.getPixel(i, j, sampleSecond);
				} catch (ArrayIndexOutOfBoundsException ex) {
					sampleSecond[0] = 0;
					sampleSecond[1] = 0;
					sampleSecond[2] = 0;
					sampleSecond[3] = 0;
				}

				int[] newPixel = comparePixels(sampleFirst, sampleSecond);
				rasterFinal.setPixel(i, j, newPixel);
			}
		}
	}
	
	/** counts correlations between a pixel
	 * 
	 * @param sampleFirst pixel from first image
	 * @param sampleSecond pixel from second image
	 * @return correlation between two pixels
	 */
	private int[] comparePixels(int sampleFirst[], int sampleSecond[]) {
		double sum = Math.sqrt(Math.pow(sampleFirst[0] - sampleSecond[0], 2)
				+ Math.pow(sampleFirst[1] - sampleSecond[1], 2)
				+ Math.pow(sampleFirst[2] - sampleSecond[2], 2));

		double sumMax = Math.sqrt(3 * 255 * 255);
		int result = (int) ((1 - sum / sumMax) * 255);

		int[] newPixel = new int[4];
		newPixel[0] = result;
		newPixel[1] = result;
		newPixel[2] = result;
		newPixel[3] = 255;

		return newPixel;
	}

	public BufferedImage getImageFinal() {
		return imageFinal;
	}

}
