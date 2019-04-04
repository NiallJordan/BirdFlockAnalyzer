package application;

import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class BlackAndWhiteConverter {


	public static int imageHeight;
	public static int imageWidth;
	public static WritableImage outputBlackAndWhite;
	private static PixelReader pixelReader;
	static BufferedImage bAndWBufferedImage;
	

	//===================BLACK & WHITE===================\\
	/**
	 * This method takes in the image and converts the image to 
	 * black and white using a pixel reader aswell as a threshold
	 * parameter that determines whether the pixel is black or white 
	 * depending on if it is above or below the threshold.
	 * 
	 * @param image
	 * @param threshold
	 * @return
	 */
	public static  Image processToBlackAndWhite(Image image, int threshold) {
		
		int imageHeightInt = imageHeight;
		int imageWidthInt = imageWidth;
		
		imageWidthInt = (int) image.getWidth();
		imageHeightInt = (int) image.getHeight();

		pixelReader = image.getPixelReader();		
		outputBlackAndWhite = new WritableImage(imageWidthInt,imageHeightInt);

		@SuppressWarnings("unused")
		PixelWriter pixelWriter = outputBlackAndWhite.getPixelWriter();
		for (int x = 0; x < imageWidthInt; x++) {
			for ( int y = 0; y< imageHeightInt; y++) {

				int imagePixel = pixelReader.getArgb(x, y);

				int red = ((imagePixel >> 16) & 0xff);
				int green = ((imagePixel >> 8) & 0xff);
				int blue = (imagePixel & 0xff);

				int newColor = (int)(red + green  + blue);

				int black = 0xff000000;
				int white = 0xffffffff;
				
				if(newColor < threshold)
					newColor= black;
				else
					newColor = white;  

				outputBlackAndWhite.getPixelWriter().setArgb(x,y,newColor);
			}
			bAndWBufferedImage = SwingFXUtils.fromFXImage(outputBlackAndWhite, null);
		}
		return outputBlackAndWhite;
	}
}
