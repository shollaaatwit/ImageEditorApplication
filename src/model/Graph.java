package model;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Interface for methods to graph a histogram image.
 */
public interface Graph {

  /**
   * Generates a histogram as an int array given a custom image.
   *
   * @param customImage The image that is being turned into a histogram.
   * @return An array of int values representing the value.
   */
  Map<String, int[]> generateHistogram(CustomImage customImage);

  /**
   * Uses an array of int values to generate an image of a histogram.
   *
   * @param rgbHistograms The mapped out rgb int array values.
   * @return the histogram image.
   */
  BufferedImage createHistogramImage(Map<String, int[]> rgbHistograms);

  /**
   * Finds the peak value of the histogram.
   *
   * @param histogram the int array histogram.
   * @return an int value of the peak.
   */
  int findPeakValue(int[] histogram);
}
