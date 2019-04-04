package Analysis;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

import Group.BirdGroups;
import application.Main;
import application.MainMenuController;

class ImageAnalysisBirdGroupsTest {

	private ImageAnalysisBirdGroups analyzer;
	
	
	//Assert that there are 12 birds in the image
	@Test
	void test12BirdsInImage() throws IOException {
		File file = new File("tests/resources/flock.jpg");
		BufferedImage image = ImageIO.read(file);
		assertEquals(MainMenuController.birdNo, 12);
	}

}
