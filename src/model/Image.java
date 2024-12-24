package model;

/**
 * Interface representing a generic image.
 * Defines methods to access and manipulate the properties and pixel data of an image.
 */
public interface Image {

  /**
   * Retrieves the width of the image in pixels.
   *
   * @return the width of the image
   */
  int getWidth();

  /**
   * Retrieves the height of the image in pixels.
   *
   * @return the height of the image
   */
  int getHeight();

  /**
   * Gets the RGB values of a specific pixel at the specified (x, y) coordinate.
   *
   * @param x the x-coordinate of the pixel
   * @param y the y-coordinate of the pixel
   * @return an array of three integers representing the RGB values of the pixel
   * @throws IllegalArgumentException if the (x, y) coordinates are out of bounds
   */
  int[] getPixel(int x, int y);

  /**
   * Sets the RGB values of a specific pixel at the specified (x, y) coordinate.
   *
   * @param x   the x-coordinate of the pixel
   * @param y   the y-coordinate of the pixel
   * @param rgb an array of three integers representing the new RGB values for the pixel
   * @throws IllegalArgumentException if the (x, y) coordinates are out of bounds
   *                                  or if the RGB array is invalid
   */
  void setPixel(int x, int y, int[] rgb);

  /**
   * Creates a deep copy of the image.
   *
   * @return a new {@code Image} instance that is an exact copy of the current image
   */
  Image copy();
}
