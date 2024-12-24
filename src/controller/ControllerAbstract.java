package controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import model.AdvancedImageTransformations;
import model.CustomImage;
import model.GraphUtil;

/**
 * Abstract class to handle image manipulation commands.
 * Provides common methods to process commands, load, save,
 * and apply various image transformations.
 */
public abstract class ControllerAbstract implements Command {
  protected ImageUtil imageUtil;
  protected Map<String, CustomImage> imageMap;

  /**
   * Constructs a ControllerAbstract with a map of images and an image utility.
   *
   * @param imageMap  A map to store images by name.
   * @param imageUtil Utility class for performing image manipulations.
   */
  public ControllerAbstract(Map<String, CustomImage> imageMap, ImageUtil imageUtil) {
    this.imageMap = imageMap;
    this.imageUtil = imageUtil;
  }

  /**
   * Processes a command and executes the corresponding image manipulation method.
   *
   * @param command      The command action to perform.
   * @param commandParts The arguments associated with the command.
   * @throws Exception if an error occurs during command processing.
   */
  public void processCommand(String command, String[] commandParts) throws Exception {
    String[] parts = commandParts;
    String action = command;

    switch (action) {
      case "load":
        loadImage(parts);
        break;
      case "load-ppm":
        loadPpmImage(parts);
        break;
      case "save":
        saveImage(parts);
        break;
      case "levels-adjust":
        handleLevelAdjust(parts);
        break;
      case "histogram":
        handleHistogram(parts);
        break;
      case "color-correct":
        handleColorCorrect(parts);
        break;
      case "compress":
        handleCompression(parts);
        break;
      case "split":
        handleSplitView(parts);
        break;
      case "grayscale":
        handleGrayscale(parts);
        break;
      case "value-component":
        handleValueComponent(parts);
        break;
      case "sepia":
        handleSepia(parts);
        break;
      case "brighten":
        handleBrighten(parts);
        break;
      case "horizontal-flip":
        handleHorizontalFlip(parts);
        break;
      case "vertical-flip":
        handleVerticalFlip(parts);
        break;
      case "blur":
        handleBlur(parts);
        break;
      case "sharpen":
        handleSharpen(parts);
        break;
      case "rgb-split":
        handleRgbSplit(parts);
        break;
      case "rgb-combine":
        handleRgbCombine(parts);
        break;
      case "red-component":
        handleVisualizeRed(parts);
        break;
      case "green-component":
        handleVisualizeGreen(parts);
        break;
      case "blue-component":
        handleVisualizeBlue(parts);
        break;
      case "intensity-component":
        handleVisualizeIntensity(parts);
        break;
      case "luma-component":
        handleVisualizeLuma(parts);
        break;
      case "script":
        handleScript(parts);
        break;
      default:
        System.out.println("Unknown command: " + action);
        break;
    }
  }

  /**
   * Loads an image from a specified file path and stores it in the image map.
   *
   * @param parts Contains the file path and the name to store the image under.
   * @throws Exception if an error occurs while loading the image.
   */
  private void loadImage(String[] parts) throws Exception {
    String filePath = parts[1];
    String imageName = parts[2];
    CustomImage image = imageUtil.loadImage(filePath);
    imageMap.put(imageName, image);
    System.out.println("Loaded image from " + filePath + " as " + imageName);
  }

  /**
   * Loads a PPM format image from a specified file path and stores it in the image map.
   *
   * @param parts Contains the PPM file path and the name to store the image under.
   * @throws Exception if an error occurs while loading the PPM image.
   */
  private void loadPpmImage(String[] parts) throws Exception {
    String ppmFilePath = parts[1];
    String ppmImageName = parts[2];
    try {
      CustomImage image = imageUtil.loadPPM(ppmFilePath);
      imageMap.put(ppmImageName, image);
      System.out.println("Loaded PPM image from " + ppmFilePath + " as " + ppmImageName);
    } catch (IOException e) {
      System.out.println("Error loading PPM file from " + ppmFilePath + ": " + e.getMessage());
    }
  }

