package model;

/**
 * Class ImageTransformations extending the,
 * abstracted class AbstractImageTransformations for,
 * editing and modifying image files.
 */
public class ImageTransformations extends AbstractImageTransformations {

  /**
   * Apply a grayscale modification to the image.
   *
   * @param img Image file that is being edited.
   * @return a grayscale image.
   */
  public CustomImage applyGrayscale(CustomImage img) {
    double[] grayWeights = {0.2126, 0.7152, 0.0722};
    return applyColorTransformation(img, grayWeights, grayWeights, grayWeights);
  }

  /**
   * Apply a sepia modification to the image.
   *
   * @param img Image file that is being edited.
   * @return a sepia-toned image.
   */
  public CustomImage applySepia(CustomImage img) {
    double[] redWeights = {0.393, 0.769, 0.189};
    double[] greenWeights = {0.349, 0.686, 0.168};
    double[] blueWeights = {0.272, 0.534, 0.131};
    return applyColorTransformation(img, redWeights, greenWeights, blueWeights);
  }

  /**
   * Apply a brighten or dimmer modification to the image.
   *
   * @param img       Image file that is being edited.
   * @param increment increment in-which the file's luminosity will be affected.
   * @return a brighter or dimmer image.
   */
  public CustomImage brighten(CustomImage img, int increment) {
    return applyTransformation(img, rgb -> {
      int red = clamp(rgb[0] + increment);
      int green = clamp(rgb[1] + increment);
      int blue = clamp(rgb[2] + increment);
      return new int[]{red, green, blue};
    });

  }

