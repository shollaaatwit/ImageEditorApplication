package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

import model.CustomImage;

/**
 * Controller class that handles user input for image manipulation commands
 * and executes them. This class can process both interactive commands and
 * batch commands from a script file.
 */
public class Controller extends ControllerAbstract {

  /**
   * Constructs a Controller instance with the specified image map and image utility.
   *
   * @param imageMap  A map storing images by name.
   * @param imageUtil An instance of {@link ImageUtil} providing image operations.
   */
  public Controller(Map<String, CustomImage> imageMap, ImageUtil imageUtil) {
    super(imageMap, imageUtil);
  }

  /**
   * The input system and args are run through main for the image modification
   * commands.
   *
   * @param args commands input through commands.
   * @throws Exception if an error occurs while processing commands.
   */
  public static void main(String[] args) throws Exception {

    Map<String, CustomImage> imageMap = new HashMap<>();
    ImageUtil imageUtil = new ImageUtil();
    Scanner scanner = new Scanner(System.in);
    ControllerAbstract controller = new Controller(imageMap, imageUtil);
    System.out.println("Enter commands (type 'exit' to quit):");
    while (true) {
      String input = scanner.nextLine().trim();
      if ("exit".equalsIgnoreCase(input)) {
        break;
      }
      String[] parts = input.split(" ");
      String command = parts[0];
      String[] commandParts = new String[parts.length - 1];
      if (parts.length > 1) {
        System.arraycopy(parts, 1, commandParts, 0, parts.length - 1);
      }
      try {
        controller.processCommand(command, parts);
      } catch (Exception e) {
        System.out.println("Invalid command, try again");
      }
    }
    scanner.close();
  }

  /**
   * Method for executing the contents of a given script and filepath.
   *
   * @param scriptPath The file path of a given script that will be executed.
   * @param imageUtil  The ImageUtil class that holds all the necessary image functionalities.
   * @param imageMap   Hashmap that will store the data of images that are loaded in.
   * @throws IOException Throws an exception if the file is unable to load in.
   */
  @Override
  public void executeScript(String scriptPath, ImageUtil imageUtil, Map<String,
          CustomImage> imageMap) throws Exception {
    List<String> commands = Files.readAllLines(Paths.get(scriptPath));
    for (String command : commands) {
      command = command.trim();
      if (command.isEmpty() || command.startsWith("#")) {
        continue;
      }
      String[] parts = command.split(" ");
      String action = parts[0];
      processCommand(action, parts);
    }
  }
}