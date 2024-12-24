import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import model.CustomImage;
import model.GraphUtil;
import controller.ImageUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

/**
 * Test class for ImageUtils to test image modification.
 */
public class ImageUtilTest {
  private ImageUtil imageUtil;
  private CustomImage image;

  @Before
  public void setUp() throws IOException {
    imageUtil = new ImageUtil();
    image = imageUtil.loadImage("test/dogs.jpg");
  }

  /**
   * Tests loading images.
   */
  @Test
  public void testLoadImage() {
    assertNotNull(image);
    assertEquals(640, image.getWidth());
    assertEquals(480, image.getHeight());
  }

  /**
   * Tests loading PPM file images.
   */
  @Test
  public void testLoadPPM() throws IOException {
    CustomImage ppmImage = imageUtil.loadPPM("test/dog1.ppm");
    assertNotNull(ppmImage);
    assertEquals(640, ppmImage.getWidth());
    assertEquals(480, ppmImage.getHeight());
  }

  /**
   * Tests saving image files in a given format.
   */
  @Test
  public void testSaveImage() throws IOException {
    String outputPath = "test/dog1.png";
    imageUtil.saveImage(image, outputPath);

    File outputFile = new File(outputPath);
    assertTrue(outputFile.exists());
  }

  /**
   * Tests saving ppm files.
   */
  @Test
  public void testSavePPM() throws IOException {
    String outputPath = "test/dog1.ppm";
    imageUtil.savePPM(image, outputPath);

    File outputFile = new File(outputPath);
    assertTrue(outputFile.exists());
  }

  /**
   * Tests turning image into grayscale version.
   */
  @Test
  public void testGrayscale() {
    CustomImage grayscaleImage = imageUtil.applyGrayscale(image);
    assertNotNull(grayscaleImage);

    for (int y = 0; y < grayscaleImage.getHeight(); y++) {
      for (int x = 0; x < grayscaleImage.getWidth(); x++) {
        int[] pixel = grayscaleImage.getPixel(x, y);
        assertEquals(pixel[0], pixel[1]);
        assertEquals(pixel[1], pixel[2]);
      }
    }
  }

