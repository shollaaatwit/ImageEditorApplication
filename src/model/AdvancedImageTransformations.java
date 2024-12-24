package model;

import java.util.Map;

/**
 * Class that provides advanced image transformations, including compression,
 * color correction, level adjustment, and split view application.
 * Implements the AdvancedTransform interface and extends ImageTransformations.
 */
public class AdvancedImageTransformations
        extends ImageTransformations implements AdvancedTransform {

  /**
   * Compresses the image using a 2D Haar wavelet transform with lossy compression.
   * This operation reduces the image size by retaining a specified percentage of
   * the most significant wavelet coefficients, resulting in a compressed image.
   *
   * @param img        the CustomImage to compress.
   * @param percentage the percentage of values to retain after compression
   *                   (e.g., 50% retains half of the coefficients).
   * @return a new CustomImage object representing the compressed image.
   */
  @Override
  public CustomImage compress(CustomImage img, int percentage) {
    int width = img.getWidth();
    int height = img.getHeight();
    CustomImage compressedImage = new CustomImage(width, height);
    int[][] redChannel = img.getColorChannel(0);
    int[][] greenChannel = img.getColorChannel(1);
    int[][] blueChannel = img.getColorChannel(2);
    compressedImage.setColorChannel(0,
            CompressUtil.compressChannel(redChannel, percentage));
    compressedImage.setColorChannel(1,
            CompressUtil.compressChannel(greenChannel, percentage));
    compressedImage.setColorChannel(2,
            CompressUtil.compressChannel(blueChannel, percentage));

    return compressedImage;
  }


  /**
   * Applies an operation to a portion of the image based on the specified position.
   *
   * @param img            The image to be transformed.
   * @param operation      The operation apply (e.g., "blur", "sharpen", "levels-adjust").
   * @param position       The percentage position for split view.
   * @param additionalArgs Additional arguments for specific operations.
   * @return The transformed image with the specified operation applied partially.
   */
  public CustomImage applySplitView(CustomImage img,
                                    String operation,
                                    int position,
                                    int[] additionalArgs) {
    CustomImage splitImage = new CustomImage(img.getWidth(), img.getHeight());
    CustomImage processedImage = null;

    if (operation.equals("blur")
            || operation.equals("sharpen")
            || operation.equals("levels-adjust")) {
      switch (operation) {
        case "blur":
          processedImage = blur(img);
          break;
        case "sharpen":
          processedImage = sharpen(img);
          break;
        case "levels-adjust":
          CustomImage copyImg = img.copy();
          processedImage = levelsAdjust(copyImg, additionalArgs[0],
                  additionalArgs[1],
                  additionalArgs[2]);
          break;
        default:
          break;
      }
    }

    int splitPosition = img.getWidth() * position / 100;

    for (int y = 0; y < img.getHeight(); y++) {
      for (int x = 0; x < img.getWidth(); x++) {
        if (x < splitPosition) {
          splitImage.setPixel(x, y, img.getPixel(x, y));
        } else {
          if (processedImage != null) {
            splitImage.setPixel(x, y, processedImage.getPixel(x, y));
          } else {
            int[] pixel = img.getPixel(x, y);
            switch (operation) {
              case "sepia":
                splitImage.setPixel(x, y, applySepiaPixel(pixel));
                break;
              case "grayscale":
                splitImage.setPixel(x, y, applyGrayscalePixel(pixel));
                break;
              default:
                throw new IllegalArgumentException("Unknown operation: " + operation);
            }
          }
        }
      }
    }

    return splitImage;
  }

  /**
   * Applies a sepia tone transformation to a single pixel.
   *
   * @param pixel The RGB array of the pixel.
   * @return The transformed pixel in sepia tones.
   */
  private int[] applySepiaPixel(int[] pixel) {
    int red = (int) (pixel[0] * 0.393 + pixel[1] * 0.769 + pixel[2] * 0.189);
    int green = (int) (pixel[0] * 0.349 + pixel[1] * 0.686 + pixel[2] * 0.168);
    int blue = (int) (pixel[0] * 0.272 + pixel[1] * 0.534 + pixel[2] * 0.131);
    return new int[]{Math.min(255, red), Math.min(255, green), Math.min(255, blue)};
  }

  /**
   * Applies a grayscale transformation to a single pixel.
   *
   * @param pixel The RGB array of the pixel.
   * @return The grayscale-transformed pixel.
   */
  private int[] applyGrayscalePixel(int[] pixel) {
    int gray = (int) (pixel[0] * 0.2126 + pixel[1] * 0.7152 + pixel[2] * 0.0722);
    return new int[]{gray, gray, gray};
  }


  /**
   * Performs the color-correct operation on the CustomImage,
   * aligning the histogram peak values.
   *
   * @param img the image file that will be color corrected.
   * @return a color corrected image.
   */
  public CustomImage colorCorrect(CustomImage img) {
    GraphUtil graphUtil = new GraphUtil();
    Map<String, int[]> histograms = graphUtil.generateHistogram(img);

    int redPeak = graphUtil.findPeakValue(histograms.get("Red"));
    int greenPeak = graphUtil.findPeakValue(histograms.get("Green"));
    int bluePeak = graphUtil.findPeakValue(histograms.get("Blue"));
    int averagePeak = (redPeak + greenPeak + bluePeak) / 3;

    int redOffset = averagePeak - redPeak;
    int greenOffset = averagePeak - greenPeak;
    int blueOffset = averagePeak - bluePeak;

    return applyTransformation(img, rgb -> new int[]{
            clamp(rgb[0] + redOffset),
            clamp(rgb[1] + greenOffset),
            clamp(rgb[2] + blueOffset)});
  }

  /**
   * Adjusts the black, mid and white level values of an image.
   *
   * @param image the image that will be adjusted.
   * @param b     the black level value.
   * @param m     the mid level value.
   * @param w     the white level value.
   * @return a level adjusted image.
   */
  public CustomImage levelsAdjust(CustomImage image, int b, int m, int w) {
    if (b >= m || m >= w || b < 0 || w > 255) {
      throw new IllegalArgumentException("Invalid black, mid, and white values.");
    }
    CustomImage adjustedImage = new CustomImage(image.getWidth(), image.getHeight());
    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        int[] rgb = image.getPixel(x, y);
        for (int i = 0; i < 3; i++) {
          int originalValue = rgb[i];
          int transformedValue = applyLinearAdjustment(originalValue, b, m, w);
          rgb[i] = Math.min(255, Math.max(0, transformedValue));
        }
        adjustedImage.setPixel(x, y, rgb);
      }
    }
    return adjustedImage;
  }

  /**
   * Adjusts a color value linearly based on black, mid, and white level values.
   *
   * @param value The original color value.
   * @param b     The black level.
   * @param m     The mid level.
   * @param w     The white level.
   * @return The adjusted color value.
   */
  private int applyLinearAdjustment(int value, int b, int m, int w) {
    if (value <= b) {
      return 0;
    } else if (value <= m) {
      return (int) ((value - b) * 127.0 / (m - b));
    } else if (value <= w) {
      return (int) ((value - m) * 128.0 / (w - m) + 127);
    } else {
      return 255;
    }
  }

}

