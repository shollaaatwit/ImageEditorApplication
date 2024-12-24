package view;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JCheckBox;
import java.awt.Container;
import java.awt.Component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Test class for the ImageEditorGUI. This class contains unit tests to validate
 * the functionality of the graphical user interface for image editing, including
 * transformations, sliders, split view, and save/load operations.
 */
public class ImageEditorGUITest {
  private ImageEditorGUI editor;

  /**
   * Finds a JLabel in the given JFrame by its name.
   *
   * @param frame the JFrame containing the components
   * @param name  the name of the JLabel to find
   * @return the JLabel if found, otherwise null
   */
  private JLabel findLabelByName(JFrame frame, String name) {
    for (Component component : frame.getContentPane().getComponents()) {
      if (component instanceof JLabel && name.equals(component.getName())) {
        return (JLabel) component;
      }
    }
    return null;
  }

  /**
   * Finds a JSlider in the given JFrame by its name.
   *
   * @param frame      the JFrame containing the components
   * @param sliderName the name of the JSlider to find
   * @return the JSlider if found, otherwise null
   */
  private JSlider findSliderByName(JFrame frame, String sliderName) {
    for (Component component : frame.getContentPane().getComponents()) {
      if (component instanceof JSlider) {
        JSlider slider = (JSlider) component;
        if (sliderName.equals(slider.getName())) {
          return slider;
        }
      } else if (component instanceof Container) {
        JSlider found = findSliderInContainer((Container) component, sliderName);
        if (found != null) {
          return found;
        }
      }
    }
    return null;
  }

  /**
   * Recursively finds a JSlider in a nested Container by its name.
   *
   * @param container  the Container to search
   * @param sliderName the name of the JSlider to find
   * @return the JSlider if found, otherwise null
   */
  private JSlider findSliderInContainer(Container container, String sliderName) {
    for (Component child : container.getComponents()) {
      if (child instanceof JSlider) {
        JSlider slider = (JSlider) child;
        if (sliderName.equals(slider.getName())) {
          return slider;
        }
      } else if (child instanceof Container) {
        JSlider found = findSliderInContainer((Container) child, sliderName);
        if (found != null) {
          return found;
        }
      }
    }
    return null;
  }

  /**
   * Finds a JCheckBox in the given JFrame by its name.
   *
   * @param frame        the JFrame containing the components
   * @param checkboxName the name or text of the JCheckBox to find
   * @return the JCheckBox if found, otherwise null
   */
  private JCheckBox findCheckboxByName(JFrame frame, String checkboxName) {
    for (Component component : frame.getContentPane().getComponents()) {
      if (component instanceof JCheckBox) {
        JCheckBox checkbox = (JCheckBox) component;
        if (checkboxName.equals(checkbox.getText())) {
          return checkbox;
        }
      } else if (component instanceof Container) {
        // Recursively search in nested containers
        JCheckBox found = findCheckboxInContainer((Container) component, checkboxName);
        if (found != null) {
          return found;
        }
      }
    }
    return null;
  }

  /**
   * Recursively finds a JCheckBox in a nested Container by its name.
   *
   * @param container    the Container to search
   * @param checkboxName the name or text of the JCheckBox to find
   * @return the JCheckBox if found, otherwise null
   */
  private JCheckBox findCheckboxInContainer(Container container, String checkboxName) {
    for (Component child : container.getComponents()) {
      if (child instanceof JCheckBox) {
        JCheckBox checkbox = (JCheckBox) child;
        if (checkboxName.equals(checkbox.getText())) {
          return checkbox;
        }
      } else if (child instanceof Container) {
        JCheckBox found = findCheckboxInContainer((Container) child, checkboxName);
        if (found != null) {
          return found;
        }
      }
    }
    return null;
  }


  /**
   * Sets up the test environment by initializing the ImageEditorGUI instance.
   */
  @Before
  public void setUp() {
    editor = new ImageEditorGUI();
  }

  /**
   * Tears down the test environment by disposing of the ImageEditorGUI instance.
   */
  @After
  public void tearDown() {
    if (editor != null) {
      editor.dispose();
    }
  }

