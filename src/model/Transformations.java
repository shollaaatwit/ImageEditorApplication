package model;

/**
 * Interface representing a transformation operation on RGB values.
 * This interface defines a method that can be implemented to apply specific transformations
 * to an array holding RGB values of an image.
 */
public interface Transformations {

  /**
   * Transforms the RGB values of a pixel.
   *
   * @param rgb an array of integers representing the RGB values of a pixel.
   *            Each index in the array corresponds to a color component:
   *            index 0 for red, index 1 for green, and index 2 for blue.
   * @return an array of integers representing the transformed RGB values of the pixel.
   */
  int[] transform(int[] rgb);

}
