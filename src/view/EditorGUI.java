package view;

/**
 * Interface for the GUI image editor methods.
 */
public interface EditorGUI {

  /**
   * Loads an image into the GUI.
   */
  void loadImage();

  /**
   * Saves the current image in the GUI.
   */
  void saveImage();

  /**
   * Updates the constant histogram in the GUI.
   */
  void updateHistogram();

  /**
   * Recognizes a color visualization in the gui.
   *
   * @param color color to visualize.
   */
  void applyColorVisualization(String color);

  /**
   * Applies a downscale effect to an image in the GUI.
   *
   * @param newWidth  the new width of the downscaled image.
   * @param newHeight the new height of the downscaled image.
   */
  void applyDownscale(int newWidth, int newHeight);

  /**
   * Loads a mask to what section will be edited in the image gui.
   */
  void loadMask();

  /**
   * Resets image back to original version.
   */
  void resetToOriginal();

  /**
   * Applies a certain transformation to the image based on button press.
   *
   * @param transformation transformation specified to image.
   */
  void applyTransformation(String transformation);

  /**
   * Updates the display in the GUI for the user.
   */
  void updateImageDisplay();
}
