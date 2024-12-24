package model;

/**
 * Interface for additional transform values being implemented.
 * This interface defines advanced image transformation operations
 * such as level adjustment, color correction, image compression, and
 * split view application.
 */
public interface AdvancedTransform {

  /**
   * Adjusts the black, mid, and white level values of an image.
   * This operation modifies the contrast and brightness levels of an image
   * based on the specified black, mid, and white level values.
   *
   * @param image the image to be adjusted.
   * @param b     the black level value, defining the darkest points.
   * @param m     the mid-level value, defining the midpoint of brightness.
   * @param w     the white level value, defining the brightest points.
   * @return a new CustomImage object with adjusted levels.
   */
  CustomImage levelsAdjust(CustomImage image, int b, int m, int w);

  /**
   * Performs a color-correction operation on the image.
   * This aligns the histogram peak values, improving the overall color balance
   * and rendering a more natural appearance to the image.
   *
   * @param img the CustomImage to be color corrected.
   * @return a new CustomImage object with color correction applied.
   */
  CustomImage colorCorrect(CustomImage img);

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
  CustomImage compress(CustomImage img, int percentage);

  /**
   * Applies a specified operation to a portion of the image, creating a split-view effect.
   * The operation is performed starting from a given position and can include additional
   * parameters for custom transformations such as level adjustments.
   *
   * @param img                 the CustomImage to apply the split-view operation on.
   * @param operation           the name of the operation to perform (e.g., "levelsAdjust").
   * @param position            the position in the image (in percentage) apply the operation.
   * @param additionalArguments an array of additional arguments required for specified operation
   *                            (e.g., {b, m, w} for levels adjustment).
   * @return a new CustomImage object with the split-view operation applied.
   */
  CustomImage applySplitView(CustomImage img,
                             String operation,
                             int position,
                             int[] additionalArguments);
}
