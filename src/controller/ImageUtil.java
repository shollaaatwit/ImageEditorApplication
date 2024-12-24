package controller;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import model.AdvancedImageTransformations;
import model.CustomImage;

/**
 * Provides basic image utility functions such as,
 * loading, saving, and applying transformations to images.
 * This class handles image operations on,
 * `CustomImage` objects and supports multiple file formats.
 */
public class ImageUtil {
  private AdvancedImageTransformations imageTransformations;

  /**
   * Initializes the ImageUtil with an instance of AdvancedImageTransformations.
   */
  public ImageUtil() {
    imageTransformations = new AdvancedImageTransformations();
  }

  /**
   * Load an image from file and convert it into CustomImage format.
   *
   * @param filePath the filepath of the image being loaded in.
   * @return a CustomImage of the file loaded in.
   * @throws IOException If the file is invalid.
   */
  public CustomImage loadImage(String filePath) throws IOException {
    BufferedImage bufferedImage = ImageIO.read(new File(filePath));
    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();
    CustomImage customImage = new CustomImage(width, height);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int rgb = bufferedImage.getRGB(x, y);

        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        customImage.setPixel(x, y, red, green, blue);
      }
    }

    return customImage;
  }

  /**
   * Load an image from an ASCII PPM file.
   *
   * @param filePath filePath that is being loaded from.
   * @return a CustomImage of the loaded file.
   * @throws IOException if the file is invalid.
   */
  public CustomImage loadPPM(String filePath) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(filePath));
    String magicNumber = reader.readLine();

    if (!magicNumber.equals("P3")) {
      throw new IOException("Unsupported PPM format: " + magicNumber);
    }

    String[] dimensions = reader.readLine().split(" ");
    int width = Integer.parseInt(dimensions[0]);
    int height = Integer.parseInt(dimensions[1]);
    int maxColorValue = Integer.parseInt(reader.readLine());

    CustomImage ppmImage = new CustomImage(width, height);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int red = Integer.parseInt(reader.readLine());
        int green = Integer.parseInt(reader.readLine());
        int blue = Integer.parseInt(reader.readLine());
        ppmImage.setPixel(x, y, red, green, blue);
      }
    }

    reader.close();
    return ppmImage;
  }

  /**
   * Save a CustomImage to a file in the specified format (like png or jpg).
   *
   * @param customImage The image being saved.
   * @param outputPath  The path the image will be saved towards.
   * @throws IOException if the file is invalid.
   */
  public void saveImage(CustomImage customImage, String outputPath) throws IOException {
    if (outputPath.endsWith(".ppm")) {
      savePPM(customImage, outputPath);
    } else {
      int width = customImage.getWidth();
      int height = customImage.getHeight();
      BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

      for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
          int[] rgb = customImage.getPixel(x, y);
          int red = rgb[0];
          int green = rgb[1];
          int blue = rgb[2];
          int pixelValue = (red << 16) | (green << 8) | blue;
          bufferedImage.setRGB(x, y, pixelValue);
        }
      }

      int lastDotIndex = outputPath.lastIndexOf(".");
      if (lastDotIndex == -1) {
        throw new IOException("No file extension!");
      }

      String fileExtension = outputPath.substring(lastDotIndex + 1);
      ImageIO.write(bufferedImage, fileExtension, new File(outputPath));
    }
  }


  /**
   * Save an image to ASCII PPM format.
   *
   * @param customImage The image being saved.
   * @param filePath    the path the image is being saved to.
   * @throws IOException If the file is invalid.
   */
  public void savePPM(CustomImage customImage, String filePath) throws IOException {
    int width = customImage.getWidth();
    int height = customImage.getHeight();

    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

    writer.write("P3\n");
    writer.write(width + " " + height + "\n");
    writer.write("255\n");

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int[] rgb = customImage.getPixel(x, y);
        writer.write(rgb[0] + "\n");
        writer.write(rgb[1] + "\n");
        writer.write(rgb[2] + "\n");
      }
    }

    writer.close();
  }

  /**
   * Apply a grayscale modification to the image.
   *
   * @param img Image file that is being edited.
   * @return a grayscale image.
   */
  public CustomImage applyGrayscale(CustomImage img) {
    return imageTransformations.applyGrayscale(img);
  }

  /**
   * Apply a sepia modification to the image.
   *
   * @param img Image file that is being edited.
   * @return a sepia-toned image.
   */
  public CustomImage applySepia(CustomImage img) {
    return imageTransformations.applySepia(img);
  }

  /**
   * Apply a brighten or dimmer modification to the image.
   *
   * @param img       Image file that is being edited.
   * @param increment increment in-which the file's luminosity will be affected.
   * @return a brighter or dimmer image.
   */
  public CustomImage brighten(CustomImage img, int increment) {
    return imageTransformations.brighten(img, increment);
  }

  /**
   * Apply a horizontal flip modification to the image.
   *
   * @param img Image file that is being edited.
   * @return a horizontally flipped image.
   */
  public CustomImage flipHorizontal(CustomImage img) {
    return imageTransformations.flipHorizontal(img);
  }

  /**
   * Apply a vertical flip modification to the image.
   *
   * @param img Image file that is being edited.
   * @return a vertically flipped image.
   */
  public CustomImage flipVertical(CustomImage img) {
    return imageTransformations.flipVertical(img);
  }

  /**
   * Apply a blur modification to the image.
   *
   * @param img Image file that is being edited.
   * @return a blurred image.
   */
  public CustomImage blur(CustomImage img) {
    return imageTransformations.blur(img);
  }

  /**
   * Apply a sharpen modification to the image.
   *
   * @param img Image file that is being edited.
   * @return a sharpened image.
   */
  public CustomImage sharpen(CustomImage img) {

    return imageTransformations.sharpen(img);
  }

  /**
   * Returns the red component of an image.
   *
   * @param img Image file that is being edited.
   * @return a red image.
   */
  public CustomImage applyRedVisualization(CustomImage img) {
    return imageTransformations.visualizeRed(img);
  }

  /**
   * Returns the green component of an image.
   *
   * @param img Image file that is being edited.
   * @return a green image.
   */
  public CustomImage applyGreenVisualization(CustomImage img) {
    return imageTransformations.visualizeGreen(img);
  }

  /**
   * Returns the blue component of an image.
   *
   * @param img Image file that is being edited.
   * @return a blue image.
   */
  public CustomImage applyBlueVisualization(CustomImage img) {
    return imageTransformations.visualizeBlue(img);
  }

  /**
   * Returns the value component of an image.
   *
   * @param img Image file that is being edited.
   * @return an edited image.
   */
  public CustomImage applyValueVisualization(CustomImage img) {
    return imageTransformations.visualizeValue(img);
  }

  /**
   * Returns the intensity component of an image.
   *
   * @param img Image file that is being edited.
   * @return an intensified image.
   */
  public CustomImage applyIntensityVisualization(CustomImage img) {
    return imageTransformations.visualizeIntensity(img);
  }

  /**
   * Returns the luma component of an image.
   *
   * @param img Image file that is being edited.
   * @return an image with increased luma.
   */
  public CustomImage applyLumaVisualization(CustomImage img) {
    return imageTransformations.visualizeLuma(img);
  }

  /**
   * Apply a brighten or dimmer modification to the image.
   *
   * @param img       Image file that is being edited.
   * @param increment increment in-which the file's luminosity will be affected.
   * @return a brighter or dimmer image.
   */
  public CustomImage adjustBrightness(CustomImage img, int increment) {
    return imageTransformations.adjustBrightness(img, increment);
  }

  /**
   * Splits an image into three different components.
   *
   * @param img Image file that is being edited.
   * @return one image file for each red, green, blue components.
   */
  public CustomImage[] splitRGB(CustomImage img) {
    return imageTransformations.splitRGB(img);
  }

  /**
   * Returns the combined image component.
   *
   * @param redImage   red image file.
   * @param greenImage green image file.
   * @param blueImage  blue image file.
   * @return an image with all three components combined.
   */
  public CustomImage combineRGB(CustomImage redImage,
                                CustomImage greenImage, CustomImage blueImage) {
    return imageTransformations.combineRGB(redImage, greenImage, blueImage);
  }

  /**
   * Image util for color correction method.
   *
   * @param img image to color correct.
   * @return a color corrected image.
   */
  public CustomImage colorCorrect(CustomImage img) {
    return imageTransformations.colorCorrect(img);
  }

  /**
   * Adjusts the black, mid, and white levels of an image.
   *
   * @param image The `CustomImage` to adjust.
   * @param b     The black level.
   * @param m     The mid level.
   * @param w     The white level.
   * @return The level-adjusted `CustomImage`.
   */
  public CustomImage levelsAdjust(CustomImage image, int b, int m, int w) {
    return imageTransformations.levelsAdjust(image, b, m, w);
  }

  /**
   * Compresses the image by retaining a specified percentage of details.
   *
   * @param img                The `CustomImage` to compress.
   * @param compressPercentage The percentage of image details to retain.
   * @return The compressed `CustomImage`.
   */
  public CustomImage compress(CustomImage img, int compressPercentage) {
    return imageTransformations.compress(img, compressPercentage);
  }

  /**
   * Applies a split view on an image with a specified operation and position.
   *
   * @param img                 The `CustomImage` to process.
   * @param operation           The operation (e.g., sepia, grayscale) to apply.
   * @param splitPercentage     The percentage position where the split view begins.
   * @param additionalArguments Additional arguments for specific operations.
   * @return The processed `CustomImage`.
   */
  public CustomImage applySplitView(CustomImage img, String operation,
                                    int splitPercentage, int[] additionalArguments) {
    return imageTransformations.applySplitView(img, operation,
            splitPercentage, additionalArguments);
  }
}