  /**
   * Saves an image to a specified file path.
   *
   * @param parts Contains the image name, save path, and format.
   * @throws Exception if the image to save is not found or if an error occurs during saving.
   */
  private void saveImage(String[] parts) throws Exception {
    String savePath = parts[1];
    String imageToSave = parts[2];

    if (!imageMap.containsKey(imageToSave)) {
      System.out.println("Image not found: " + imageToSave);
      return;
    }

    CustomImage image = imageMap.get(imageToSave);

    try {
      if (savePath.toLowerCase().endsWith(".ppm")) {
        imageUtil.savePPM(image, savePath);
        System.out.println("Saved image " + imageToSave + " as PPM to " + savePath);
      } else {
        imageUtil.saveImage(image, savePath);
        System.out.println("Saved image " + imageToSave + " to " + savePath);
      }
    } catch (IOException e) {
      System.out.println("Error saving file " + savePath + ": " + e.getMessage());
    }
  }


  /**
   * Applies a grayscale transformation to an image.
   *
   * @param parts Contains the source image name and the output image name.
   */
  private void handleGrayscale(String[] parts) {
    String sourceImage = parts[1];
    String outputImageName = parts[2];
    CustomImage originalImage = imageMap.get(sourceImage);
    if (imageMap.containsKey(sourceImage)) {
      if (parts.length == 5) {
        handleSplitView(parts);
      }
      else if (parts.length == 4) {
        originalImage = imageMap.get(sourceImage);
        CustomImage originalCopy = originalImage.copy();
        AdvancedImageTransformations transformer = new AdvancedImageTransformations();
        CustomImage grayscale = transformer.applyWithMask(originalCopy, imageMap.get(parts[2]),
                "grayscale");
        outputImageName = parts[3];
        imageMap.put(outputImageName, grayscale);
        System.out.println("Applied grayscale to " + sourceImage
                + " and stored as " + outputImageName
                + " using mask ");
      } else {
        CustomImage grayscaleImage = originalImage.copy();
        CustomImage finalGrayscaleImage = imageUtil.applyGrayscale(grayscaleImage);
        imageMap.put(outputImageName, finalGrayscaleImage);
        System.out.println("Applied grayscale to "
                + sourceImage + " and stored as " + outputImageName);
      }
    } else {
      System.out.println("Image not found: " + sourceImage);
    }
  }

  /**
   * Creates a value component visualization of an image.
   *
   * @param parts Contains the source image name and the output image name.
   */
  private void handleValueComponent(String[] parts) {
    String sourceImage = parts[1];
    String outputImageName = parts[2];
    if (imageMap.containsKey(sourceImage)) {
      CustomImage originalImage = imageMap.get(sourceImage);
      CustomImage valueImageCopy = originalImage.copy();
      CustomImage valueImage = imageUtil.applyValueVisualization(valueImageCopy);
      imageMap.put(outputImageName, valueImage);
      System.out.println("Created value component image for "
              + sourceImage + " and stored as " + outputImageName);
    } else {
      System.out.println("Image not found: " + sourceImage);
    }
  }

  /**
   * Applies a sepia transformation to an image.
   *
   * @param parts Contains the source image name and the output image name.
   */
  private void handleSepia(String[] parts) {
    String sourceImage = parts[1];
    String outputImageName = parts[2];
    if (imageMap.containsKey(sourceImage)) {
      if (parts.length == 5) {
        handleSplitView(parts);
      }
      else if (parts.length == 4) {
        CustomImage originalImage = imageMap.get(sourceImage);
        CustomImage originalCopy = originalImage.copy();
        AdvancedImageTransformations transformer = new AdvancedImageTransformations();
        CustomImage sepiaImage = transformer.applyWithMask(originalCopy, imageMap.get(parts[2]),
                "sepia");
        outputImageName = parts[3];
        imageMap.put(outputImageName, sepiaImage);
        System.out.println("Applied sepia to " + sourceImage
                + " and stored as " + outputImageName
                + " using mask " + parts[2]);
      } else {
        CustomImage originalImage = imageMap.get(sourceImage);
        CustomImage sepiaImageCopy = originalImage.copy();
        CustomImage sepiaImage = imageUtil.applySepia(sepiaImageCopy);
        imageMap.put(outputImageName, sepiaImage);
        System.out.println("Applied sepia to "
                + sourceImage + " and stored as " + outputImageName);
      }
    } else {
      System.out.println("Image not found: " + sourceImage);
    }
  }

  /**
   * Brightens an image by a specified increment.
   *
   * @param parts Contains the increment value, source image name, and output image name.
   */
  private void handleBrighten(String[] parts) {
    int increment = Integer.parseInt(parts[1]);
    String sourceImage = parts[2];
    String outputImageName = parts[3];
    if (imageMap.containsKey(sourceImage)) {
      CustomImage originalImage = imageMap.get(sourceImage);
      CustomImage originalCopy = originalImage.copy();
      CustomImage brightenedImage = imageUtil.adjustBrightness(originalCopy, increment);
      imageMap.put(outputImageName, brightenedImage);
      System.out.println("Brightened " + sourceImage + " by "
              + increment + " and stored as " + outputImageName);
    } else {
      System.out.println("Image not found: " + sourceImage);
    }
  }

