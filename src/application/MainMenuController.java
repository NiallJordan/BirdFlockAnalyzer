package application;

import java.awt.Color;


import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;


import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import Analysis.ImageAnalysisBirdGroups;
import Group.BirdGroups;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;


/**
 * The MainMenuController class is the controller for the Main Page 
 * for the bird flock analyzer project. It controls such options as 
 * the opening of a file, a method to use the black and white converter
 * to set an image to black and white, draws boxes around identified birds
 * and also controls the sliders. 
 * 
 * @author Niall Jordan / 20079783
 *
 */
public class MainMenuController {

	@FXML MenuBar menuBar;
	@FXML Menu fileMenu, editMenu;
	@FXML MenuItem chooseImageMenuItem;

	@FXML Button flockAnalyserButton;
	@FXML Text imageNameText, imageSizeText, flockSizeText, directionOfFlightText, patterntOfFlightText;

	public  Image image;
	public 	Image bwImage;
	public  BufferedImage bufferedImage;

	@FXML ImageView imageViewBirds, imageViewBlackAndWhite;

	File selectedImage;
	FileChooser fileChooser = new FileChooser();
	
	List<BirdGroups> bgs;
	
	@FXML Slider thresholdSlider, noiseReductionSlider; 
	public static int noiseReductionLevel = 1, birdNo = 1, thresholdLevel = 127;

	
	
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	
	
	//=====================OPEN FILE DIALOG=====================\\

	/**
	 * This method opens a dialog box to which the user can browse the system 
	 * for an image to insert and later analyse. It also shows the image name, 
	 * path and size in KB in the information box in the FX.
	 * 
	 * If anything other than an image is chosen in the dialog box, the system 
	 * will output a error message in the console notifying the user of an 
	 * invalid image exception.
	 * 
	 */
	@FXML public void openFile() {
		selectedImage = fileChooser.showOpenDialog(null);
		System.out.print("Opening File");

		if(selectedImage != null) {
			try {
				bufferedImage = ImageIO.read(selectedImage);
				image = SwingFXUtils.toFXImage(bufferedImage, null);
				String imageName = selectedImage.getName();
				Long imageSizeBytes = new Long(selectedImage.length());
				double imageSizeDouble = imageSizeBytes.doubleValue();
				double imageSizeKB = imageSizeDouble/1024;
				String actualImageSize = Double.toString(imageSizeKB);

				imageViewBirds.setImage(image);
				changeImageToBlackAndWhite();
				imageNameText.setText(imageName);
				imageSizeText.setText(actualImageSize.substring(0, 4) + "KB");
			}catch(IOException exception1) {
				exception1.printStackTrace();
			}
		}else {
			System.out.println("Invalid Image");
		}
	}

	//===================BLACK & WHITE===================\\

	/**
	 * This method takes in the image from this class and uses the processToBlackAndWhite method
	 * from the BlackAndWhiteConverter class to process the image to a black and white state.
	 * It then sets the right image view to a black and white version of the initial image.
	 * 
	 */
	public void changeImageToBlackAndWhite() {
		System.out.println("Converting Image to Black & White");
		Image processedBlackAndWhite = BlackAndWhiteConverter.processToBlackAndWhite(image,thresholdLevel);
		System.out.println("Image Converted and set to Image View");
		imageViewBlackAndWhite.setImage(processedBlackAndWhite);
	}


	//===================Draw Rectangle For Each Group===================\\

	/**
	 * Using Graphics2D, this method takes in the buffered image and for every bird found, a
	 * box is drawn around it on the original image.
	 * 
	 * Number labels are also printed on the image beside every box, showing how many birds are
	 * in the image. It also sets the flock size in the information panel in the fx to the size
	 * of the List.
	 * 
	 * @param flocks
	 */
	public void printFlockToStoredImage(List<BirdGroups> bgs) {
		List<BirdGroups> birdGroupings = ImageAnalysisBirdGroups.findBirdsInImage(BlackAndWhiteConverter.bAndWBufferedImage, noiseReductionLevel);

		for (BirdGroups birdGroup : bgs) {

			Graphics2D rect = bufferedImage.createGraphics();
			Stroke stroke = new BasicStroke(5,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,0, new float[] {3,1},0);
			rect.setStroke(stroke);
			rect.setColor(Color.RED);
			rect.drawRect(birdGroup.getX1(), birdGroup.getY1(), birdGroup.getX2() - birdGroup.getX1(), birdGroup.getY2() - birdGroup.getY1());
			rect.dispose();

			Graphics2D seqNumbers = bufferedImage.createGraphics();
			seqNumbers.setColor(Color.BLUE);
			seqNumbers.drawString(birdNo + " " ,birdGroup.getX1(), birdGroup.getY1());
			birdNo++;
			seqNumbers.dispose();
			

			image = SwingFXUtils.toFXImage(bufferedImage, null);
			imageViewBirds.setImage(image);
			int numberOfBirdsInImage = bgs.size();

			flockSizeText.setText((Integer.toString(numberOfBirdsInImage)));

		}
	}



	/**
	 * This method runs the unionfind algorithm on the image to locate the 
	 * black pixels or birds in the image. It then returns the above printFlockToStoredImage
	 * method, which draws the box around each bird.
	 * 
	 * @param e
	 */
	@FXML
	public void analyseFlock(ActionEvent e) {
		System.out.println("Analysing Flock");
		List<BirdGroups> bgs = ImageAnalysisBirdGroups.findBirdsInImage(BlackAndWhiteConverter.bAndWBufferedImage, noiseReductionLevel);
		
		System.out.println("Printing Boxes to the Image");
		printFlockToStoredImage(bgs);
	}

	//===================NOISE AND OUTLIER MANAGMENT===================\\


	/**
	 * Initialization method for sliders on the fx.
	 * 
	 */

	@FXML
	public void initialize() {
		System.out.println("Initializing Sliders");
		
		noiseReductionSlider.setValue(noiseReductionLevel);
		noiseReductionSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,Number oldvalue,Number newValue) {
				if(image != null) {
					noiseReductionLevel = newValue.intValue();
					flockSizeText.setText(Integer.toString(birdNo));
					imageViewBirds.setImage(image);
				}	
			}
		});

		thresholdSlider.setValue(thresholdLevel);
		thresholdSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,Number oldvalue,Number newValue) {
				if(image != null) {
					thresholdLevel = newValue.intValue();
					changeImageToBlackAndWhite();	
				}
			}
		});
	}




	//===================EXIT===================\\

	@FXML public void exit(ActionEvent e) {
		System.out.println("----------------------");
		System.out.println("Now exiting the system");
		System.out.println("----------------------");

		Platform.exit();
	}
}
