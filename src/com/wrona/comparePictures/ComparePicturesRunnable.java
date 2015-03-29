package com.wrona.comparePictures;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class ComparePicturesRunnable implements Runnable {
	BufferedImage imageFirst, imageSecond, imageFinal;

	public ComparePicturesRunnable(BufferedImage imageFirst,
			BufferedImage imageSecond) {
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
				} catch(ArrayIndexOutOfBoundsException ex) {
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
			
				

				double sum = Math.sqrt(Math.pow(sampleFirst[0]
						- sampleSecond[0], 2)
						+ Math.pow(sampleFirst[1] - sampleSecond[1], 2)
						+ Math.pow(sampleFirst[2] - sampleSecond[2], 2));

				double sum2 = Math.sqrt(3 * 255 * 255);
				int roz = (int) ((1 - sum / sum2) * 255);

				int[] newPixel = new int[4];
				newPixel[0] = roz;
				newPixel[1] = roz;
				newPixel[2] = roz;
				newPixel[3] = 255;

				rasterFinal.setPixel(i, j, newPixel);
			}
		}
		new PreviewFrame(imageFinal, "koniec");
	}

	public BufferedImage getImageFirst() {
		return imageFirst;
	}

	public void setImageFirst(BufferedImage imageFirst) {
		this.imageFirst = imageFirst;
	}

	public BufferedImage getImageSecond() {
		return imageSecond;
	}

	public void setImageSecond(BufferedImage imageSecond) {
		this.imageSecond = imageSecond;
	}

	public BufferedImage getImageFinal() {
		return imageFinal;
	}

	public void setImageFinal(BufferedImage imageFinal) {
		this.imageFinal = imageFinal;
	}

}
