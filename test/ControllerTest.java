import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import model.CustomImage;
import controller.ImageUtil;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Test class for the Controller class, tests all image modification.
 */
public class ControllerTest {
  private ImageUtil imageUtil;
  private CustomImage originalImage;

  @Before
  public void setUp() throws IOException {
    imageUtil = new ImageUtil();
    HashMap<String, CustomImage> imageMap = new HashMap<>();
    String imagePath = "test/dogs.jpg";
    originalImage = imageUtil.loadImage(imagePath);
    imageMap.put("originalImage", originalImage);
  }

  /**
   * Helper method to validate pixel values of an image.
   */
  private void validatePixelValues(CustomImage image, int x, int y, int[] expectedRGB) {
    int[] actualRGB = image.getPixel(x, y);
    assertArrayEquals("Pixel values do not match at ("
            + x
            + ", "
            + y
            + ")", expectedRGB, actualRGB);
  }


  /**
   * Tests turning image into grayscale version.
   * @throws IOException If file is Invalid.
   */
  @Test
  public void testApplyGrayscale() throws IOException {
    String savePath = "test/dog-gray.png";
    String format = "png";

    CustomImage grayscaleImage = imageUtil.applyGrayscale(originalImage);
    imageUtil.saveImage(grayscaleImage, savePath);
    assertNotNull(grayscaleImage);
  }

  /**
   * Tests turning image into sepia-tone version.
   * @throws IOException If file is Invalid.
   */
  @Test
  public void testApplySepia() throws IOException {
    String savePath = "test/dog-sepia.png";
    String format = "png";

    CustomImage sepiaImage = imageUtil.applySepia(originalImage);
    imageUtil.saveImage(sepiaImage, savePath);
    assertNotNull(sepiaImage);
  }

  /**
   * Tests image brightening functionality.
   * @throws IOException If file is Invalid.
   */
  @Test
  public void testBrightenImage() throws IOException {
    String savePath = "test/dog-bright.png";
    String format = "png";
    int increment = 50;

    CustomImage brightenedImage = imageUtil.adjustBrightness(originalImage, increment);

    imageUtil.saveImage(brightenedImage, savePath);
    assertNotNull(brightenedImage);
  }

  /**
   * Tests image dimming functionality.
   * @throws IOException If file is Invalid.
   */
  @Test
  public void testDarkenedImage() throws IOException {
    String savePath = "test/dog-dark.png";
    String format = "png";
    int decrement = -50;

    CustomImage darkImage = imageUtil.adjustBrightness(originalImage, decrement);

    imageUtil.saveImage(darkImage, savePath);
    assertNotNull(darkImage);
  }

  /**
   * Tests horizontal flip functionality.
   * @throws IOException If file is Invalid.
   */
  @Test
  public void testHorizontalFlip() throws IOException {
    String savePath = "test/dog-hflip.png";
    String format = "png";

    CustomImage hFlipImage = imageUtil.flipHorizontal(originalImage);

    imageUtil.saveImage(hFlipImage, savePath);
    assertNotNull(hFlipImage);
  }

  /**
   * Tests vertical flip functionality.
   * @throws IOException If file is Invalid.
   */
  @Test
  public void testVerticalFlip() throws IOException {
    String savePath = "test/dog-vflip.png";
    String format = "png";

    CustomImage vFlipImage = imageUtil.flipVertical(originalImage);

    imageUtil.saveImage(vFlipImage, savePath);
    assertNotNull(vFlipImage);
  }

  /**
   * Tests blur functionality on image.
   * @throws IOException If file is Invalid.
   */
  @Test
  public void testBlur() throws IOException {
    String savePath = "test/dog-blur.png";
    String format = "png";

    CustomImage blurImage = imageUtil.blur(originalImage);

    imageUtil.saveImage(blurImage, savePath);
    assertNotNull(blurImage);
  }

  /**
   * Tests functionality for sharpening image quality.
   * @throws IOException If file is Invalid.
   */
  @Test
  public void testSharpen() throws IOException {
    String savePath = "test/dog-sharp.png";
    String format = "png";

    CustomImage sharpImage = imageUtil.sharpen(originalImage);

    imageUtil.saveImage(sharpImage, savePath);
    assertNotNull(sharpImage);
  }

  /**
   * Tests rgb splitting image into three color-component images.
   * @throws IOException If file is Invalid.
   */
  @Test
  public void testRGBSplit() throws IOException {
    String savePath1 = "test/dog-red.png";
    String savePath2 = "test/dog-green.png";
    String savePath3 = "test/dog-blue.png";
    String format = "png";

    CustomImage[] colorImage = imageUtil.splitRGB(originalImage);

    imageUtil.saveImage(colorImage[0], savePath1);
    imageUtil.saveImage(colorImage[1], savePath2);
    imageUtil.saveImage(colorImage[2], savePath3);
    assertNotNull(colorImage[0]);
    assertNotNull(colorImage[1]);
    assertNotNull(colorImage[2]);
  }

  /**
   * Tests combining three rgb value components into one image.
   * @throws IOException If file is Invalid.
   */
  @Test
  public void testRGBCombine() throws IOException {
    String savePath1 = "test/dog-combined.png";
    String format = "png";
    CustomImage[] colorImage = imageUtil.splitRGB(originalImage);

    CustomImage combinedImage = imageUtil.combineRGB(colorImage[0],
            colorImage[1], colorImage[2]);
    imageUtil.saveImage(combinedImage, savePath1);
    assertNotNull(combinedImage);
  }

