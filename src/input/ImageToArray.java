package input;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ImageToArray {

	//dimensions of image (since only the input image will be loaded in this fashion)
	private static int w = 0, h = 0;
	
	public static byte[] ImageToPixels(File file) {
		byte[] pixels = null;
		try {

			BufferedImage img = ImageIO.read(file);
			
			w = img.getWidth();		//sets image dimensions, to be used in determining whether there is an alpha channel or not
			h = img.getHeight();
			
			pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();	//converts image to an array of values
																					//4 per pixel with alpha channel, 3 without (alpha, b, g, r)
		
		} catch (IOException e) {
			e.printStackTrace();
		}

		return pixels;
	}

	//removes the alpha channel from the pixel array if there is one
	public static byte[] removeAlpha(byte[] x) {

		//if there is alpha, x.length should be h*w*4, else h*w*3
		
		return null;
	}

	//converts RGB pixel values to Y (luminance) channel
	public static ArrayList<Double> RGBtoY(byte[] x) {

		ArrayList<Double> yValues = new ArrayList<>();

		for (int n = 0; n < x.length / 4; n++) {

			int r = 255 * Math.abs(x[(4 * n) + 3]);		//finds values in pixel array
			int g = 255 * Math.abs(x[(4 * n) + 2]);		//values in pixel array are 0-1, 0-255 needed for conversion
			int b = 255 * Math.abs(x[(4 * n) + 1]);

			yValues.add((0.299 * r) + (0.587 * g) + (0.114 * b));		//converts to analog Y channel

		}
		return yValues;
	}

	//converts one dimensional Y ArrayList to two dimensional y array
	public static double[][] toArray(ArrayList<Double> yValues) {

		double[][] yArray = new double[h][w];
		int index = 0;

		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {

				yArray[i][j] = yValues.get(index);	//adds element from ArrayList to proper position in array
				index++;		//determines position of ArrayList (since it is one-dimensional, it increases consistently)

			}
		}

		return yArray;

	}
	
	// runs all the previous functions and returns the image as a two-dimensional Y array
	public static double[][] convertImage(File file) {
		byte[] pixels = ImageToPixels(file);
		ArrayList<Double> yValues = RGBtoY(pixels);
		return toArray(yValues);
	}
}