  /**
   * Applies a horizontal flip to an image.
   *
   * @param parts Contains the source image name and the output image name.
   */
  private void handleHorizontalFlip(String[] parts) {
    String sourceImage = parts[1];
    String outputImageName = parts[2];
    if (imageMap.containsKey(sourceImage)) {
      CustomImage originalImage = imageMap.get(sourceImage);
      CustomImage originalCopy = originalImage.copy();
      CustomImage flippedImage = imageUtil.flipHorizontal(originalCopy);
      imageMap.put(outputImageName, flippedImage);
      System.out.println("Applied horizontal flip to "
              + sourceImage + " and stored as " + outputImageName);
    } else {
      System.out.println("Image not found: " + sourceImage);
    }
  }

  /**
   * Applies a vertical flip to an image.
   *
   * @param parts Contains the source image name and the output image name.
   */
  private void handleVerticalFlip(String[] parts) {
    String sourceImage = parts[1];
    String outputImageName = parts[2];
    if (imageMap.containsKey(sourceImage)) {
      CustomImage originalImage = imageMap.get(sourceImage);
      CustomImage originalCopy = originalImage.copy();
      CustomImage flippedImage = imageUtil.flipVertical(originalCopy);
      imageMap.put(outputImageName, flippedImage);
      System.out.println("Applied vertical flip to "
              + sourceImage + " and stored as " + outputImageName);
    } else {
      System.out.println("Image not found: " + sourceImage);
    }
  }

  /**
   * Applies a blur effect to an image.
   *
   * @param parts Contains the source image name and the output image name.
   */
  private void handleBlur(String[] parts) throws IOException {
    String sourceImage = parts[1];
    String outputImageName = parts[2];
    if (imageMap.containsKey(sourceImage)) {
      if (parts.length == 5) {
        handleSplitView(parts);
      }
      else if (parts.length == 4) {
        CustomImage originalImage = imageMap.get(sourceImage);
        CustomImage originalCopy = originalImage.copy();
        AdvancedImageTransformations transformer = new AdvancedImageTransformations();
        CustomImage blurredImage = transformer.applyWithMask(originalCopy, imageMap.get(parts[2]),
                "blur");
        outputImageName = parts[3];
        imageMap.put(outputImageName, blurredImage);
        System.out.println("Applied blur to " + sourceImage
                + " and stored as " + outputImageName
                + " using mask " + parts[2]);
      }
      else {
        CustomImage originalImage = imageMap.get(sourceImage);
        CustomImage originalCopy = originalImage.copy();
        CustomImage blurredImage = imageUtil.blur(originalCopy);
        imageMap.put(outputImageName, blurredImage);
        System.out.println("Applied blur to " + sourceImage + " and stored as " + outputImageName);
      }
    } else {
      System.out.println("Image not found: " + sourceImage);
    }
  }

  /**
   * Applies a sharpen effect to an image.
   *
   * @param parts Contains the source image name and the output image name.
   */
  private void handleSharpen(String[] parts) {
    String sourceImage = parts[1];
    String outputImageName = parts[2];
    if (imageMap.containsKey(sourceImage)) {
      if (parts.length == 5) {
        handleSplitView(parts);
      }
      else if (parts.length == 4) {
        CustomImage originalImage = imageMap.get(sourceImage);
        CustomImage originalCopy = originalImage.copy();
        AdvancedImageTransformations transformer = new AdvancedImageTransformations();
        CustomImage sharpenedImage = transformer.applyWithMask(originalCopy, imageMap.get(parts[2]),
                "sharpen");
        outputImageName = parts[3];
        imageMap.put(outputImageName, sharpenedImage);
        System.out.println("Applied sharpen to " + sourceImage
                + " and stored as " + outputImageName
                + " using mask");
      } else {
        CustomImage originalImage = imageMap.get(sourceImage);
        CustomImage originalCopy = originalImage.copy();
        CustomImage sharpenedImage = imageUtil.sharpen(originalCopy);
        imageMap.put(outputImageName, sharpenedImage);
        System.out.println("Applied sharpen to " + sourceImage
                + " and stored as " + outputImageName);
      }

    } else {
      System.out.println("Image not found: " + sourceImage);
    }
  }

