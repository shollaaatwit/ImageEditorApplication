package model;

/**
 * Interface for Transform methods that will apply transformative,
 * functions to CustomImage files.
 */
public interface Transform {

  /**
   * Apply a grayscale modification to the image.
   *
   * @param img Image file that is being edited.
   * @return a grayscale image.
   */
  CustomImage applyGrayscale(CustomImage img);

  /**
   * Apply a sepia modification to the image.
   *
   * @param img Image file that is being edited.
   * @return a sepia-toned image.
   */
  CustomImage applySepia(CustomImage img);

  /**
   * Apply a brighten or dimmer modification to the image.
   *
   * @param img       Image file that is being edited.
   * @param increment increment in-which the file's luminosity will be affected.
   * @return a brighter or dimmer image.
   */
  CustomImage brighten(CustomImage img, int increment);

  /**
   * Apply a horizontal flip modification to the image.
   *
   * @param img Image file that is being edited.
   * @return a horizontally flipped image.
   */
  CustomImage flipHorizontal(CustomImage img);

  /**
   * Apply a vertical flip modification to the image.
   *
   * @param img Image file that is being edited.
   * @return a vertically flipped image.
   */
  CustomImage flipVertical(CustomImage img);

  /**
   * Apply a blur modification to the image.
   *
   * @param img Image file that is being edited.
   * @return a blurred image.
   */
  CustomImage blur(CustomImage img);

  /**
   * Apply a sharpen modification to the image.
   *
   * @param img Image file that is being edited.
   * @return a sharpened image.
   */
  CustomImage sharpen(CustomImage img);

  /**
   * Returns the red component of an image.
   *
   * @param img Image file that is being edited.
   * @return a red image.
   */
  CustomImage visualizeRed(CustomImage img);

  /**
   * Returns the green component of an image.
   *
   * @param img Image file that is being edited.
   * @return a green image.
   */
  CustomImage visualizeGreen(CustomImage img);

  /**
   * Returns the blue component of an image.
   *
   * @param img Image file that is being edited.
   * @return a blue image.
   */
  CustomImage visualizeBlue(CustomImage img);

  /**
   * Returns the value component of an image.
   *
   * @param img Image file that is being edited.
   * @return an edited image.
   */
  CustomImage visualizeValue(CustomImage img);

  /**
   * Returns the intensity component of an image.
   *
   * @param img Image file that is being edited.
   * @return an intensified image.
   */
  CustomImage visualizeIntensity(CustomImage img);

  /**
   * Returns the luma component of an image.
   *
   * @param img Image file that is being edited.
   * @return an image with increased luma.
   */
  CustomImage visualizeLuma(CustomImage img);

  /**
   * Apply a brighten or dimmer modification to the image.
   *
   * @param img       Image file that is being edited.
   * @param increment increment in-which the file's luminosity will be affected.
   * @return a brighter or dimmer image.
   */
  CustomImage adjustBrightness(CustomImage img, int increment);

  /**
   * Splits an image into three different components.
   *
   * @param img Image file that is being edited.
   * @return one image file for each red, green, blue components.
   */
  CustomImage[] splitRGB(CustomImage img);

  /**
   * Returns the combined image component.
   *
   * @param redImage   red image file.
   * @param greenImage green image file.
   * @param blueImage  blue image file.
   * @return an image with all three components combined.
   */
  CustomImage combineRGB(CustomImage redImage, CustomImage greenImage,
                         CustomImage blueImage);

}