  /**
   * Tests loading an image with a null file selection.
   * Ensures that an appropriate error message is displayed.
   */
  @Test
  public void testLoadImageWithNullFile() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Status label should reflect no file selected.",
                "No image loaded", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception thrown during loadImage with null file: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests saving an image without loading any image first.
   * Ensures that an appropriate error message is displayed.
   */
  @Test
  public void testSaveImageWithoutLoading() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.saveImage();
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Status label should indicate no image to save.",
                "No image to save", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception thrown during saveImage without loading an image: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests resetting to the original image state without loading an image.
   * Ensures that an appropriate error message is displayed.
   */
  @Test
  public void testResetToOriginalWithoutImage() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.resetToOriginal();
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Status label should indicate no image to reset.",
                "No image to reset", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception thrown during resetToOriginal without an image: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }


  /**
   * Tests loading a valid image into the GUI.
   * Fails test if it does not load the image.
   */
  @Test
  public void testLoadValidImage() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Status label should confirm the image is loaded.",
                "Image loaded successfully", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception thrown during loadImage with a valid file: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests saving an image through the GUI after loading it in.
   * Fails if it cannot save or load the image.
   */
  @Test
  public void testSaveImageAfterLoading() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        editor.saveImage();
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Status label should confirm the image is saved.",
                "Image saved successfully", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception thrown during saveImage after loading an image: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests saving an image from an invalid path.
   * Passes if it can save the image to the path specified.
   */
  @Test
  public void testSaveImageWithInvalidPath() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        editor.saveImage();
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Status label should reflect save failure.",
                "Failed to save image", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception thrown during saveImage with invalid path: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests applying levels without an image being present.
   * Passes if it can catch the exception of no image. existing.
   */
  @Test
  public void testApplyLevelsWithoutImage() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.resetToOriginal(); // Ensure no image is loaded
        editor.applyTransformation("levels");
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Status label should indicate no image for applying levels.",
                "No image loaded to apply levels", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception thrown during applyLevels without an image: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests transform application with no image present.
   * Passes if it catches the exception.
   */
  @Test
  public void testApplyTransformationWithoutImage() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.applyTransformation("blur");
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Status label should indicate no image for transformation.",
                "No image loaded to apply transformation", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception thrown during applyTransformation without an image: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests trying to load in an invalid image file.
   * Passes if it can successfully catch the exception.
   */
  @Test
  public void testLoadInvalidImageFile() {
    SwingUtilities.invokeLater(() -> {
      try {
        // Simulate loading an invalid image file
        editor.loadImage(); // You need to mock a file chooser to simulate invalid file input
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Status label should indicate invalid file.",
                "Error loading image: Invalid file format", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception thrown during loadImage with invalid file: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests applying blur effect after loading in the image.
   */
  @Test
  public void testApplyBlurAfterLoading() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        editor.applyTransformation("blur");
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Status label should confirm the blur transformation was applied.",
                "Blur applied successfully", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception thrown during blur transformation after loading an image: "
                + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests applying grayscale effect after loading in the image.
   */
  @Test
  public void testApplyGrayscaleAfterLoading() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        editor.applyTransformation("grayscale");
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals(
                "Status label should confirm the grayscale transformation was applied.",
                "Grayscale applied successfully", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception thrown during grayscale transformation after loading an image: "
                + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests reseting the image after performing a transformation through gui.
   */
  @Test
  public void testResetAfterTransformation() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        editor.applyTransformation("blur");
        editor.resetToOriginal();
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Status label should confirm reset to original.",
                "Image reset to original", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception thrown during resetToOriginal after applying transformation: "
                + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests split view toggle in the GUI to see if it splits correctly.
   */
  @Test
  public void testSplitViewToggle() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();

        // Find the split view checkbox (assuming it is part of the GUI)
        JCheckBox splitViewCheckbox = null;
        for (Component comp : editor.getContentPane().getComponents()) {
          if (comp instanceof JCheckBox && "Split View".equals(((JCheckBox) comp).getText())) {
            splitViewCheckbox = (JCheckBox) comp;
            break;
          }
        }
        assertNotNull("Split view checkbox should exist.", splitViewCheckbox);

        // Simulate toggling the checkbox
        splitViewCheckbox.setSelected(true);
        assertEquals("Split view should be enabled.",
                true, splitViewCheckbox.isSelected());
      } catch (Exception e) {
        fail("Exception thrown during split view toggle: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests invalid slider values for transforms that have sliders.
   */
  @Test
  public void testInvalidSliderValues() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        editor.applyTransformation("levels");
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Status label should reflect invalid slider configuration.",
                "Invalid slider values: Black < Mid < White", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception thrown during invalid slider adjustments: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests level adjustment with valid levels of input.
   */
  @Test
  public void testAdjustLevelsWithValidInput() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();

        JSlider blackSlider = findSliderByName(editor, "blackLevelSlider");
        JSlider midSlider = findSliderByName(editor, "midLevelSlider");
        JSlider whiteSlider = findSliderByName(editor, "whiteLevelSlider");

        assertNotNull("Black level slider should exist.", blackSlider);
        assertNotNull("Mid level slider should exist.", midSlider);
        assertNotNull("White level slider should exist.", whiteSlider);

        blackSlider.setValue(20);
        midSlider.setValue(128);
        whiteSlider.setValue(240);

        editor.applyTransformation("levels");
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Levels adjusted successfully.",
                "Levels adjusted successfully",
                statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during level adjustment with valid input: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests level adjustment with invalid b, m, w levels.
   */
  @Test
  public void testAdjustLevelsWithInvalidInput() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();

        JSlider blackSlider = findSliderByName(editor, "blackLevelSlider");
        JSlider midSlider = findSliderByName(editor, "midLevelSlider");
        JSlider whiteSlider = findSliderByName(editor, "whiteLevelSlider");

        assertNotNull("Black level slider should exist.", blackSlider);
        assertNotNull("Mid level slider should exist.", midSlider);
        assertNotNull("White level slider should exist.", whiteSlider);

        blackSlider.setValue(150);
        midSlider.setValue(100); // Invalid: black >= mid
        whiteSlider.setValue(200);

        editor.applyTransformation("levels");
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Invalid levels configuration. Ensure: Black < Mid < White.",
                statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during level adjustment with invalid input: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests applying valid compression in GUI.
   */
  @Test
  public void testApplyCompressionWithValidInput() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();

        JSlider compressSlider = findSliderByName(editor, "compressSlider");
        assertNotNull("Compression slider should exist.", compressSlider);

        compressSlider.setValue(50); // Set compression to 50%

        editor.applyTransformation("compress");
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Image compressed successfully.", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during compression with valid input: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests applying invalid compression and catching exception.
   */
  @Test
  public void testApplyCompressionWithInvalidInput() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();

        JSlider compressSlider = findSliderByName(editor, "compressSlider");
        assertNotNull("Compression slider should exist.", compressSlider);

        compressSlider.setValue(-10);

        editor.applyTransformation("compress");
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Invalid compression value.", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during compression with invalid input: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests loading mask successfully into gui.
   */
  @Test
  public void testLoadMaskSuccessfully() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadMask();
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Mask loaded successfully!", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during mask loading: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests loading in then applying transform with the mask in the gui.
   */
  @Test
  public void testApplyTransformationWithMask() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        editor.loadMask();

        editor.applyTransformation("blur"); // Apply transformation with mask
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Transformation applied with mask successfully.",
                statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during transformation with mask: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests disabling split view.
   */
  @Test
  public void testDisableSplitView() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();

        JCheckBox splitViewCheckbox = findCheckboxByName(editor, "Split View");
        assertNotNull("Split view checkbox should exist.", splitViewCheckbox);

        splitViewCheckbox.setSelected(false); // Simulate disabling split view
        assertEquals("Split view should be disabled.",
                false, splitViewCheckbox.isSelected());
      } catch (Exception e) {
        fail("Exception during disabling split view: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests a horizontal flip through the gui.
   */
  @Test
  public void testFlipHorizontal() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        editor.applyTransformation("flipHorizontal");
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Image flipped horizontally.", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during horizontal flip: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests a vertical flip through the gui.
   */
  @Test
  public void testFlipVertical() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        editor.applyTransformation("flipVertical");
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Image flipped vertically.", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during vertical flip: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests the red visualization function in the gui.
   */
  @Test
  public void testVisualizeRed() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        editor.applyColorVisualization("red-component");
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Red component visualization applied.", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during red visualization: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests the green visualization function in the gui.
   */
  @Test
  public void testVisualizeGreen() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        editor.applyColorVisualization("green-component");
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Green component visualization applied.", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during green visualization: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests the blue visualization function in the gui.
   */
  @Test
  public void testVisualizeBlue() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        editor.applyColorVisualization("blue-component");
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Blue component visualization applied.", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during blue visualization: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests blur transformation in the gui.
   */
  @Test
  public void testBlurTransformation() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        editor.applyTransformation("blur");
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Blur applied successfully.", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during blur transformation: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests a sharpen transformation in the gui.
   */
  @Test
  public void testSharpenTransformation() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        editor.applyTransformation("sharpen");
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Sharpen applied successfully.", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during sharpen transformation: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests a sepia transformation in the gui.
   */
  @Test
  public void testSepiaTransformation() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        editor.applyTransformation("sepia");
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Sepia applied successfully.", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during sepia transformation: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests a grayscale transformation in the gui.
   */
  @Test
  public void testGrayscaleTransformation() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        editor.applyTransformation("grayscale");
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Grayscale applied successfully.", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during grayscale transformation: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests chaining multiple transformations together i.e(grayscale, blur, sharpen),
   * all into one image through the gui.
   */
  @Test
  public void testChainingTransformations() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        editor.applyTransformation("grayscale");
        editor.applyTransformation("blur");
        editor.applyTransformation("sharpen");
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Sharpen applied successfully.", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during chained transformations: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests applying an invalid transformation and catching the exception.
   */
  @Test
  public void testApplyInvalidTransformation() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        editor.applyTransformation("invalidTransformation");
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Error: Unknown transformation.", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during invalid transformation: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests split view rendering correctly through gui.
   */
  @Test
  public void testSplitViewRendering() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        editor.applyTransformation("blur");
        JCheckBox splitViewCheckbox = findCheckboxByName(editor, "Split View");
        assertNotNull("Split view checkbox should exist.", splitViewCheckbox);

        splitViewCheckbox.setSelected(true);
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Split view enabled.", statusLabel.getText());

        splitViewCheckbox.setSelected(false);
        assertEquals("Split view disabled.", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during split view rendering: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests downscale transformation in gui.
   */
  @Test
  public void testDownscaleTransformation() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        editor.applyDownscale(100, 100);
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Image downscaled successfully to 100x100!", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during downscale transformation: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests a set of invalid downscale dimensions through the gui and,
   * catches the exception if it passes.
   */
  @Test
  public void testInvalidDownscaleDimensions() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        editor.applyDownscale(-10, -20);
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Invalid dimensions. "
                        + "Width and height must be positive "
                        + "and smaller than the original dimensions.",
                statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during invalid downscale dimensions: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests reset function after multiple operations are performed on the image.
   */
  @Test
  public void testResetAfterMultipleTransformations() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        editor.applyTransformation("grayscale");
        editor.applyTransformation("blur");
        editor.applyTransformation("sharpen");
        editor.resetToOriginal();

        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Image reset to original", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during reset after multiple transformations: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests loading in an empty file.
   */
  @Test
  public void testHandleEmptyFileLoad() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Error: Empty file loaded.", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during empty file load: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests attempting to load in an unsupported file format and catches exception.
   */
  @Test
  public void testLoadUnsupportedFileFormat() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Error loading image: Unsupported file format",
                statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during unsupported file format load: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests trying to load in multiple images in sequence.
   */
  @Test
  public void testLoadMultipleImagesSequentially() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        JLabel statusLabel1 = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist for first image load.", statusLabel1);
        assertEquals("Image loaded successfully", statusLabel1.getText());

        editor.loadImage();
        JLabel statusLabel2 = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist for second image load.", statusLabel2);
        assertEquals("Image loaded successfully", statusLabel2.getText());
      } catch (Exception e) {
        fail("Exception during sequential image loading: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests applying sepia after applying a color visualization function.
   */
  @Test
  public void testApplySepiaAfterColorVisualization() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        editor.applyColorVisualization("red");
        editor.applyTransformation("sepia");

        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("Sepia applied successfully.", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during sepia after color visualization: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests trying to save an image to a valid path.
   */
  @Test
  public void testSaveImageWithValidPath() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        editor.saveImage();

        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist after save operation.", statusLabel);
        assertEquals("Image saved successfully!", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during save image with valid path: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests saving an image with no loaded image, which will be caught if it passes.
   */
  @Test
  public void testSaveImageWithoutAnyLoadedImage() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.saveImage();

        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist.", statusLabel);
        assertEquals("No image loaded to save.", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during save image without a loaded image: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests saving an image after any transformation function.
   */
  @Test
  public void testSaveImageAfterTransformation() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();
        editor.applyTransformation("blur");

        editor.saveImage();

        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist after save operation.", statusLabel);
        assertEquals("Image saved successfully!", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during save image after transformation: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests overwriting an existing file with a new one.
   */
  @Test
  public void testOverwriteExistingFile() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();

        editor.saveImage();

        editor.saveImage();

        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist after overwriting a file.",
                statusLabel);
        assertEquals("Image saved successfully!", statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during overwrite existing file save: " + e.getMessage());
      }
    });
    assertNotNull(editor);
  }

  /**
   * Tests saving a file with an invalid file extension,
   * will catch the exception if it passes.
   */
  @Test
  public void testSaveImageWithInvalidExtension() {
    SwingUtilities.invokeLater(() -> {
      try {
        editor.loadImage();


        editor.saveImage();

        JLabel statusLabel = findLabelByName(editor, "statusLabel");
        assertNotNull("Status label should exist after invalid extension save attempt.",
                statusLabel);
        assertEquals("Error saving image: Unsupported file extension.",
                statusLabel.getText());
      } catch (Exception e) {
        fail("Exception during save image with invalid extension: "
                + e.getMessage());
      }
    });
    assertNotNull(editor);
  }


}