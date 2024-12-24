package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.ImageIcon;

import controller.ImageUtil;
import model.AdvancedImageTransformations;
import model.CustomImage;
import model.GraphUtil;
import model.ImageTransformations;

/**
 * A graphical user interface for an image editor application that supports
 * various image transformations, color visualizations, and editing features.
 * This class uses Java Swing to implement the GUI components.
 */
public class ImageEditorGUI extends JFrame implements EditorGUI {
  private JLabel imageLabel;
  private JPanel histogramPanel;
  private CustomImage currentImage;
  private CustomImage transformedImage;
  private CustomImage maskImage;
  private ImageTransformations imageTransformations;
  private AdvancedImageTransformations imageTransformations1;
  private boolean splitViewEnabled = false;
  private JSlider splitPercentageSlider;



  /**
   * Constructs the ImageEditorGUI and initializes all components.
   * Sets up the layout, panels, and controls for the GUI.
   */
  public ImageEditorGUI() {

    imageTransformations = new ImageTransformations();
    imageTransformations1 = new AdvancedImageTransformations();

    setTitle("Image Processor");
    setSize(1000, 800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout(10, 10));

    // Title
    JLabel titleLabel = new JLabel("Image Processor", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
    add(titleLabel, BorderLayout.NORTH);

    // Image Display Panel
    imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(JLabel.CENTER);
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);
    add(imageScrollPane, BorderLayout.CENTER);

    //Slider Panel
    JSlider scaleSlider = new JSlider(10, 100, 100);
    scaleSlider.setMajorTickSpacing(10);
    scaleSlider.setPaintTicks(true);
    scaleSlider.setPaintLabels(true);


    // Controls Panel
    JPanel controlsPanel = createControlsPanel();
    JScrollPane controlsScrollPane = new JScrollPane(controlsPanel);
    controlsScrollPane.setHorizontalScrollBarPolicy(
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    controlsScrollPane.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    add(controlsScrollPane, BorderLayout.EAST);

    JSlider compressSlider = new JSlider(0, 100, 0);
    compressSlider.setMajorTickSpacing(25);
    compressSlider.setPaintTicks(true);
    compressSlider.setPaintLabels(true);
    // Histogram Panel
    histogramPanel = new JPanel(new GridLayout(1, 3, 5, 5));
    histogramPanel.setPreferredSize(new Dimension(800, 200));
    add(histogramPanel, BorderLayout.SOUTH);

    setVisible(true);
  }

  /**
   * Creates and initializes the controls panel containing buttons,
   * sliders, and other interactive components for transformations.
   *
   * @return a JPanel with all controls configured
   */
  private JPanel createControlsPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(0, 1, 5, 5));


    JComboBox<String> transformationDropdown = new JComboBox<>( new String[]{
        "blue-component",
        "blur",
        "grayscale",
        "green-component",
        "red-component",
        "sepia",
        "sharpen"
    });

    // Basic Controls
    JButton importButton = new JButton("Import File");
    JButton saveButton = new JButton("Save File");
    JButton resetButton = new JButton("Reset to Original");

    // Transformation Buttons
    JButton verticalFlipButton = new JButton("Vertical-Flip");
    JButton horizontalFlipButton = new JButton("Horizontal-Flip");
    JButton blurButton = new JButton("Blur");
    JButton sharpenButton = new JButton("Sharpen");
    JButton sepiaButton = new JButton("Sepia");
    JButton grayscaleButton = new JButton("Greyscale");
    JButton colorCorrectButton = new JButton("Color Correct");

    // Color Visualizations
    JButton redButton = new JButton("Red-Component");
    JButton greenButton = new JButton("Green-Component");
    JButton blueButton = new JButton("Blue-Component");
    JButton valueButton = new JButton("Value");
    JButton intensityButton = new JButton("Intensity");
    JButton lumaButton = new JButton("Luma");

