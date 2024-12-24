package model;

import java.awt.image.BufferedImage;


/**
 * CustomImage class represents an image with pixels, width, and height,
 * supporting basic pixel manipulation and PPM file operations.
 */
public class CustomImage implements Image {
  private int[][][] pixels;
  private int width;
  private int height;

  /**
   * Constructor for a CustomImage.
   *
   * @param width  width of the image as an int value.
   * @param height height of the image as an int value.
   */
  public CustomImage(int width, int height) {
    this.width = width;
    this.height = height;
    this.pixels = new int[height][width][3];
  }

  /**
   * Getter method for width of an image.
   *
   * @return the width int value.
   */
  public int getWidth() {
    return width;
  }

  /**
   * Getter method for height of an image.
   *
   * @return the height int value.
   */
  public int getHeight() {
    return height;
  }

  /**
   * Gets the value of a pixel given a certain coordinate.
   *
   * @param x the X coordinate int value of an index,
   *          in an array that represents the image.
   * @param y the Y coordinate int value of an index,
   *          in an array that represents the image.
   * @return the pixel at the given indices.
   */
  public int[] getPixel(int x, int y) {
    if (x < 0 || x >= width || y < 0 || y >= height) {
      throw new IndexOutOfBoundsException("Pixel coordinates are out of bounds");
    }
    return pixels[y][x];
  }


  /**
   * Sets the pixel at an index to a certain value rgb.
   *
   * @param x   the X coordinate int value of an index,
   *            in an array that represents the image.
   * @param y   the Y coordinate int value of an index,
   *            in an array that represents the image.
   * @param rgb the rgb value that is being changed
   *            at the index.
   */
  public void setPixel(int x, int y, int[] rgb) {
    if (x < 0 || x >= width || y < 0 || y >= height) {
      throw new IndexOutOfBoundsException("Pixel coordinates are out of bounds");
    }
    this.pixels[y][x] = rgb;
  }

  /**
   * Sets the pixel at an index to a certain value rgb.
   *
   * @param x     the X coordinate int value of an index,
   *              in an array that represents the image.
   * @param y     the Y coordinate int value of an index,
   *              in an array that represents the image.
   * @param red   The red value in rgb.
   * @param green The green value in rgb.
   * @param blue  The blue value in rgb.
   */
  public void setPixel(int x, int y, int red, int green, int blue) {
    if (x < 0 || x >= width || y < 0 || y >= height) {
      throw new IndexOutOfBoundsException("Pixel coordinates are out of bounds");
    }
    this.pixels[y][x][0] = red;
    this.pixels[y][x][1] = green;
    this.pixels[y][x][2] = blue;
  }

  /**
   * Sets a specific color channel of the image.
   *
   * @param channelIndex The index of the color channel to set.
   * @param newChannel   A 2D array representing the new color channel data.
   */
  public void setColorChannel(int channelIndex, int[][] newChannel) {
    int height = this.getHeight();
    int width = this.getWidth();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        this.pixels[y][x][channelIndex] = newChannel[y][x];
      }
    }
  }

  /**
   * Retrieves a specific color channel from the image.
   *
   * @param rgbChannel The index of the color channel to retrieve.
   * @return A 2D array representing the specified color channel.
   */
  public int[][] getColorChannel(int rgbChannel) {
    int height = this.getHeight();
    int width = this.getWidth();
    int[][] channel = new int[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        channel[i][j] = this.pixels[i][j][rgbChannel];
      }
    }

    return channel;
  }

  /**
   * Creates a CustomImage instance from a BufferedImage.
   *
   * @param bufferedImage The BufferedImage to convert.
   * @return A CustomImage instance representing the BufferedImage.
   */
  public static CustomImage fromBufferedImage(BufferedImage bufferedImage) {
    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();
    CustomImage customImage = new CustomImage(width, height);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
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
   * Creates a deep copy of the current CustomImage.
   *
   * @return A new CustomImage instance with identical pixel data.
   */
  public CustomImage copy() {
    CustomImage copy = new CustomImage(this.width, this.height);
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int[] originalPixel = this.getPixel(x, y);
        int[] clonedPixel = originalPixel.clone();
        copy.setPixel(x, y, clonedPixel);
      }
    }
    return copy;
  }

  /**
   * Converts the current CustomImage to a BufferedImage.
   *
   * @return A BufferedImage instance representing the current CustomImage.
   */
  public BufferedImage toBufferedImage() {
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int[] rgb = pixels[y][x];
        int color = (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
        image.setRGB(x, y, color);
      }
    }
    return image;
  }
}