  /**
   * Splits an image into RGB components.
   *
   * @param parts Contains source name and the names to store the red, green, and blue channels.
   */
  private void handleRgbSplit(String[] parts) {
    String sourceImage = parts[1];
    String outputImageName = parts[2];
    String outputImageName1 = parts[3];
    String outputImageName2 = parts[4];
    if (imageMap.containsKey(sourceImage)) {
      CustomImage originalImage = imageMap.get(sourceImage);
      CustomImage originalCopy = originalImage.copy();
      CustomImage[] rgbSplitImages = imageUtil.splitRGB(originalCopy);
      imageMap.put(outputImageName, rgbSplitImages[0]);
      imageMap.put(outputImageName1, rgbSplitImages[1]);
      imageMap.put(outputImageName2, rgbSplitImages[2]);
      System.out.println("Applied RGB split to " + sourceImage
              + " and stored as " + outputImageName);
    } else {
      System.out.println("Image not found: " + sourceImage);
    }
  }

  /**
   * Combines three images (red, green, and blue channels) into a single RGB image.
   *
   * @param parts Contains names of the red, green blue images and name to store combined image.
   */
  private void handleRgbCombine(String[] parts) {
    String redImage = parts[1];
    String greenImage = parts[2];
    String blueImage = parts[3];
    String outputImageName = parts[4];
    if (imageMap.containsKey(redImage) && imageMap.containsKey(greenImage)
            && imageMap.containsKey(blueImage)) {
      CustomImage combinedImage = imageUtil.combineRGB(imageMap.get(redImage),
              imageMap.get(greenImage), imageMap.get(blueImage));
      imageMap.put(outputImageName, combinedImage);
      System.out.println("Combined RGB channels into " + outputImageName);
    } else {
      System.out.println("One or more images not found.");
    }
  }

  /**
   * Visualizes the red channel of an image.
   *
   * @param parts Contains the source image name and the output image name.
   */
  private void handleVisualizeRed(String[] parts) {
    String sourceImage = parts[1];
    String outputImageName = parts[2];
    if (parts.length == 4) {
      CustomImage originalImage = imageMap.get(sourceImage);
      CustomImage originalCopy = originalImage.copy();
      AdvancedImageTransformations transformer = new AdvancedImageTransformations();
      CustomImage redImage = transformer.applyWithMask(originalCopy, imageMap.get(parts[2]),
              "red-component");
      outputImageName = parts[3];
      imageMap.put(outputImageName, redImage);
      System.out.println("Visualized red channel for " + sourceImage
              + " and stored as " + outputImageName
              + " using mask ");
    }
    else if (imageMap.containsKey(sourceImage)) {
      CustomImage originalImage = imageMap.get(sourceImage);
      CustomImage originalCopy = originalImage.copy();
      CustomImage redVisualization = imageUtil.applyRedVisualization(originalCopy);
      imageMap.put(outputImageName, redVisualization);
      System.out.println("Visualized red channel for "
              + sourceImage + " and stored as " + outputImageName);
    }
    else {
      System.out.println("Image not found: " + sourceImage);
    }
  }

  /**
   * Visualizes the green channel of an image.
   *
   * @param parts Contains the source image name and the output image name.
   */
  private void handleVisualizeGreen(String[] parts) {
    String sourceImage = parts[1];
    String outputImageName = parts[2];
    if (parts.length == 4) {
      CustomImage originalImage = imageMap.get(sourceImage);
      CustomImage originalCopy = originalImage.copy();
      AdvancedImageTransformations transformer = new AdvancedImageTransformations();
      CustomImage greenImage = transformer.applyWithMask(originalCopy, imageMap.get(parts[2]),
              "green-component");
      outputImageName = parts[3];
      imageMap.put(outputImageName, greenImage);
      System.out.println("Visualized green channel for " + sourceImage
              + " and stored as " + outputImageName
              + " using mask ");
    }
    else if (imageMap.containsKey(sourceImage)) {
      CustomImage originalImage = imageMap.get(sourceImage);
      CustomImage originalCopy = originalImage.copy();
      CustomImage greenVisualization = imageUtil.applyGreenVisualization(originalCopy);
      imageMap.put(outputImageName, greenVisualization);
      System.out.println("Visualized green channel for "
              + sourceImage + " and stored as " + outputImageName);
    } else {
      System.out.println("Image not found: " + sourceImage);
    }
  }