    // Advanced Features
    JCheckBox splitViewToggle = new JCheckBox("Split View");
    JLabel splitPercentageLabel = new JLabel("Split Percentage:");
    splitPercentageSlider = new JSlider(0, 100, 50);
    splitPercentageSlider.setMajorTickSpacing(25);
    splitPercentageSlider.setPaintTicks(true);
    splitPercentageSlider.setPaintLabels(true);
    JButton loadMaskButton = new JButton("Load Mask");
    JButton applyMaskButton = new JButton("Apply Mask Transformation");
    JButton applyDownscaleButton = new JButton("Apply Downscale");

    JTextField widthField = new JTextField();
    JTextField heightField = new JTextField();

    JSlider blackLevelSlider = new JSlider(0, 255, 0);
    JSlider midLevelSlider = new JSlider(0, 255, 128);
    JSlider whiteLevelSlider = new JSlider(0, 255, 255);

    JTextField blackLevelField = new JTextField(
            String.valueOf(blackLevelSlider.getValue()), 5);
    JTextField midLevelField = new JTextField(
            String.valueOf(midLevelSlider.getValue()), 5);
    JTextField whiteLevelField = new JTextField(
            String.valueOf(whiteLevelSlider.getValue()), 5);

    blackLevelSlider.setMajorTickSpacing(50);
    blackLevelSlider.setPaintTicks(true);
    blackLevelSlider.setPaintLabels(true);

    midLevelSlider.setMajorTickSpacing(50);
    midLevelSlider.setPaintTicks(true);
    midLevelSlider.setPaintLabels(true);

    whiteLevelSlider.setMajorTickSpacing(50);
    whiteLevelSlider.setPaintTicks(true);
    whiteLevelSlider.setPaintLabels(true);

    blackLevelSlider.addChangeListener(e -> blackLevelField.setText(
            String.valueOf(blackLevelSlider.getValue())));
    midLevelSlider.addChangeListener(e -> midLevelField.setText(
            String.valueOf(midLevelSlider.getValue())));
    whiteLevelSlider.addChangeListener(e -> whiteLevelField.setText(
            String.valueOf(whiteLevelSlider.getValue())));

