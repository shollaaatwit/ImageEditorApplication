## Overview
This project is structured to enable image manipulation and enhancement through a modular and extensible design. Below is an overview of each main class, interface, and its purpose.

# Class and Interface Descriptions : 

Transform interface:
Purpose: Defines a comprehensive set of methods for basic image transformations, including color adjustments, brightness, flipping, and RGB manipulations.
Responsibilities:
Color Adjustments: Includes methods to convert images to grayscale (applyGrayscale), sepia (applySepia), and apply color visualization for red, green, blue, value, intensity, and luma components.
Brightness Adjustment: Provides methods like brighten and adjustBrightness to modify the image’s brightness.
Flipping: Contains methods for horizontal and vertical flipping (flipHorizontal, flipVertical).
Blur and Sharpen: Defines methods for blurring and sharpening images.
RGB Manipulation: Provides methods to split an image into its red, green, and blue components (splitRGB) and to recombine them (combineRGB).

- Transformations interface:
Purpose: Provides a functional interface for performing transformations on RGB values of image pixels.
Responsibilities:
transform: Defines a method to apply a transformation to an RGB array, typically used for color or brightness adjustments on individual pixels.

- Command interface:
Purpose: Defines methods for processing commands and executing scripts for image manipulation tasks.
Responsibilities:
processCommand: Interprets and executes a single command based on the provided input.
executeScript: Reads a script file containing multiple commands and executes them sequentially for batch processing.

- AdvancedTransform interface:
Purpose: Defines advanced image manipulation methods, primarily for image compression, color correction, level adjustments, and split-view transformations.
Responsibilities:
levelsAdjust: Adjusts image contrast by setting black, mid-tone, and white points.
colorCorrect: Balances color levels in the image to achieve a consistent appearance.
compress: Compresses the image using a 2D Haar wavelet transform, retaining a specified percentage of data for lossy compression.
applySplitView: Creates a split-view transformation by applying a specific operation (e.g., blur or grayscale) to part of the image.

- Graph interface:
Purpose: Defines methods for generating and manipulating histograms from image data, providing insights into image color distributions and intensities.
Responsibilities:
Histogram Generation: Creates a histogram from an image’s RGB values using generateHistogram, which returns a mapping of color channels to intensity arrays.
Histogram Visualization: Generates a visual representation of the histogram as a BufferedImage through createHistogramImage.
Peak Detection: Identifies the peak intensity in a histogram array with findPeakValue, useful for tasks like color correction and contrast adjustment.

- CustomImage class:
Purpose: Represents an image as a 3D array of pixel data (RGB values), with methods to manipulate individual pixels and manage the image’s dimensions.
Responsibilities:
Pixel Manipulation: Provides methods for getting and setting RGB values for specific pixels (getPixel, setPixel).
Image Loading and Saving: Supports loading from and saving to PPM files (loadPPM, saveAsPPM).
Image Conversion: Includes methods to convert to/from BufferedImage and create a copy of the image data.
Constructor Management: Initializes image dimensions and pixel data, allowing for various ways to define or copy an image.

- ImageUtil class:
Purpose: Provides core utility functions for loading, saving, and performing image transformations, acting as an interface between Controller and AdvancedImageTransformations for managing CustomImage objects.
Responsibilities:
Image File Handling: Supports loading images from standard formats and PPM files (loadImage, loadPPM) and saving images in various formats, including PPM (saveImage, savePPM).
Basic Transformations: Provides methods for applying grayscale, sepia, brightness adjustments, flipping, blurring, and sharpening transformations on images.
Component Visualization: Enables visualization of specific color components (red, green, blue) and grayscale-based components (value, intensity, luma).
Advanced Transformations: Interfaces with AdvancedImageTransformations to apply complex transformations, such as compression, color correction, levels adjustment, and split views.
RGB Splitting and Combining: Splits an image into separate color channels (RGB) and recombines these channels into a single image.

- AbstractImageTransformations class:
Purpose: Provides a foundation for image transformation methods, including reusable utilities for applying transformations, clamping values, and using convolution kernels.
Responsibilities:
Transformation Application: Applies pixel-wise transformations through applyTransformation, using a specified transformation function.
Color Transformation: Supports applying transformations based on weighted color values (e.g., for grayscale or sepia effects) using applyColorTransformation.
Clamping Utility: Ensures pixel values stay within valid RGB bounds (0-255) via clamp.
Kernel Application: Provides applyKernel to apply convolution kernels, allowing for effects like blurring and sharpening.


