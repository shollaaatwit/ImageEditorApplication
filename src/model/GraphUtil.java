package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for generating and visualizing histograms from images.
 * Includes methods to create histograms for RGB channels, draw histogram images,
 * and find peak values in histograms.
 */
public class GraphUtil {

  /**
   * Generates a histogram as an int array given a custom image.
   *
   * @param customImage The image that is being turned into a histogram.
   * @return An array of int values representing the value.
   */
  public static Map<String, int[]> generateHistogram(CustomImage customImage) {
    int width = customImage.getWidth();
    int height = customImage.getHeight();
    int[] redHistogram = new int[256];
    int[] greenHistogram = new int[256];
    int[] blueHistogram = new int[256];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int[] rgb = customImage.getPixel(x, y);
        int red = rgb[0];
        int green = rgb[1];
        int blue = rgb[2];
        redHistogram[red]++;
        greenHistogram[green]++;
        blueHistogram[blue]++;
      }
    }

    Map<String, int[]> rgbHistograms = new HashMap<>();
    rgbHistograms.put("Red", redHistogram);
    rgbHistograms.put("Green", greenHistogram);
    rgbHistograms.put("Blue", blueHistogram);

    return rgbHistograms;
  }

  /**
   * Uses an array of int values to generate an image of a histogram.
   *
   * @param rgbHistograms The mapped out rgb int array values.
   * @return the histogram image.
   */
  public static BufferedImage createHistogramImage(Map<String, int[]> rgbHistograms) {
    int width = 256;
    int height = 256;
    BufferedImage histogramImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = histogramImage.createGraphics();
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, width, height);

    // Use the static drawHistogram method
    drawHistogram(g, rgbHistograms.get("Red"), Color.RED, width, height);
    drawHistogram(g, rgbHistograms.get("Green"), Color.GREEN, width, height);
    drawHistogram(g, rgbHistograms.get("Blue"), Color.BLUE, width, height);

    g.dispose();
    return histogramImage;
  }

  /**
   * Draws a histogram line plot for a specific color channel.
   *
   * @param g         The Graphics2D object used for drawing.
   * @param histogram The histogram data for the color channel.
   * @param color     The color used to draw the histogram.
   * @param width     The width of the histogram image.
   * @param height    The height of the histogram image.
   */
  private static void drawHistogram(Graphics2D g, int[] histogram,
                                    Color color, int width, int height) {
    g.setColor(color);
    int topPoint = 0;

    // Find the peak value in the histogram
    for (int point : histogram) {
      if (point > topPoint) {
        topPoint = point;
      }
    }

    // Draw the histogram line plot
    for (int i = 1; i < histogram.length; i++) {
      int previousHeight = (int) ((histogram[i - 1] / (double) topPoint) * height);
      int currentHeight = (int) ((histogram[i] / (double) topPoint) * height);
      g.drawLine(i - 1, height - previousHeight, i, height - currentHeight);
    }
  }

  /**
   * Finds the peak value of the histogram.
   *
   * @param histogram the int array histogram.
   * @return an int value of the peak.
   */
  public static int findPeakValue(int[] histogram) {
    int peakIndex = 0;
    for (int i = 1; i < histogram.length; i++) {
      if (histogram[i] > histogram[peakIndex]) {
        peakIndex = i;
      }
    }
    return peakIndex;
  }
}
