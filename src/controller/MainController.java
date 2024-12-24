package controller;

import java.util.HashMap;
import java.util.Map;

import model.CustomImage;
import view.ImageEditorGUI;

/**
 * Main Controller class, changes text mode or gui mode depending on flags,
 * that are passed through i.e, -text, or -file for passing in files.
 */
public class MainController {

  /**
   * Reads in command line arguments and passes it to text mode controller,
   * or ImageEditorGUI main method depending on flags.
   * @param args the arguments being passed through MainController.
   * @throws Exception if there is an invalid flag passed through.
   */
  public static void main(String[] args) throws Exception {
    boolean isTextMode = false;
    boolean isScriptMode = false;
    String scriptPath = null;

    for (String arg : args) {
      if (arg.equals("-text")) {
        isTextMode = true;
        break;
      }
      if (arg.equals("-file")) {
        isScriptMode = true;
        scriptPath = args[1];
        break;
      }
    }

    if (isTextMode) {
      Controller.main(args);
    } else if (isScriptMode) {
      if (scriptPath != null) {
        processScript(scriptPath);
      }
    } else {
      ImageEditorGUI.main(args);
    }
  }

  /**
   * Processes script files if the -file flag is passed through.
   * @param scriptPath the string containing the path of the script to be used.
   * @throws Exception if there is an invalid path.
   */
  private static void processScript(String scriptPath) throws Exception {
    ImageUtil imageUtil = new ImageUtil();
    Map<String, CustomImage> imageMap = new HashMap<>();
    Controller controller = new Controller(imageMap, imageUtil);
    controller.executeScript(scriptPath, imageUtil, imageMap);
  }
}