- ImageTransformations class:
Purpose: Extends AbstractImageTransformations to provide concrete implementations for various image manipulation techniques, including color transformations, brightness adjustments, blurring, sharpening, and component visualization.
Responsibilities:
Color Transformations: Provides methods to convert images to grayscale and sepia tones.
Brightness Adjustment: Adjusts image brightness by a specified increment or decrement.
Flipping: Contains methods for horizontal and vertical flipping of images.
Blurring and Sharpening: Applies convolution kernels to achieve blurring and sharpening effects.
Color Component Visualization: Generates grayscale images representing specific components (Red, Green, Blue, Value, Intensity, Luma).
RGB Splitting and Combining: Splits an image into red, green, and blue components and recombines them into a single color image.

- AdvancedImageTransformations class:
Purpose: Extends ImageTransformations to provide advanced image processing features, including lossy compression using the Haar wavelet transform, split-view transformations, color correction, and levels adjustment.
Responsibilities:
Image Compression: Uses a 2D Haar wavelet transform in compress to apply lossy compression, keeping a specified percentage of significant data.
Split View Transformation: Implements applySplitView to apply transformations (e.g., blur, grayscale) to a portion of the image based on a given position.
Color Correction: Adjusts color levels across RGB channels to achieve balanced coloring via colorCorrect.
Levels Adjustment: Uses levelsAdjust to adjust black, mid-tone, and white points in the image for improved contrast.
Haar Transform Utilities: Includes methods for performing forward and inverse Haar transforms on each color channel independently, as well as for applying a compression threshold.

- GraphUtil class:
Purpose: Implements the Graph interface to provide utilities for generating and visualizing image histograms, offering insights into color distributions within an image.
Responsibilities:
Histogram Generation: Creates histograms for the red, green, and blue channels of an image using generateHistogram, storing each channel's frequency distribution in a map.
Histogram Visualization: Constructs a visual representation of the RGB histograms in createHistogramImage, displaying each color component's distribution as a line plot.
Peak Detection: Identifies the intensity value with the highest frequency in a histogram via findPeakValue, useful for color correction and adjustments.

- ControllerAbstract class:
Purpose: An abstract class implementing the Command interface to provide a framework for processing a wide variety of image manipulation commands. It serves as the core command handler that directs the flow of different image transformation tasks.
Responsibilities:
Command Processing: Accepts and interprets various image commands, such as load, save, grayscale, compress, and other transformation actions.
Transformation and Visualization: Delegates transformation commands to ImageUtil methods for operations like grayscale conversion, brightness adjustment, and RGB visualization.
Script Execution: Provides the structure for running batch commands from a script file, intended for implementation in subclasses.
Error Handling: Checks the validity of input commands, handles missing images, and provides feedback on command success or failure.

- Controller class:
Purpose: Acts as the main entry point for executing image manipulation commands via the command-line interface (CLI). Extends ControllerAbstract to provide a specific implementation that processes commands interactively or through scripts.
Responsibilities:
Command Execution: Reads and executes commands from user input in a CLI environment, passing tasks to the processCommand method in the superclass.
Script Processing: Implements executeScript to read and run a batch of commands from a specified script file.
User Interaction: Provides a prompt for users to input commands interactively, facilitating an intuitive CLI experience for image manipulation tasks.


### How to run:
In order to run this application,we will have to run the Controller script, which will allow the user to input
a command as a text field once the application has started running. To get started, the user will want to first load in the image that they will want to use, using the commands down below to load, save, enhance and manipulate their imagery in different formats of their choosing.

Alternatively, if the user wishes to run a script file for their commands, they can supply a script file with the list of commands they need and run the **script** command.
- **load <filepath> <imageName>**
  - Loads an image from the specified filepath and stores it in memory under the given
    image.
  - **Example:** `load src/dogs.jpg dog`
  - **Condition:** Must be executed before using other commands on the specified image.
- **save <filepath> <imageName>**
  - Saves the specified image to the filepath in the specified format (e.g., png, jpg, ppm).
  - **Example:** `save src/dogs.png dog`
  - **Condition:** The image specified by imageName must be loaded first.
- **script <filepath>**
  - Executes a script file containing commands in sequence.
  - **Example:** `script path/to/script.txt`
  - **Condition:** Ensure commands in the script file follow the correct order (e.g., `load` before
    `save`).
- **grayscale <sourceImage> <outputImageName>**
  - Converts the specified image to grayscale and stores the result under outputImageName.
  - **Example:** `grayscale dog grayscaleDog`
- **sepia <sourceImage> <outputImageName>**
  - Applies a sepia filter to the specified image and stores the result under outputImageName.
  - **Example:** `sepia dog sepiaDog`
- **vertical-flip <sourceImage> <outputImageName>**
  - Flips the specified image vertically.
  - **Example:** `vertical-flip dog verticalFlippedDog`