  /**
   * Visualizes the blue channel of an image.
   *
   * @param parts Contains the source image name and the output image name.
   */
  private void handleVisualizeBlue(String[] parts) {
    String sourceImage = parts[1];
    String outputImageName = parts[2];
    if (parts.length == 4) {
      CustomImage originalImage = imageMap.get(sourceImage);
      CustomImage originalCopy = originalImage.copy();
      AdvancedImageTransformations transformer = new AdvancedImageTransformations();
      CustomImage blueImage = transformer.applyWithMask(originalCopy, imageMap.get(parts[2]),
              "blue-component");
      outputImageName = parts[3];
      imageMap.put(outputImageName, blueImage);
      System.out.println("Visualized blue channel for " + sourceImage
              + " and stored as " + outputImageName
              + " using mask ");
    }
    else if (imageMap.containsKey(sourceImage)) {
      CustomImage originalImage = imageMap.get(sourceImage);
      CustomImage originalCopy = originalImage.copy();
      CustomImage blueVisualization = imageUtil.applyBlueVisualization(originalCopy);
      imageMap.put(outputImageName, blueVisualization);
      System.out.println("Visualized blue channel for "
              + sourceImage + " and stored as " + outputImageName);
    } else {
      System.out.println("Image not found: " + sourceImage);
    }
  }

  /**
   * Visualizes the intensity of an image.
   *
   * @param parts Contains the source image name and the output image name.
   */
  private void handleVisualizeIntensity(String[] parts) {
    String sourceImage = parts[1];
    String outputImageName = parts[2];
    if (imageMap.containsKey(sourceImage)) {
      CustomImage originalImage = imageMap.get(sourceImage);
      CustomImage originalCopy = originalImage.copy();
      CustomImage intensityVisualization = imageUtil.applyIntensityVisualization(originalCopy);
      imageMap.put(outputImageName, intensityVisualization);
      System.out.println("Visualized intensity for "
              + sourceImage + " and stored as " + outputImageName);
    } else {
      System.out.println("Image not found: " + sourceImage);
    }
  }

  /**
   * Visualizes the luma component of an image.
   *
   * @param parts Contains the source image name and the output image name.
   */
  private void handleVisualizeLuma(String[] parts) {
    String sourceImage = parts[1];
    String outputImageName = parts[2];
    if (imageMap.containsKey(sourceImage)) {
      CustomImage originalImage = imageMap.get(sourceImage);
      CustomImage originalCopy = originalImage.copy();
      CustomImage lumaVisualization = imageUtil.applyLumaVisualization(originalCopy);
      imageMap.put(outputImageName, lumaVisualization);
      System.out.println("Visualized luma for "
              + sourceImage + " and stored as " + outputImageName);
    } else {
      System.out.println("Image not found: " + sourceImage);
    }
  }

  private void handleMaskOperation(CustomImage img, CustomImage mask, String transformation) {
    AdvancedImageTransformations transformer = new AdvancedImageTransformations();
    transformer.applyWithMask(img, mask, transformation);
  }

  /**
   * Generates a histogram for an image and stores it as an image.
   *
   * @param parts Contains the source image name and the output image name.
   */
  private void handleHistogram(String[] parts) {
    String sourceImage = parts[1];
    String outputImageName = parts[2];
    if (imageMap.containsKey(sourceImage)) {
      CustomImage originalImage = imageMap.get(sourceImage);
      CustomImage originalCopy = originalImage.copy();
      GraphUtil gUtil = new GraphUtil();
      Map<String, int[]> histograms = gUtil.generateHistogram(originalCopy);
      BufferedImage histogram = gUtil.createHistogramImage(histograms);
      imageMap.put(outputImageName, CustomImage.fromBufferedImage(histogram));
      System.out.println("Created histogram of "
              + sourceImage + " and stored as " + outputImageName);
    } else {
      System.out.println("Image not found: " + sourceImage);
    }
  }

  /**
   * Applies color correction to an image.
   *
   * @param parts Contains the source image name and the output image name.
   */
  private void handleColorCorrect(String[] parts) {
    String sourceImage = parts[1];
    String outputImageName = parts[2];
    if (imageMap.containsKey(sourceImage)) {
      CustomImage originalImage = imageMap.get(sourceImage);
      CustomImage originalCopy = originalImage.copy();
      CustomImage colorCorrectedImg = imageUtil.colorCorrect(originalCopy);
      imageMap.put(outputImageName, colorCorrectedImg);
      System.out.println("Color corrected "
              + sourceImage + " and stored as " + outputImageName);
    } else {
      System.out.println("Image not found: " + sourceImage);
    }
  }

