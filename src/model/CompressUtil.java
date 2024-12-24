package model;

import java.util.Arrays;

/**
 * Compress util class for compression methods.
 */
public class CompressUtil {

  /**
   * Compress a single channel using Haar wavelet transform.
   */
  public static int[][] compressChannel(int[][] channel, int percentage) {
    int size = nextPowerOfTwo(Math.max(channel.length, channel[0].length));
    float[][] data = padArray(channel, size);
    data = transform(data, size);
    applyDynamicThreshold(data, percentage);
    data = invert(data, size);
    return unpadArray(data, channel.length, channel[0].length);
  }

  /**
   * Find next power of 2 for even array lengths.
   */
  private static int nextPowerOfTwo(int n) {
    int power = 1;
    while (power < n) {
      power *= 2;
    }
    return power;
  }

  /**
   * Pad array to nearest power of 2 size.
   */
  private static float[][] padArray(int[][] array, int size) {
    float[][] padded = new float[size][size];
    for (int i = 0; i < array.length; i++) {
      for (int j = 0; j < array[0].length; j++) {
        padded[i][j] = array[i][j];
      }
    }
    return padded;
  }

  /**
   * Remove padding and convert back to int array.
   */
  private static int[][] unpadArray(float[][] padded, int height, int width) {
    int[][] result = new int[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        result[i][j] = clamp(Math.round(padded[i][j]));
      }
    }
    return result;
  }

  private static void applyDynamicThreshold(float[][] data, int percentage) {
    float[] allValues = new float[data.length * data[0].length];
    float totalMagnitude = 0;
    int index = 0;
    for (float[] row : data) {
      for (float val : row) {
        allValues[index] = Math.abs(val);
        totalMagnitude += allValues[index];
        index++;
      }
    }
    float thresholdMagnitude = totalMagnitude * (percentage / 100.0f);
    float runningSum = 0;
    Arrays.sort(allValues);
    float threshold = 0;
    for (int i = 0; i < allValues.length; i++) {
      runningSum += allValues[i];
      if (runningSum >= thresholdMagnitude) {
        threshold = allValues[i];
        break;
      }
    }
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[0].length; j++) {
        if (Math.abs(data[i][j]) <= threshold) {
          data[i][j] = 0;
        }
      }
    }
  }

  private static float[][] transform(float[][] data, int size) {
    int m = size;
    while (m > 1) {
      for (int i = 0; i < m; i++) {
        transformRow(data[i], m);
      }
      for (int j = 0; j < m; j++) {
        float[] col = new float[m];
        for (int i = 0; i < m; i++) {
          col[i] = data[i][j];
        }
        transformRow(col, m);
        for (int i = 0; i < m; i++) {
          data[i][j] = col[i];
        }
      }
      m = m / 2;
    }
    return data;
  }

  private static void transformRow(float[] row, int length) {
    float[] temp = new float[length];
    for (int i = 0; i < length / 2; i++) {
      float avg = (row[2 * i] + row[2 * i + 1]) / (float) Math.sqrt(2);
      float diff = (row[2 * i] - row[2 * i + 1]) / (float) Math.sqrt(2);
      temp[i] = avg;
      temp[i + length / 2] = diff;
    }
    System.arraycopy(temp, 0, row, 0, length);
  }

  private static float[][] invert(float[][] data, int size) {
    int m = 2;
    while (m <= size) {
      for (int j = 0; j < m; j++) {
        float[] col = new float[m];
        for (int i = 0; i < m; i++) {
          col[i] = data[i][j];
        }
        invertRow(col, m);
        for (int i = 0; i < m; i++) {
          data[i][j] = col[i];
        }
      }
      for (int i = 0; i < m; i++) {
        invertRow(data[i], m);
      }
      m = m * 2;
    }
    return data;
  }

  private static void invertRow(float[] row, int length) {
    float[] temp = new float[length];
    for (int i = 0; i < length / 2; i++) {
      float avg = row[i];
      float diff = row[i + length / 2];
      temp[2 * i] = (avg + diff) / (float) Math.sqrt(2);
      temp[2 * i + 1] = (avg - diff) / (float) Math.sqrt(2);
    }
    System.arraycopy(temp, 0, row, 0, length);
  }

  private static int clamp(int value) {
    return Math.max(0, Math.min(255, value));
  }
}