- **horizontal-flip [name] [new_name]**
    - This command will turn the current loaded image into a horizontally-flipped version of the image with a new name to refer to it with.
- **brighten <increment/decrement> <sourceImage> <outputImageName>**
  - Brightens the specified image by the given increment and saves it under outputImageName.
  - **Example:** `brighten 50 dog brightenedDog`
- **blur <sourceImage> <outputImageName>**
  - Applies a blur effect to the specified image.
  - **Example:** `blur dog blurredDog`
- **sharpen <sourceImage> <outputImageName>**
  - Sharpens the specified image.
  - **Example:** `sharpen dog sharpenedDog`
- **horizontal-flip <sourceImage> <outputImageName>**
  - Flips the specified image horizontally.
  - **Example:** `horizontal-flip dog horizontalFlippedDog`
- **value-component [name] [new_name]**
    - This command will turn the current loaded image into a version of the image depending on the value the user wants, such as rgb color and luma value.
- **red-component <sourceImage> <outputImageName>**
  - Visualizes only the red channel of the image.
  - **Example:** `red-component dog redVisualizedDog`
- **green-component <sourceImage> <outputImageName>**
  - Visualizes only the green channel of the image.
  - **Example:** `green-component dog greenVisualizedDog`
- **blue-component <sourceImage> <outputImageName>**
  - Visualizes only the blue channel of the image.
  - **Example:** `blue-component dog blueVisualizedDog`
- **luma-component <sourceImage> <outputImageName>**
  - Visualizes the luma component of the image.
  - **Example:** `luma-component dog lumaDog`
- **intensity-component <sourceImage> <outputImageName>**
  - Visualizes the intensity component of the image.
  - **Example:** `intensity-component dog intensityDog`
- **rgb-split <sourceImage> <redImage> <greenImage> <blueImage>**
  - Splits the RGB channels of the specified image into separate images for red, green, and blue.
  - **Example:** `rgb-split dog redDog greenDog blueDog`
- **rgb-combine <redImage> <greenImage> <blueImage> <outputImageName>**
  - Combines the red, green, and blue images into a single image.
  - **Example:** `rgb-combine redDog greenDog blueDog combinedRGBDog`
- **compress <percentage> <sourceImage> <outputImageName>**
  - Compresses the specified image by the given percentage.
  - **Example:** `compress 50 dog compressedDog`
- **levels-adjust <black> <mid> <white> <sourceImage> <outputImageName>**
  - Adjusts the levels of the specified image based on black, mid, and white points.
  - **Example:** `levels-adjust 20 128 200 dog levelAdjustedDog`
- **color-correct <sourceImage> <outputImageName>**
  - Applies color correction to the specified image.
  - **Example:** `color-correct dog colorCorrectedDog`
- **histogram <sourceImage> <outputImageName>**
  - Generates a histogram for the specified image.
  - **Example:** `histogram dog histogramDog`
- **<operation> <sourceImage> <outputImageName> split <position>**
  - Applies the specified operation (e.g., sepia) to part of the image based on position.
  - **Example:** `sepia dog splitSepiaDog split 50`
- **exit**
  - this will exit the software if using the input line.


## Summary Of Changes

#### Assignment 5 Update:
  In this assignment we implemented new functionalities to the software. These core functionalities include the ability to compress,
  color correct, level adjust and produce a histogram of the current image. Additionally, split view operation support has been given to blur, sharpen, 
sepia, grayscale and levels adjust.

New functionalities were implemented through an additional class that extended the old ImageTransformations as AdvancedImageTransformations. We did this so that we could
leverage the abstract class to avoid duplicate code and also avoid having to revise the originally made class in our design. We also created abstract classes for the
controller to avoid code duplication, and interfaces for all of our public methods so that they could be documented.

Interfaces and abstract classes, as well as the additional transformation class were made to keep our design clean and readable, while also keeping our original design ideas.

#### Assignment 6 Update:

In this assignment, we implemented a graphical user interface for the application. When running the GUI, the user will be able to perform numerous operations that were previously implemented
as well as new operations such as the image scaling tool. The gui will also constantly show a histogram of the current image that is being edited, so that the user will always see the graph representation
of the rgb values in the image.

During this update, we also added more interface integration to the project to improve readability, as well as improving our abstract classes. Additional classes were made to organize helper functions so that
the classes that were part of the core design would be easier to revise. Numerous improvements to previous functionalities were also made, such as to compression.


## Reference picture used for testing and Citation

    - Two yellow labrador retriever puppies · free stock photo. (n.d.). https://www.pexels.com/photo/two-yellow-labrador-retriever-puppies-1108099/ 


