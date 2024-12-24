package model;

/**
 * Abstract class to simplify the functions used in order to apply,
 * modifications to images.
 */
public abstract class AbstractImageTransformations implements Transform {

  /**
   * This method will apply a standard transformation onto an image.
   *
   * @param img         the image file that is being edited.
   * @param transformer the interface that stores the rgb values.
   * @return an image with the specified transformations.
   */
  protected CustomImage applyTransformation(CustomImage img, Transformations transformer) {
    int width = img.getWidth();
    int height = img.getHeight();

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int[] rgb = img.getPixel(x, y);
        int[] transformedRGB = transformer.transform(rgb);
        img.setPixel(x, y, transformedRGB);
      }
    }
    return img;
  }

  /**
   * A method to clamp an int value to not exceed 255 or below 0.
   *
   * @param value the value that is being clamped.
   * @return the clamped int value.
   */
  protected int clamp(int value) {
    return Math.max(0, Math.min(255, value));
  }

  /**
   * This method will apply a color transformation onto an image.
   *
   * @param img          the image file that is being edited.
   * @param redWeights   weights to the red rgb value.
   * @param greenWeights weights to the green rgb value.
   * @param blueWeights  weights to the blue rgb value.
   * @return an image with the specified transformations.
   */
  protected CustomImage applyColorTransformation(CustomImage img,
                                                 double[] redWeights,
                                                 double[] greenWeights,
                                                 double[] blueWeights) {
    return applyTransformation(img, rgb -> {
      int red = rgb[0];
      int green = rgb[1];
      int blue = rgb[2];

      int newRed = clamp((int) (redWeights[0] * red + redWeights[1] *
              green + redWeights[2] * blue));
      int newGreen = clamp((int) (greenWeights[0] * red + greenWeights[1] *
              green + greenWeights[2] * blue));
      int newBlue = clamp((int) (blueWeights[0] * red + blueWeights[1] *
              green + blueWeights[2] * blue));

      return new int[]{newRed, newGreen, newBlue};
    });
  }

  /**
   * This method will apply kernel to images for when needing a full transformation.
   *
   * @param img       the image file that is being edited.
   * @param kernel    the matrix representing the kernel.
   * @return the modified image file.
   */
  protected CustomImage applyKernel(CustomImage img, float[][] kernel) {
    int width = img.getWidth();
    int height = img.getHeight();
    CustomImage transformedImg = new CustomImage(width, height);

    for (int x = 1; x < width - 1; x++) {
      for (int y = 1; y < height - 1; y++) {
        float[] newPixel = {0f, 0f, 0f};

        for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 1; j++) {
            int[] rgb = img.getPixel(x + i, y + j);
            float kernelValue = kernel[i + 1][j + 1];

            // Accumulate the weighted RGB values
            newPixel[0] += rgb[0] * kernelValue;
            newPixel[1] += rgb[1] * kernelValue;
            newPixel[2] += rgb[2] * kernelValue;
          }
        }

        // Set the new pixel values, clamped to the valid range
        transformedImg.setPixel(x, y, new int[]{
                clamp(Math.round(newPixel[0])), // Convert float to int
                clamp(Math.round(newPixel[1])),
                clamp(Math.round(newPixel[2]))
        });
      }
    }
    return transformedImg;
  }
}