  /**
   * Tests image compression functionality.
   * @throws IOException If file is invalid.
   */
  @Test
  public void testCompression() throws IOException {
    String savePath = "test/dog-compressed.png";
    int compressPercentage = 50;

    CustomImage compressedImage = imageUtil.compress(originalImage, compressPercentage);
    imageUtil.saveImage(compressedImage, savePath);

    assertNotNull(compressedImage);
  }

  /**
   * Tests color correction functionality on an image.
   * @throws IOException If file is invalid.
   */
  @Test
  public void testColorCorrection() throws IOException {
    String savePath = "test/dog-color-corrected.png";

    CustomImage colorCorrectedImage = imageUtil.colorCorrect(originalImage);
    imageUtil.saveImage(colorCorrectedImage, savePath);

    assertNotNull(colorCorrectedImage);
  }

  /**
   * Tests level adjustment functionality on an image.
   * @throws IOException If file is invalid.
   */
  @Test
  public void testLevelAdjustment() throws IOException {
    String savePath = "test/dog-level-adjusted.png";
    int blackLevel = 10;
    int midLevel = 128;
    int whiteLevel = 240;

    CustomImage levelAdjustedImage = imageUtil.levelsAdjust(
            originalImage, blackLevel, midLevel, whiteLevel);
    imageUtil.saveImage(levelAdjustedImage, savePath);

    assertNotNull(levelAdjustedImage);
  }

  /**
   * Tests split view functionality by applying sepia on the right half of the image.
   * @throws IOException If file is invalid.
   */
  @Test
  public void testSplitViewSepia() throws IOException {
    String savePath = "test/dog-split-sepia.png";
    int splitPosition = 50;

    CustomImage splitSepiaImage = imageUtil.applySplitView(originalImage,
            "sepia", splitPosition, null);
    imageUtil.saveImage(splitSepiaImage, savePath);

    assertNotNull(splitSepiaImage);
  }

  /**
   * Tests split view functionality by applying grayscale on the right half of the image.
   * @throws IOException If file is invalid.
   */
  @Test
  public void testSplitViewGrayscale() throws IOException {
    String savePath = "test/dog-split-grayscale.png";
    int splitPosition = 50;

    CustomImage splitGrayscaleImage = imageUtil.applySplitView(originalImage,
            "grayscale", splitPosition, null);
    imageUtil.saveImage(splitGrayscaleImage, savePath);

    assertNotNull(splitGrayscaleImage);
  }

  /**
   * Tests split view functionality with level adjustment on the right half of the image.
   * @throws IOException If file is invalid.
   */
  @Test
  public void testSplitViewLevelAdjustment() throws IOException {
    String savePath = "test/dog-split-level-adjusted.png";
    int splitPosition = 50;
    int[] levelAdjustArgs = {10, 128, 240};

    CustomImage splitLevelAdjustedImage = imageUtil.applySplitView(originalImage,
            "levels-adjust", splitPosition, levelAdjustArgs);
    imageUtil.saveImage(splitLevelAdjustedImage, savePath);

    assertNotNull(splitLevelAdjustedImage);
  }

  /**
   * Tests turning image into grayscale version.
   * @throws IOException If file is Invalid.
   */
  @Test
  public void testApplyGrayscalevalidate() throws IOException {
    String savePath = "test/dog-gray.png";
    String format = "png";

    CustomImage grayscaleImage = imageUtil.applyGrayscale(originalImage);
    imageUtil.saveImage(grayscaleImage, savePath);

    assertNotNull(grayscaleImage);

    int[] pixel = originalImage.getPixel(10, 10); // Original pixel
    int gray = (pixel[0] + pixel[1] + pixel[2]) / 3; // Expected grayscale
    validatePixelValues(grayscaleImage, 10, 10, new int[]{gray, gray, gray});
  }


  /**
   * Tests horizontal flip functionality.
   * @throws IOException If file is Invalid.
   */
  @Test
  public void testHorizontalFlipvalidate() throws IOException {
    String savePath = "test/dog-hflip.png";
    String format = "png";

    CustomImage hFlipImage = imageUtil.flipHorizontal(originalImage);
    imageUtil.saveImage(hFlipImage, savePath);

    assertNotNull(hFlipImage);

    int width = originalImage.getWidth();
    int[] originalPixel = originalImage.getPixel(30, 40);
    validatePixelValues(hFlipImage, width - 30 - 1, 40, originalPixel);
  }

  /**
   * Tests vertical flip functionality.
   * @throws IOException If file is Invalid.
   */
  @Test
  public void testVerticalFlipvalidate() throws IOException {
    String savePath = "test/dog-vflip.png";
    String format = "png";

    CustomImage vFlipImage = imageUtil.flipVertical(originalImage);
    imageUtil.saveImage(vFlipImage, savePath);

    assertNotNull(vFlipImage);

    int height = originalImage.getHeight();
    int[] originalPixel = originalImage.getPixel(30, 40);
    validatePixelValues(vFlipImage, 30, height - 40 - 1, originalPixel);
  }
}
