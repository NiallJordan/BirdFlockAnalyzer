package Analysis;

import java.awt.image.BufferedImage;


import java.util.ArrayList;
import java.util.List;

import application.MainMenuController;
import application.QuickUnionFind;
import Group.BirdGroups;

public class ImageAnalysisBirdGroups {


	private int noiseReductionValue;

	public ImageAnalysisBirdGroups(int noiseReductionValue, List<BirdGroups> birdGroups) {
		this.setNoiseReductionValue(noiseReductionValue > 1 ? noiseReductionValue : 1);
	}


	/**
	 * This method attempts to find all birds in the image by checking
	 * for black pixels in all directions around that pixel and then 
	 * adding that pixel to the set.
	 * 
	 * @param bI
	 * @param noiseReductionValue
	 * @return
	 */
	public static List<BirdGroups> findBirdsInImage(BufferedImage bI , int noiseReductionValue){

		int imageHeightInt = bI.getHeight();
		int imageWidthInt = bI.getWidth();
		QuickUnionFind quf = new QuickUnionFind((int) (imageWidthInt * imageHeightInt));

		for(int y=0; y < bI.getHeight(); y++) {  							//where y = column
			for(int x = 0; x < bI.getWidth(); x++) {						//where x = row

				int pixel = bI.getRGB(x, y);
				final int pixelID = getPixelId(x,y,imageWidthInt);

				//If white pixel is find, skips method
				if((pixel & 1) == 1) {
					continue;
				}

				//Left
				if(x>0 && pixel == bI.getRGB(x-1, y)) {
					quf.unite(pixelID, getPixelId(x-1, y, imageWidthInt));
				}

				//Right
				if(x< imageWidthInt -1 && pixel == bI.getRGB(x+1, y)) {
					quf.unite(pixelID, getPixelId(x+1, y, imageWidthInt));
				}

				//Up
				if( y>0 && pixel == bI.getRGB(x, y-1)) {
					quf.unite(pixelID, getPixelId(x,y-1,imageWidthInt));
				}

				//Down
				if(y<imageHeightInt-1 && pixel ==bI.getRGB(x, y+1)) {
					quf.unite(pixelID, getPixelId(x , y+1, imageWidthInt));
				}


				//Up & Right
				if (y < imageHeightInt - 1 && x > 0 && pixel == bI.getRGB(x - 1, y + 1)) {
					quf.unite(pixelID, getPixelId(x - 1, y + 1, imageWidthInt));
				}

				//Up & Left
				if (y > 0 && x > 0 && pixel == bI.getRGB(x - 1, y - 1)) {
					quf.unite(pixelID, getPixelId(x - 1, y - 1, imageWidthInt));
				}

				//Down & Right
				if (y < imageHeightInt - 1 && x < imageWidthInt - 1 && pixel == bI.getRGB(x + 1, y + 1)) {
					quf.unite(pixelID, getPixelId(x + 1, y + 1, imageWidthInt));
				}

				//Down & Left
				if (y > 0 && x < imageWidthInt - 1 && pixel == bI.getRGB(x + 1, y - 1)) {
					quf.unite(pixelID, getPixelId(x + 1, y - 1, imageWidthInt));
				}

			}
		}



		List<BirdGroups> birdGroups = new ArrayList<BirdGroups>();

		for(int root: quf.getRoots(1)) {
			List <Integer> elementsOfTree = quf.getElementsOfTree(root);

			int pNode = elementsOfTree.get(0);
			int lastChildNode = elementsOfTree.get(elementsOfTree.size() -1);

			final int x1 = (int) (pNode % bI.getWidth());
			final int y1 = (int) (pNode / bI.getWidth());
			final int x2 = (int) (lastChildNode % bI.getWidth());
			final int y2 = (int) (lastChildNode / bI.getWidth());

			birdGroups.add(new BirdGroups(x1,y1,x2,y2));
		}
		return birdGroups;
	}

	//
	public static int getPixelId(int x, int y,int imageWidthInt) {
		return x + (y * imageWidthInt);
	}


	public int getNoiseReductionValue() {
		return noiseReductionValue;
	}

	public void setNoiseReductionValue(int noiseReductionValue) {
		this.noiseReductionValue = noiseReductionValue;
	}
}