  /**
   * Adjusts the levels of an image based on black, mid, and white points.
   *
   * @param parts Contains the black, mid, white values, source image name, and output image name.
   */

  private void handleLevelAdjust(String[] parts) {
    int b = Integer.parseInt(parts[1]);
    int m = Integer.parseInt(parts[2]);
    int w = Integer.parseInt(parts[3]);
    String sourceImage = parts[4];
    String outputImageName = parts[5];
    if (imageMap.containsKey(sourceImage)) {
      if (parts.length == 8) {
        handleSplitView(parts);
      } else {
        CustomImage originalImage = imageMap.get(sourceImage);
        CustomImage originalCopy = originalImage.copy();
        CustomImage levelAdjustedImg = imageUtil.levelsAdjust(originalCopy, b, m, w);
        imageMap.put(outputImageName, levelAdjustedImg);
        System.out.println("Applied level adjustment to "
                + sourceImage + " and stored as " + outputImageName);
      }

    } else {
      System.out.println("Image not found: " + sourceImage);
    }
  }

  /**
   * Compresses an image by a specified percentage.
   *
   * @param parts Contains the source image name, compression percentage, and output image name.
   */
  private void handleCompression(String[] parts) {
    if (parts.length != 4) {
      System.out.println("Invalid command. Usage: " +
              "compress <sourceImage> <percentage> <outputImage>");
      return;
    }
    String compressSource = parts[2];
    int compressPercentage;
    try {
      compressPercentage = Integer.parseInt(parts[1]);
    } catch (NumberFormatException e) {
      System.out.println("Invalid percentage value: " + parts[2]);
      return;
    }
    String compressOutput = parts[3];
    if (!imageMap.containsKey(compressSource)) {
      System.out.println("Image not found: " + compressSource);
      return;
    }
    CustomImage originalImage = imageMap.get(compressSource);
    CustomImage originalCopy = originalImage.copy();
    CustomImage compressedImage = imageUtil.compress(originalCopy, compressPercentage);
    imageMap.put(compressOutput, compressedImage);
    System.out.println("Compressed " + compressSource + " by " + compressPercentage +
            "% and stored as " + compressOutput);
  }

  /**
   * Applies a split view transformation to an image.
   *
   * @param parts Contains the operation, source image, split position, and output image name.
   */
  private void handleSplitView(String[] parts) {
    String operation = parts[0];
    String splitSource = parts[1];
    String splitOutput = parts[2];
    int position = 0;

    int[] additionalArguments = new int[0];

    if (operation.equals("levels-adjust")) {
      additionalArguments = new int[]{Integer.parseInt(parts[1]),
              Integer.parseInt(parts[2]), Integer.parseInt(parts[3])};
      splitSource = parts[4];
      splitOutput = parts[5];
      position = Integer.parseInt(parts[7]);
    } else {
      position = Integer.parseInt(parts[4]);
    }

    if (imageMap.containsKey(splitSource)) {
      CustomImage originalImage = imageMap.get(splitSource);
      CustomImage originalCopy = originalImage.copy();
      CustomImage splitImage = imageUtil.applySplitView(
              originalCopy, operation, position, additionalArguments);
      imageMap.put(splitOutput, splitImage);
      System.out.println("Applied split view on " + splitSource +
              " with operation " + operation + " at position " + position +
              " and stored as " + splitOutput);
    } else {
      System.out.println("Image not found: " + splitSource);
    }
  }

  /**
   * Executes a script file with a sequence of commands.
   *
   * @param parts Contains the path of the script file.
   * @throws Exception if an error occurs while reading the script file.
   */
  private void handleScript(String[] parts) throws Exception {
    String scriptPath = parts[1];
    executeScript(scriptPath, imageUtil, imageMap);
  }

  /**
   * Executes the contents of a given script file, processing commands line-by-line.
   *
   * @param scriptPath The file path of the script to be executed.
   * @param imageUtil  Utility instance for image manipulations.
   * @param imageMap   Map storing images by name.
   * @throws Exception if an error occurs while executing the script.
   */
  public abstract void executeScript(String scriptPath, ImageUtil imageUtil,
                                     Map<String, CustomImage> imageMap) throws Exception;

}