  /**
   * Tests turning image into sepia-tone version.
   */
  @Test
  public void testSepia() {
    CustomImage originalImageCopy = new CustomImage(image.getWidth(), image.getHeight());

    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        originalImageCopy.setPixel(x, y, image.getPixel(x, y));
      }
    }

    CustomImage sepiaImage = imageUtil.applySepia(originalImageCopy);
    assertNotNull(sepiaImage);

    for (int y = 0; y < sepiaImage.getHeight(); y++) {
      for (int x = 0; x < sepiaImage.getWidth(); x++) {
        int[] originalPixel = image.getPixel(x, y);
        int[] sepiaPixel = sepiaImage.getPixel(x, y);

        assertTrue("Sepia transformation did not change pixel at (" + x + ", " + y + ")",
                originalPixel[0] != sepiaPixel[0] || originalPixel[1] != sepiaPixel[1]
                        || originalPixel[2] != sepiaPixel[2]);
      }
    }
  }

  /**
   * Tests image brightening functionality.
   */
  @Test
  public void testBrighten() {
    CustomImage brightenedImage = imageUtil.brighten(image, 50);
    assertNotNull(brightenedImage);

    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        int[] originalPixel = image.getPixel(x, y);
        int[] brightenedPixel = brightenedImage.getPixel(x, y);

        assertTrue(brightenedPixel[0] >= originalPixel[0]);
        assertTrue(brightenedPixel[1] >= originalPixel[1]);
        assertTrue(brightenedPixel[2] >= originalPixel[2]);
      }
    }
  }

  /**
   * Tests image color correcting functionality.
   */
  @Test
  public void testColorCorrect() {
    CustomImage brightenedImage = imageUtil.colorCorrect(image);
    assertNotNull(brightenedImage);

    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        int[] originalPixel = image.getPixel(x, y);
        int[] brightenedPixel = brightenedImage.getPixel(x, y);

        assertTrue(brightenedPixel[0] >= originalPixel[0]);
        assertTrue(brightenedPixel[1] >= originalPixel[1]);
        assertTrue(brightenedPixel[2] >= originalPixel[2]);
      }
    }
  }

  /**
   * Tests image brightening functionality.
   */
  @Test
  public void testLevelAdjust() {
    CustomImage brightenedImage = imageUtil.levelsAdjust(image, 20, 70, 170);
    assertNotNull(brightenedImage);
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        int[] originalPixel = image.getPixel(x, y);
        int[] brightenedPixel = brightenedImage.getPixel(x, y);
        assertTrue(brightenedPixel[0] >= originalPixel[0]);
        assertTrue(brightenedPixel[1] >= originalPixel[1]);
        assertTrue(brightenedPixel[2] >= originalPixel[2]);
      }
    }
  }


  /**
   * Tests image dimming functionality.
   */
  @Test
  public void testDarken() {
    CustomImage brightenedImage = imageUtil.brighten(image, -50);
    assertNotNull(brightenedImage);

    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        int[] originalPixel = image.getPixel(x, y);
        int[] brightenedPixel = brightenedImage.getPixel(x, y);

        assertTrue(brightenedPixel[0] >= originalPixel[0]);
        assertTrue(brightenedPixel[1] >= originalPixel[1]);
        assertTrue(brightenedPixel[2] >= originalPixel[2]);
      }
    }
  }

  /**
   * Tests horizontal flip functionality.
   */
  @Test
  public void testHorizontalFlip() {
    CustomImage flippedImage = imageUtil.flipHorizontal(image);
    assertNotNull(flippedImage);

    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        int[] originalPixel = image.getPixel(x, y);
        int[] flippedPixel = flippedImage.getPixel(image.getWidth() - 1 - x, y);

        assertArrayEquals(originalPixel, flippedPixel);
      }
    }
  }

  /**
   * Tests vertical flip functionality.
   */
  @Test
  public void testVerticalFlip() {
    CustomImage flippedImage = imageUtil.flipVertical(image);
    assertNotNull(flippedImage);

    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        int[] originalPixel = image.getPixel(x, y);
        int[] flippedPixel = flippedImage.getPixel(x, image.getHeight() - 1 - y);

        assertArrayEquals(originalPixel, flippedPixel);
      }
    }
  }

  /**
   * Tests blur functionality on image.
   */
  @Test
  public void testBlur() {
    CustomImage blurredImage = imageUtil.blur(image);
    assertNotNull(blurredImage);

    boolean changed = false;
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        int[] originalPixel = image.getPixel(x, y);
        int[] blurredPixel = blurredImage.getPixel(x, y);

        if (!java.util.Arrays.equals(originalPixel, blurredPixel)) {
          changed = true;
          break;
        }
      }
    }
    assertTrue("Blur transformation should alter at least one pixel", changed);
  }

  /**
   * Tests rgb splitting image into three color-component images.
   */
  @Test
  public void testRGBSplitAndCombine() {
    CustomImage[] rgbImages = imageUtil.splitRGB(image);

    CustomImage combinedImage = imageUtil.combineRGB(rgbImages[0], rgbImages[1], rgbImages[2]);
    assertNotNull(combinedImage);


    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        assertArrayEquals("Pixel mismatch at (" + x + ", " + y + ")",
                image.getPixel(x, y), combinedImage.getPixel(x, y));
      }
    }
  }

  /**
   * Tests visualizing red component for image.
   */
  @Test
  public void testVisualizeRed() {
    CustomImage redImage = imageUtil.applyRedVisualization(image);
    assertNotNull(redImage);

    for (int y = 0; y < redImage.getHeight(); y++) {
      for (int x = 0; x < redImage.getWidth(); x++) {
        int[] redPixel = redImage.getPixel(x, y);
        int redValue = image.getPixel(x, y)[0];

        assertEquals(redValue, redPixel[0]);

        assertEquals(0, redPixel[1]); // Green channel
        assertEquals(0, redPixel[2]); // Blue channel
      }
    }
  }


  /**
   * Tests visualizing green component for image.
   */
  @Test
  public void testVisualizeGreen() {
    CustomImage greenImage = imageUtil.applyGreenVisualization(image);
    assertNotNull(greenImage);

    for (int y = 0; y < greenImage.getHeight(); y++) {
      for (int x = 0; x < greenImage.getWidth(); x++) {
        int[] greenPixel = greenImage.getPixel(x, y);
        int greenValue = image.getPixel(x, y)[1];

        assertEquals(greenValue, greenPixel[1]);

        assertEquals(0, greenPixel[0]); // Red channel
        assertEquals(0, greenPixel[2]); // Blue channel
      }
    }
  }


  /**
   * Tests visualizing blue component for image.
   */
  @Test
  public void testVisualizeBlue() {
    CustomImage blueImage = imageUtil.applyBlueVisualization(image);
    assertNotNull(blueImage);

    for (int y = 0; y < blueImage.getHeight(); y++) {
      for (int x = 0; x < blueImage.getWidth(); x++) {
        int[] bluePixel = blueImage.getPixel(x, y);
        int expectedBlueValue = image.getPixel(x, y)[2];

        assertEquals(0, bluePixel[0]);
        assertEquals(0, bluePixel[1]);

        assertEquals(expectedBlueValue, bluePixel[2]);
      }
    }
  }


  /**
   * Tests visualizing value of component in image.
   */
  @Test
  public void testVisualizeValue() {
    CustomImage valueImage = imageUtil.applyValueVisualization(image);
    assertNotNull(valueImage);

    for (int y = 0; y < valueImage.getHeight(); y++) {
      for (int x = 0; x < valueImage.getWidth(); x++) {
        int[] originalPixel = image.getPixel(x, y);
        int[] valuePixel = valueImage.getPixel(x, y);

        int expectedValue = Math.max(originalPixel[0],
                Math.max(originalPixel[1], originalPixel[2]));
        assertEquals(expectedValue, valuePixel[0]);
        assertEquals(expectedValue, valuePixel[1]);
        assertEquals(expectedValue, valuePixel[2]);
      }
    }
  }

  /**
   * Tests visualization of intensity in image.
   */
  @Test
  public void testVisualizeIntensity() {
    CustomImage intensityImage = imageUtil.applyIntensityVisualization(image);
    assertNotNull(intensityImage);

    for (int y = 0; y < intensityImage.getHeight(); y++) {
      for (int x = 0; x < intensityImage.getWidth(); x++) {
        int[] originalPixel = image.getPixel(x, y);
        int[] intensityPixel = intensityImage.getPixel(x, y);

        int expectedIntensity = (originalPixel[0] +
                originalPixel[1] + originalPixel[2]) / 3;
        assertEquals(expectedIntensity, intensityPixel[0]);
        assertEquals(expectedIntensity, intensityPixel[1]);
        assertEquals(expectedIntensity, intensityPixel[2]);
      }
    }
  }

  /**
   * Tests visualizing luma value in image.
   */
  @Test
  public void testVisualizeLuma() {
    CustomImage lumaImage = imageUtil.applyLumaVisualization(image);
    assertNotNull(lumaImage);

    for (int y = 0; y < lumaImage.getHeight(); y++) {
      for (int x = 0; x < lumaImage.getWidth(); x++) {
        int[] originalPixel = image.getPixel(x, y);
        int[] lumaPixel = lumaImage.getPixel(x, y);

        int expectedLuma = (int) (0.2126 * originalPixel[0] + 0.7152 *
                originalPixel[1] + 0.0722 * originalPixel[2]);
        assertEquals(expectedLuma, lumaPixel[0], 1);
        assertEquals(expectedLuma, lumaPixel[1], 1);
        assertEquals(expectedLuma, lumaPixel[2], 1);
      }
    }
  }

  /**
   * Tests loading in a nonexistent image file.
   */
  @Test
  public void testLoadNonExistentImageFile() {
    String invalidImagePath = "test/non_existent_image.jpg";
    try {
      imageUtil.loadImage(invalidImagePath);
      fail("Expected an IOException to be thrown.");
    } catch (IOException e) {
      assertTrue(e instanceof IOException);
    }
  }

  /**
   * Tests loading in an invalid image format.
   */
  @Test
  public void testLoadInvalidImageFormat() {
    String invalidFilePath = "test/invalid_text_file.txt";
    try {
      imageUtil.loadImage(invalidFilePath);
      fail("Expected an IOException to be thrown.");
    } catch (IOException e) {
      assertTrue(e instanceof IOException);
    }
  }

  /**
   * Tests loading in a nonexistent ppm file.
   */
  @Test
  public void testLoadNonExistentPPMFile() {
    String nonExistentPPMPath = "test/non_existent_image.ppm";
    try {
      imageUtil.loadPPM(nonExistentPPMPath);
      fail("Expected an IOException to be thrown.");
    } catch (IOException e) {
      assertTrue(e instanceof IOException);
    }
  }

  /**
   * Tests loading in an invalid ppm format.
   */
  @Test
  public void testLoadInvalidPPMFormat() {
    String invalidPPMFilePath = "test/invalid_ppm_file.txt";
    try {
      imageUtil.loadPPM(invalidPPMFilePath);
      fail("Expected an IOException to be thrown.");
    } catch (IOException e) {
      assertTrue(e instanceof IOException);
    }
  }

  /**
   * Tests saving image to an invalid path.
   */
  @Test
  public void testSaveImageToInvalidPath() {
    String invalidOutputPath = "/invalid_directory/dog1.png";
    try {
      imageUtil.saveImage(image, invalidOutputPath);
      fail("Expected an IOException to be thrown.");
    } catch (IOException e) {
      assertTrue(e instanceof IOException);
    }
  }

  /**
   * Tests saving ppm to invalid path.
   */
  @Test
  public void testSavePPMToInvalidPath() {
    String invalidOutputPath = "/invalid_directory/dog1.ppm";
    try {
      imageUtil.savePPM(image, invalidOutputPath);
      fail("Expected an IOException to be thrown.");
    } catch (IOException e) {
      assertTrue(e instanceof IOException);
    }
  }

  /**
   * Tests image compression functionality.
   */
  @Test
  public void testCompress() {
    int compressPercentage = 50;
    CustomImage compressedImage = imageUtil.compress(image, compressPercentage);
    assertNotNull(compressedImage);

    boolean changed = false;
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        int[] originalPixel = image.getPixel(x, y);
        int[] compressedPixel = compressedImage.getPixel(x, y);
        if (!java.util.Arrays.equals(originalPixel, compressedPixel)) {
          changed = true;
          break;
        }
      }
    }
    assertTrue("Compression transformation should alter at least one pixel", changed);
  }

  /**
   * Tests split view functionality with grayscale on the right half of the image.
   */
  @Test
  public void testSplitViewGrayscale() {
    int splitPosition = 50;
    CustomImage splitGrayscaleImage = imageUtil.applySplitView(image,
            "grayscale", splitPosition, null);
    assertNotNull(splitGrayscaleImage);

    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        int[] pixel = splitGrayscaleImage.getPixel(x, y);
        if (x >= image.getWidth() * splitPosition / 100) {
          assertEquals(pixel[0], pixel[1]);
          assertEquals(pixel[1], pixel[2]);
        }
      }
    }
  }

  /**
   * Tests split view functionality with sepia on the right half of the image.
   */
  @Test
  public void testSplitViewSepia() {
    int splitPosition = 50;
    CustomImage splitSepiaImage = imageUtil.applySplitView(image,
            "sepia", splitPosition, null);
    assertNotNull(splitSepiaImage);

    boolean changed = false;
    for (int y = 0; y < splitSepiaImage.getHeight(); y++) {
      for (int x = image.getWidth() * splitPosition / 100; x < image.getWidth(); x++) {
        int[] originalPixel = image.getPixel(x, y);
        int[] sepiaPixel = splitSepiaImage.getPixel(x, y);
        if (!java.util.Arrays.equals(originalPixel, sepiaPixel)) {
          changed = true;
          break;
        }
      }
    }
    assertTrue("Split view with sepia should alter pixels on the right side", changed);
  }

  /**
   * Tests split view with sharpen operation.
   */
  @Test
  public void testSplitViewSharpen() {
    int splitPosition = 50;
    CustomImage splitSharpenImage = imageUtil.applySplitView(image,
            "sharpen", splitPosition, null);
    assertNotNull(splitSharpenImage);

    boolean changed = false;
    for (int y = 0; y < splitSharpenImage.getHeight(); y++) {
      for (int x = image.getWidth() * splitPosition / 100; x < image.getWidth(); x++) {
        int[] originalPixel = image.getPixel(x, y);
        int[] sharpenPixel = splitSharpenImage.getPixel(x, y);
        if (!java.util.Arrays.equals(originalPixel, sharpenPixel)) {
          changed = true;
          break;
        }
      }
    }
    assertTrue("Split view with sharpen should alter pixels on the right side", changed);
  }

  /**
   * Tests split view with blur operation.
   */
  @Test
  public void testSplitViewBlur() {
    int splitPosition = 50;
    CustomImage splitBlurImage = imageUtil.applySplitView(image,
            "blur", splitPosition, null);
    assertNotNull(splitBlurImage);

    boolean changed = false;
    for (int y = 0; y < splitBlurImage.getHeight(); y++) {
      for (int x = image.getWidth() * splitPosition / 100; x < image.getWidth(); x++) {
        int[] originalPixel = image.getPixel(x, y);
        int[] blurPixel = splitBlurImage.getPixel(x, y);
        if (!java.util.Arrays.equals(originalPixel, blurPixel)) {
          changed = true;
          break;
        }
      }
    }
    assertTrue("Split view with blur should alter pixels on the right side", changed);
  }


  /**
   * Tests split view functionality with level adjustment on the right half of the image.
   */
  @Test
  public void testSplitViewLevelAdjust() {
    int splitPosition = 50;
    int[] levelAdjustArgs = {10, 128, 240}; // black, mid, and white levels
    CustomImage splitLevelAdjustedImage = imageUtil.applySplitView(image,
            "levels-adjust", splitPosition, levelAdjustArgs);
    assertNotNull(splitLevelAdjustedImage);

    boolean changed = false;
    for (int y = 0; y < splitLevelAdjustedImage.getHeight(); y++) {
      for (int x = image.getWidth() * splitPosition / 100; x < image.getWidth(); x++) {
        int[] originalPixel = image.getPixel(x, y);
        int[] adjustedPixel = splitLevelAdjustedImage.getPixel(x, y);
        if (!java.util.Arrays.equals(originalPixel, adjustedPixel)) {
          changed = true;
          break;
        }
      }
    }
    assertTrue("Split view with level adjustment should alter " +
            "pixels on the right side", changed);
  }

  /**
   * Tests generating a histogram for an image.
   */
  @Test
  public void testGenerateHistogram() throws IOException {
    ImageUtil imageUtil = new ImageUtil();
    GraphUtil graphUtil = new GraphUtil();
    CustomImage image = imageUtil.loadImage("test/dogs.jpg");

    Map<String, int[]> histogram = graphUtil.generateHistogram(image);
    assertNotNull(histogram);
    assertNotNull(histogram.get("Red"));
    assertNotNull(histogram.get("Green"));
    assertNotNull(histogram.get("Blue"));
  }

  /**
   * Tests invalid input for Levels-Adjust.
   */
  @Test
  public void testInvalidLevelsAdjust() throws IOException {
    ImageUtil imageUtil = new ImageUtil();
    CustomImage image = imageUtil.loadImage("test/dogs.jpg");

    try {
      imageUtil.levelsAdjust(image, 50, 20, 3);
      fail("cant level adjust these values");
    } catch (IllegalArgumentException e) {
      //do nothing passed cause caught
    }
  }

  /**
   * Tests creating and saving a histogram image.
   */
  @Test
  public void testCreateHistogramImage() throws IOException {
    ImageUtil imageUtil = new ImageUtil();
    GraphUtil graphUtil = new GraphUtil();
    CustomImage image = imageUtil.loadImage("test/dogs.jpg");

    Map<String, int[]> histogram = graphUtil.generateHistogram(image);
    assertNotNull(histogram);

    String outputPath = "test/histogram.png";
    imageUtil.saveImage(CustomImage.fromBufferedImage(
            graphUtil.createHistogramImage(histogram)), outputPath);

    File outputFile = new File(outputPath);
    assertTrue(outputFile.exists());
    outputFile.delete();
  }
}