  /**
   * Apply a horizontal flip modification to the image.
   *
   * @param img Image file that is being edited.
   * @return a horizontally flipped image.
   */
  public CustomImage flipHorizontal(CustomImage img) {
    int width = img.getWidth();
    int height = img.getHeight();
    CustomImage flippedImg = new CustomImage(width, height);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        flippedImg.setPixel(width - 1 - x, y, img.getPixel(x, y));
      }
    }

    return flippedImg;
  }

  /**
   * Apply a vertical flip modification to the image.
   *
   * @param img Image file that is being edited.
   * @return a vertically flipped image.
   */
  public CustomImage flipVertical(CustomImage img) {
    int width = img.getWidth();
    int height = img.getHeight();
    CustomImage flippedImg = new CustomImage(width, height);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        flippedImg.setPixel(x, height - 1 - y, img.getPixel(x, y));
      }
    }

    return flippedImg;
  }

  /**
   * Apply a blur modification to the image.
   *
   * @param img Image file that is being edited.
   * @return a blurred image.
   */
  public CustomImage blur(CustomImage img) {
    float[][] kernel = {
            {1 / 16f, 1 / 8f, 1 / 16f},
            {1 / 8f, 1 / 4f, 1 / 8f},
            {1 / 16f, 1 / 8f, 1 / 16f}
    };
    return applyKernel(img, kernel);
  }

  /**
   * Apply a sharpen modification to the image.
   *
   * @param img Image file that is being edited.
   * @return a sharpened image.
   */
  public CustomImage sharpen(CustomImage img) {
    float[][] kernel = {
            {-1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f},
            {-1 / 8f, 1 / 4f, 1 / 4f, 1 / 4f, -1 / 8f},
            {-1 / 8f, 1 / 4f, 1, 1 / 4f, -1 / 8f},
            {-1 / 8f, 1 / 4f, 1 / 4f, 1 / 4f, -1 / 8f},
            {-1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f}
    };
    return applyKernel(img, kernel);
  }

  /**
   * Returns the red component of an image.
   *
   * @param img Image file that is being edited.
   * @return a red image.
   */
  public CustomImage visualizeRed(CustomImage img) {
    return applyTransformation(img, rgb -> new int[]{rgb[0], rgb[0], rgb[0]});
  }


  /**
   * Returns the green component of an image.
   *
   * @param img Image file that is being edited.
   * @return a green image.
   */
  public CustomImage visualizeGreen(CustomImage img) {
    return applyTransformation(img, rgb -> new int[]{rgb[1], rgb[1], rgb[1]});
  }

  /**
   * Returns the blue component of an image.
   *
   * @param img Image file that is being edited.
   * @return a blue image.
   */
  public CustomImage visualizeBlue(CustomImage img) {
    return applyTransformation(img, rgb -> new int[]{rgb[2], rgb[2], rgb[2]});
  }

  /**
   * Returns the value component of an image.
   *
   * @param img Image file that is being edited.
   * @return an edited image.
   */
  public CustomImage visualizeValue(CustomImage img) {
    return applyTransformation(img, rgb -> {
      int value = Math.max(rgb[0], Math.max(rgb[1], rgb[2]));
      return new int[]{value, value, value};
    });
  }

  /**
   * Returns the intensity component of an image.
   *
   * @param img Image file that is being edited.
   * @return an intensified image.
   */
  public CustomImage visualizeIntensity(CustomImage img) {
    return applyTransformation(img, rgb -> {
      int intensity = (rgb[0] + rgb[1] + rgb[2]) / 3;
      return new int[]{intensity, intensity, intensity};
    });
  }

  /**
   * Returns the luma component of an image.
   *
   * @param img Image file that is being edited.
   * @return an image with increased luma.
   */
  public CustomImage visualizeLuma(CustomImage img) {
    return applyTransformation(img, rgb -> {
      int luma = (int) (0.2126 * rgb[0] + 0.7152 * rgb[1] + 0.0722 * rgb[2]);
      return new int[]{luma, luma, luma};
    });
  }

  /**
   * Apply a brighten or dimmer modification to the image.
   *
   * @param img       Image file that is being edited.
   * @param increment increment in-which the file's luminosity will be affected.
   * @return a brighter or dimmer image.
   */
  public CustomImage adjustBrightness(CustomImage img, int increment) {
    return applyTransformation(img, rgb -> {
      int red = Math.min(255, Math.max(0, rgb[0] + increment));
      int green = Math.min(255, Math.max(0, rgb[1] + increment));
      int blue = Math.min(255, Math.max(0, rgb[2] + increment));
      return new int[]{red, green, blue};
    });
  }

  /**
   * Splits an image into three different components.
   *
   * @param img Image file that is being edited.
   * @return one image file for each red, green, blue components.
   */
  public CustomImage[] splitRGB(CustomImage img) {
    int width = img.getWidth();
    int height = img.getHeight();

    CustomImage redImage = new CustomImage(width, height);
    CustomImage greenImage = new CustomImage(width, height);
    CustomImage blueImage = new CustomImage(width, height);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int[] rgb = img.getPixel(x, y);

        redImage.setPixel(x, y, rgb[0], 0, 0);
        greenImage.setPixel(x, y, 0, rgb[1], 0);
        blueImage.setPixel(x, y, 0, 0, rgb[2]);
      }
    }

    return new CustomImage[]{redImage, greenImage, blueImage};
  }

  /**
   * Returns the combined image component.
   *
   * @param redImage   red image file.
   * @param greenImage green image file.
   * @param blueImage  blue image file.
   * @return an image with all three components combined.
   */
  public CustomImage combineRGB(CustomImage redImage, CustomImage greenImage,
                                CustomImage blueImage) {
    int width = redImage.getWidth();
    int height = redImage.getHeight();

    CustomImage colorImage = new CustomImage(width, height);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int[] redPixel = redImage.getPixel(x, y);
        int[] greenPixel = greenImage.getPixel(x, y);
        int[] bluePixel = blueImage.getPixel(x, y);

        int red = redPixel[0];
        int green = greenPixel[1];
        int blue = bluePixel[2];

        colorImage.setPixel(x, y, new int[]{red, green, blue});
      }
    }

    return colorImage;
  }

  /**
   * Downscales the given image to the specified width and height.
   * This method reduces the dimensions of the input image while maintaining
   * the aspect ratio and mapping original pixels to the new dimensions using
   * proportional scaling.
   *
   * @param img       the image to be downscaled
   * @param newWidth  the desired width of the downscaled image
   * @param newHeight the desired height of the downscaled image
   * @return a new instance representing the downscaled image
   * @throws IllegalArgumentException if the provided dimensions are invalid.
   */
  public CustomImage downscale(CustomImage img, int newWidth, int newHeight) {
    if (newWidth <= 0 || newHeight <= 0
            || newWidth > img.getWidth() || newHeight > img.getHeight()) {
      throw new IllegalArgumentException("Invalid dimensions. "
              + "Ensure they are positive and less than "
              + "or equal to the original dimensions.");
    }

    int originalWidth = img.getWidth();
    int originalHeight = img.getHeight();

    CustomImage downscaledImage = new CustomImage(newWidth, newHeight);

    for (int y = 0; y < newHeight; y++) {
      for (int x = 0; x < newWidth; x++) {
        int originalX = x * originalWidth / newWidth;
        int originalY = y * originalHeight / newHeight;

        int[] pixel = img.getPixel(originalX, originalY);
        downscaledImage.setPixel(x, y, pixel);
      }
    }

    return downscaledImage;
  }

  /**
   * Applies a specified transformation to the source image using a mask image.
   * Only the pixels where the mask is black (0, 0, 0) are transformed; other
   * pixels retain their original values.
   *
   * @param source         the source image to be transformed
   * @param mask           the mask image specifying areas to apply the transformation
   * @param transformation the name of the transformation to apply.
   * @return a new {@code CustomImage} instance with the transformation applied to specified areas.
   * @throws IllegalArgumentException if the source and mask images have different dimensions.
   */
  public CustomImage applyWithMask(CustomImage source, CustomImage mask, String transformation) {
    if (source.getWidth() != mask.getWidth() || source.getHeight() != mask.getHeight()) {
      throw new IllegalArgumentException("Source and mask images must have the same dimensions.");
    }

    CustomImage result = source.copy();

    for (int y = 0; y < source.getHeight(); y++) {
      for (int x = 0; x < source.getWidth(); x++) {
        int[] maskPixel = mask.getPixel(x, y);

        if (maskPixel[0] < 10 && maskPixel[1] < 10 && maskPixel[2] < 10) {
          int[] transformedPixel = applyTransformationToPixel(source, x, y, transformation);
          result.setPixel(x, y, transformedPixel);
        }
      }
    }

    return result;
  }

  /**
   * Applies the specified pixel-level transformation to a single pixel
   * based on the transformation type.
   *
   * @param source         the source image as a {@code CustomImage}.
   * @param x              the x-coordinate of the pixel to transform.
   * @param y              the y-coordinate of the pixel to transform.
   * @param transformation the type of transformation to apply.
   * @return an array representing the transformed pixel's RGB values.
   * @throws IllegalArgumentException if the transformation type is unknown.
   */
  private int[] applyTransformationToPixel(CustomImage source,
                                           int x, int y, String transformation) {
    int[] pixel = source.getPixel(x, y);

    switch (transformation) {
      case "grayscale":
        int gray = (int) (0.2126 * pixel[0] + 0.7152 * pixel[1] + 0.0722 * pixel[2]);
        return new int[]{gray, gray, gray};

      case "sepia":
        int red = Math.min(255, (int) (pixel[0] * 0.393 + pixel[1] * 0.769 + pixel[2] * 0.189));
        int green = Math.min(255, (int) (pixel[0] * 0.349 + pixel[1] * 0.686 + pixel[2] * 0.168));
        int blue = Math.min(255, (int) (pixel[0] * 0.272 + pixel[1] * 0.534 + pixel[2] * 0.131));
        return new int[]{red, green, blue};

      case "red-component":
        return new int[]{pixel[0], 0, 0};

      case "green-component":
        return new int[]{0, pixel[1], 0};

      case "blue-component":
        return new int[]{0, 0, pixel[2]};

      case "blur":
        return applyKernelAtPixel(source, x, y, new float[][] {
                {1 / 16f, 1 / 8f, 1 / 16f},
                {1 / 8f, 1 / 4f, 1 / 8f},
                {1 / 16f, 1 / 8f, 1 / 16f}
        });

      case "sharpen":
        return applyKernelAtPixel(source, x, y, new float[][] {
                {-1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f},
                {-1 / 8f, 1 / 4f, 1 / 4f, 1 / 4f, -1 / 8f},
                {-1 / 8f, 1 / 4f, 1, 1 / 4f, -1 / 8f},
                {-1 / 8f, 1 / 4f, 1 / 4f, 1 / 4f, -1 / 8f},
                {-1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f}
        });

      default:
        throw new IllegalArgumentException("Unknown transformation: " + transformation);
    }
  }

  /**
   * Applies a convolution kernel to a specific pixel in the image.
   * The kernel considers the pixel's neighbors, applies weights, and computes
   * the resulting RGB values.
   *
   * @param source the source image as a CustomImage.
   * @param x      the x-coordinate of the pixel to transform.
   * @param y      the y-coordinate of the pixel to transform.
   * @param kernel a 2D array representing the convolution kernel.
   * @return an array representing the transformed pixel's RGB values.
   */
  private int[] applyKernelAtPixel(CustomImage source, int x, int y, float[][] kernel) {
    int width = source.getWidth();
    int height = source.getHeight();

    float[] rgb = {0, 0, 0};
    int kernelHeight = kernel.length;
    int kernelWidth = kernel[0].length;
    int kernelOffsetY = kernelHeight / 2;
    int kernelOffsetX = kernelWidth / 2;

    // Apply kernel
    for (int ky = 0; ky < kernelHeight; ky++) {
      for (int kx = 0; kx < kernelWidth; kx++) {
        int neighborX = clampIndex(x + kx - kernelOffsetX, width);
        int neighborY = clampIndex(y + ky - kernelOffsetY, height);

        int[] neighborPixel = source.getPixel(neighborX, neighborY);
        float weight = kernel[ky][kx];
        rgb[0] += neighborPixel[0] * weight;
        rgb[1] += neighborPixel[1] * weight;
        rgb[2] += neighborPixel[2] * weight;
      }
    }

    return new int[]{
            clamp((int) rgb[0]),
            clamp((int) rgb[1]),
            clamp((int) rgb[2])
    };
  }

  /**
   * Ensures that a pixel value remains within the valid range of 0 to 255.
   *
   * @param value the pixel value to clamp.
   * @return the clamped pixel value, which will be between 0 and 255.
   */
  protected int clamp(int value) {
    return Math.max(0, Math.min(255, value));
  }

  /**
   * Clamps a given index to ensure it stays within the valid image bounds.
   * Used for edge cases where neighboring indices might go out of bounds.
   *
   * @param index the index to clamp.
   * @param max   the maximum allowed value for the index (e.g., width or height of the image).
   * @return the clamped index value, which will be between 0 and {@code max - 1}.
   */
  private int clampIndex(int index, int max) {
    return Math.max(0, Math.min(index, max - 1));
  }

}