    blackLevelField.addActionListener(e -> {
      try {
        int value = Integer.parseInt(blackLevelField.getText());
        if (value >= 0 && value <= 255) {
          blackLevelSlider.setValue(value);
        } else {
          throw new NumberFormatException();
        }
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null,
                "Invalid input for Black Level. Enter a number between 0 and 255.",
                "Error", JOptionPane.ERROR_MESSAGE);
      }
    });

    midLevelField.addActionListener(e -> {
      try {
        int value = Integer.parseInt(midLevelField.getText());
        if (value >= 0 && value <= 255) {
          midLevelSlider.setValue(value);
        } else {
          throw new NumberFormatException();
        }
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null,
                "Invalid input for Mid Level. Enter a number between 0 and 255.",
                "Error", JOptionPane.ERROR_MESSAGE);
      }
    });

    whiteLevelField.addActionListener(e -> {
      try {
        int value = Integer.parseInt(whiteLevelField.getText());
        if (value >= 0 && value <= 255) {
          whiteLevelSlider.setValue(value);
        } else {
          throw new NumberFormatException();
        }
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null,
                "Invalid input for White Level. "
                        + "Enter a number between 0 and 255.",
                "Error", JOptionPane.ERROR_MESSAGE);
      }
    });

    panel.add(new JLabel("Adjust Black Level:"));
    panel.add(blackLevelSlider);
    panel.add(blackLevelField);

    panel.add(new JLabel("Adjust Mid Level:"));
    panel.add(midLevelSlider);
    panel.add(midLevelField);

    panel.add(new JLabel("Adjust White Level:"));
    panel.add(whiteLevelSlider);
    panel.add(whiteLevelField);


    JButton applyLevelsButton = new JButton("Apply Levels");
    applyLevelsButton.addActionListener(e -> {
      if (currentImage == null) {
        JOptionPane.showMessageDialog(null, "No image loaded.",
                "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }

      int black = blackLevelSlider.getValue();
      int mid = midLevelSlider.getValue();
      int white = whiteLevelSlider.getValue();

      if (black >= mid || mid >= white) {
        JOptionPane.showMessageDialog(null,
                "Invalid levels configuration. "
                        + "Ensure: Black < Mid < White.",
                "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }

      try {
        transformedImage = imageTransformations1.levelsAdjust(
                currentImage.copy(), black, mid, white);
        updateHistogram(transformedImage);
        updateImageDisplay();
        JOptionPane.showMessageDialog(null,
                "Levels adjusted successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
      } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null,
                "Error adjusting levels: "
                        + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
      }
    });


    panel.add(applyLevelsButton);

    importButton.addActionListener(e -> loadImage());
    saveButton.addActionListener(e -> saveImage());
    resetButton.addActionListener(e -> resetToOriginal());
    verticalFlipButton.addActionListener(e -> applyTransformation("flipVertical"));
    horizontalFlipButton.addActionListener(e -> applyTransformation("flipHorizontal"));
    blurButton.addActionListener(e -> applyTransformation("blur"));
    sharpenButton.addActionListener(e -> applyTransformation("sharpen"));
    sepiaButton.addActionListener(e -> applyTransformation("sepia"));
    grayscaleButton.addActionListener(e -> applyTransformation("grayscale"));
    colorCorrectButton.addActionListener(e -> applyTransformation("color-correct"));
    redButton.addActionListener(e -> applyColorVisualization("red"));
    greenButton.addActionListener(e -> applyColorVisualization("green"));
    blueButton.addActionListener(e -> applyColorVisualization("blue"));
    valueButton.addActionListener(e -> applyTransformation("value"));
    intensityButton.addActionListener(e -> applyTransformation("intensity"));
    lumaButton.addActionListener(e -> applyTransformation("luma"));


    JSlider compressLevelSlider = new JSlider(0, 100, 0);
    compressLevelSlider.setMajorTickSpacing(25);
    compressLevelSlider.setPaintTicks(true);
    compressLevelSlider.setPaintLabels(true);
    JButton applyCompressButton = new JButton("Apply Compression");
    applyCompressButton.addActionListener(e -> {
      int percentage = compressLevelSlider.getValue();
      if (currentImage == null) {
        JOptionPane.showMessageDialog(this,
                "No image loaded.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        return;
      }


      if (percentage < 0 || percentage > 100) {
        JOptionPane.showMessageDialog(this,
                "Invalid compression",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        return;
      }

      try {
        transformedImage = imageTransformations1.compress(
                currentImage.copy(),
                percentage
        );
        updateHistogram(transformedImage);
        updateImageDisplay();
        JOptionPane.showMessageDialog(this,
                "Image compressed successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
      } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this,
                "Error compressing levels: "
                        + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
      }
    });
    panel.add(new JLabel("Compression level:"));
    panel.add(compressLevelSlider);
    panel.add(applyCompressButton);

    splitViewToggle.addActionListener(e -> {
      splitViewEnabled = splitViewToggle.isSelected();
      updateImageDisplay();
    });
    splitPercentageSlider.addChangeListener(e -> {
      if (splitViewEnabled) {
        updateImageDisplay();
      }
    });

    loadMaskButton.addActionListener(e -> loadMask());
    applyDownscaleButton.addActionListener(e -> {
      if (currentImage == null) {
        JOptionPane.showMessageDialog(this, "No image loaded.",
                "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }
      try {
        int newWidth = Integer.parseInt(widthField.getText());
        int newHeight = Integer.parseInt(heightField.getText());
        applyDownscale(newWidth, newHeight);
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this,
                "Invalid input. Please enter valid integers for width and height.",
                "Error", JOptionPane.ERROR_MESSAGE);
      }
    });


    applyMaskButton.addActionListener(e -> {
      if (currentImage == null) {
        JOptionPane.showMessageDialog(this,
                "No source image loaded.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        return;
      }

      if (maskImage == null) {
        JOptionPane.showMessageDialog(this,
                "No mask image loaded. Please load a mask first.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        return;
      }

      String selectedTransformation = (String) transformationDropdown.getSelectedItem();
      if (selectedTransformation == null) {
        JOptionPane.showMessageDialog(this,
                "No transformation selected. Please select a transformation.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        return;
      }

      try {
        transformedImage = imageTransformations.applyWithMask(
                currentImage, maskImage, selectedTransformation);
        updateHistogram(transformedImage);
        updateImageDisplay();
        JOptionPane.showMessageDialog(this,
                "Mask transformation applied successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
      } catch (IllegalArgumentException ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
      } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this,
                "An unexpected error occurred: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
      }
    });


    // Components in Panel
    panel.add(importButton);
    panel.add(saveButton);
    panel.add(resetButton);
    panel.add(verticalFlipButton);
    panel.add(horizontalFlipButton);
    panel.add(blurButton);
    panel.add(sharpenButton);
    panel.add(sepiaButton);
    panel.add(grayscaleButton);
    panel.add(colorCorrectButton);
    panel.add(redButton);
    panel.add(greenButton);
    panel.add(blueButton);
    panel.add(valueButton);
    panel.add(intensityButton);
    panel.add(lumaButton);
    panel.add(splitViewToggle);
    panel.add(splitPercentageLabel);
    panel.add(splitPercentageSlider);
    panel.add(loadMaskButton);
    panel.add(applyMaskButton);
    panel.add(new JLabel("Mask Transformations:"));
    panel.add(transformationDropdown);
    panel.add(new JLabel("New Width:"));
    panel.add(widthField);
    panel.add(new JLabel("New Height:"));
    panel.add(heightField);
    panel.add(applyDownscaleButton);


    return panel;
  }

  /**
   * Opens a file chooser to load an image from the filesystem.
   * The loaded image is displayed in the main image area of the GUI.
   */
  @Override
  public void loadImage() {
    if (currentImage != null) {
      int response = JOptionPane.showConfirmDialog(this,
              "The current image is unsaved. Do you want to continue?",
              "Unsaved Changes", JOptionPane.YES_NO_OPTION);
      if (response != JOptionPane.YES_OPTION) {
        return;
      }
    }

    JFileChooser fileChooser = new JFileChooser();
    int returnValue = fileChooser.showOpenDialog(this);

    if (returnValue == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      try {
        ImageUtil imageUtil = new ImageUtil();
        BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
        String firstLine = reader.readLine();
        reader.close();
        if ("P3".equals(firstLine)) {
          currentImage = imageUtil.loadPPM(selectedFile.getAbsolutePath());
        } else {
          currentImage = imageUtil.loadImage(selectedFile.getAbsolutePath());
        }
        transformedImage = null;
        updateImageDisplay();
        updateHistogram();
        System.out.println("Image loaded successfully.");
      } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this,
                "Error loading image: "
                        + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  /**
   * Opens a file chooser to save the currently loaded or transformed image
   * to the filesystem in a supported format.
   */
  @Override
  public void saveImage() {
    if (currentImage == null) {
      JOptionPane.showMessageDialog(this,
              "No image loaded to save.",
              "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    JFileChooser fileChooser = new JFileChooser();
    int returnValue = fileChooser.showSaveDialog(this);

    if (returnValue == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      try {
        ImageUtil imageUtil = new ImageUtil();
        String filePath = selectedFile.getAbsolutePath();

        if (!filePath.endsWith(".png") && !filePath.endsWith(".jpg")
                && !filePath.endsWith(".ppm")) {
          filePath += ".png";
        }

        CustomImage imageToSave = (transformedImage != null) ? transformedImage : currentImage;
        imageUtil.saveImage(imageToSave, filePath);

        JOptionPane.showMessageDialog(this,
                "Image saved successfully!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
      } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this,
                "Error saving image: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  /**
   * Updates the histogram display based on the current image loaded.
   * This method retrieves the histogram data for the `currentImage` and
   * displays it in the histogram panel. If no image is loaded, the histogram
   * will not be updated.
   */
  @Override
  public void updateHistogram() {
    if (currentImage != null) {
      Map<String, int[]> histograms = GraphUtil.generateHistogram(currentImage);

      BufferedImage histogramImage = GraphUtil.createHistogramImage(histograms);

      histogramPanel.removeAll();
      histogramPanel.add(new JLabel(new ImageIcon(histogramImage)));
      histogramPanel.revalidate();
      histogramPanel.repaint();
    }
  }

  /**
   * Updates the histogram display based on the provided image.
   * This method is used to refresh the histogram using a specific image,
   * typically a transformed or temporary image, without modifying the current
   * loaded image's histogram. If the provided image is null, the method does nothing.
   *
   * @param newImage the image for which the histogram should be generated and displayed
   */
  private void updateHistogram(CustomImage newImage) {
    if (currentImage != null) {
      Map<String, int[]> histograms = GraphUtil.generateHistogram(newImage);

      BufferedImage histogramImage = GraphUtil.createHistogramImage(histograms);

      histogramPanel.removeAll();
      histogramPanel.add(new JLabel(new ImageIcon(histogramImage)));
      histogramPanel.revalidate();
      histogramPanel.repaint();
    }
  }

  /**
   * Applies color visualization to the current image based on the specified color.
   *
   * @param color the color to visualize (red, green, or blue)
   */
  @Override
  public void applyColorVisualization(String color) {
    if (currentImage == null) {
      JOptionPane.showMessageDialog(this, "No image loaded.",
              "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    try {
      switch (color.toLowerCase()) {
        case "red":
          transformedImage = imageTransformations1.visualizeRed(currentImage.copy());
          updateHistogram(transformedImage);
          break;
        case "green":
          transformedImage = imageTransformations1.visualizeGreen(currentImage.copy());
          updateHistogram(transformedImage);
          break;
        case "blue":
          transformedImage = imageTransformations1.visualizeBlue(currentImage.copy());
          updateHistogram(transformedImage);
          break;
        default:
          throw new IllegalArgumentException("Unknown color visualization: " + color);
      }
      updateImageDisplay();
    } catch (Exception ex) {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(this, "Error visualizing color: "
                      + ex.getMessage(),
              "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Downscales the current image to the specified dimensions.
   *
   * @param newWidth  the target width
   * @param newHeight the target height
   */
  @Override
  public void applyDownscale(int newWidth, int newHeight) {
    if (currentImage == null) {
      JOptionPane.showMessageDialog(this, "No image loaded.",
              "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    if (newWidth <= 0 || newHeight <= 0
            || newWidth > currentImage.getWidth()
            || newHeight > currentImage.getHeight()) {
      JOptionPane.showMessageDialog(this,
              "Invalid dimensions. "
                      + "Width and height must be positive "
                      + "and smaller than the original dimensions.",
              "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    try {
      transformedImage = imageTransformations.downscale(currentImage, newWidth, newHeight);
      updateImageDisplay();
      JOptionPane.showMessageDialog(this,
              "Image downscaled successfully to " + newWidth + "x" + newHeight + "!",
              "Success", JOptionPane.INFORMATION_MESSAGE);
    } catch (Exception ex) {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(this,
              "An error occurred while downscaling the image.",
              "Error", JOptionPane.ERROR_MESSAGE);
    }
  }


  /**
   * Loads a mask image from the file system.
   */
  @Override
  public void loadMask() {
    JFileChooser fileChooser = new JFileChooser();
    int returnValue = fileChooser.showOpenDialog(this);

    if (returnValue == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      try {
        ImageUtil imageUtil = new ImageUtil();
        CustomImage tempMask = imageUtil.loadImage(selectedFile.getAbsolutePath());

        // Validate dimensions
        if (currentImage == null || tempMask.getWidth() != currentImage.getWidth()
                || tempMask.getHeight() != currentImage.getHeight()) {
          JOptionPane.showMessageDialog(this,
                  "Mask dimensions do not match the current image.",
                  "Error",
                  JOptionPane.ERROR_MESSAGE);
          return;
        }

        maskImage = tempMask;
        JOptionPane.showMessageDialog(this,
                "Mask loaded successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
      } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this,
                "Error loading mask: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  /**
   * Resets the displayed image to its original state, discarding any transformations.
   */
  @Override
  public void resetToOriginal() {
    if (currentImage == null) {
      JOptionPane.showMessageDialog(this,
              "No image loaded to reset.",
              "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    transformedImage = null;
    updateImageDisplay();
    updateHistogram();
    System.out.println("Image reset to original.");
  }

  /**
   * Applies the specified transformation to the currently loaded image.
   *
   * @param transformation the transformation to apply (e.g., "blur", "grayscale")
   */
  @Override
  public void applyTransformation(String transformation) {
    if (currentImage == null) {
      JOptionPane.showMessageDialog(this,
              "No image loaded.",
              "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    try {
      transformedImage = currentImage.copy();

      switch (transformation) {
        case "flipHorizontal":
          transformedImage = imageTransformations.flipHorizontal(transformedImage);
          updateHistogram(transformedImage);
          break;
        case "flipVertical":
          transformedImage = imageTransformations.flipVertical(transformedImage);
          updateHistogram(transformedImage);
          break;
        case "blur":
          transformedImage = imageTransformations.blur(transformedImage);
          updateHistogram(transformedImage);
          break;
        case "sharpen":
          transformedImage = imageTransformations.sharpen(transformedImage);
          updateHistogram(transformedImage);
          break;
        case "grayscale":
          transformedImage = imageTransformations.applyGrayscale(transformedImage);
          updateHistogram(transformedImage);
          break;
        case "sepia":
          transformedImage = imageTransformations.applySepia(transformedImage);
          updateHistogram(transformedImage);
          break;
        case "value":
          transformedImage = imageTransformations.visualizeValue(transformedImage);
          updateHistogram(transformedImage);
          break;
        case "intensity":
          transformedImage = imageTransformations.visualizeIntensity(transformedImage);
          updateHistogram(transformedImage);
          break;
        case "luma":
          transformedImage = imageTransformations.visualizeLuma(transformedImage);
          updateHistogram(transformedImage);
          break;
        case "color-correct":
          transformedImage = imageTransformations1.colorCorrect(transformedImage);
          updateHistogram(transformedImage);
          break;

        default:
          throw new IllegalArgumentException("Unknown transformation: " + transformation);


      }

      updateImageDisplay();
    } catch (Exception ex) {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(this,
              "Error applying transformation: "
                      + ex.getMessage(),
              "Error",
              JOptionPane.ERROR_MESSAGE);
    }
  }


  /**
   * Updates the displayed image in the main GUI area, reflecting the current
   * transformation or original image state.
   */
  @Override
  public void updateImageDisplay() {
    if (currentImage == null) {
      System.out.println("No image loaded. Cannot update display.");
      return;
    }

    try {
      BufferedImage displayImage;

      if (splitViewEnabled && transformedImage != null) {
        BufferedImage original = currentImage.toBufferedImage();
        BufferedImage transformed = transformedImage.toBufferedImage();

        int splitPercentage = splitPercentageSlider.getValue();
        int splitWidth = (original.getWidth() * splitPercentage) / 100;

        int totalWidth = original.getWidth();
        int maxHeight = Math.max(original.getHeight(), transformed.getHeight());

        BufferedImage combined = new BufferedImage(totalWidth,
                maxHeight, BufferedImage.TYPE_INT_RGB);
        Graphics g = combined.getGraphics();

        g.drawImage(original.getSubimage(0, 0, splitWidth,
                original.getHeight()), 0, 0, null);

        g.drawImage(transformed.getSubimage(splitWidth, 0,
                totalWidth - splitWidth,
                transformed.getHeight()), splitWidth, 0, null);
        g.dispose();

        displayImage = combined;
        System.out.println("Split view updated successfully with "
                + splitPercentage + "% split.");
      } else {
        displayImage = (transformedImage != null)
                ? transformedImage.toBufferedImage()
                : currentImage.toBufferedImage();
        System.out.println((transformedImage != null
                ? "Displaying transformed image."
                : "Displaying original image."));
      }

      imageLabel.setIcon(new ImageIcon(displayImage));
      imageLabel.revalidate();
      imageLabel.repaint();
      System.out.println("Image display updated successfully.");
    } catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(this,
              "Error updating image display: " + e.getMessage(),
              "Error", JOptionPane.ERROR_MESSAGE);
    }
  }


  /**
   * Main method for the gui, if ran, will invoke java swing to open the gui interface.
   *
   * @param args args that are passed to main.
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(ImageEditorGUI::new);
  }
}
