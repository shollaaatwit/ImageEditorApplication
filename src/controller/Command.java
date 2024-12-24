package controller;

import java.util.Map;

import model.CustomImage;


/**
 * Interface defining command operations for image manipulation.
 * Includes methods for processing individual commands and executing scripts.
 */
public interface Command {

  /**
   * Processes a single command for image manipulation.
   *
   * @param command      The command name to be executed.
   * @param commandParts Additional arguments required by the command.
   * @throws Exception if there is an error executing the command.
   */
  void processCommand(String command, String[] commandParts) throws Exception;

  /**
   * Executes a script file containing multiple image manipulation commands.
   *
   * @param scriptPath The file path of the script to be executed.
   * @param imageUtil  An instance of {@link ImageUtil} providing image operations.
   * @param imageMap   A map storing images by name, allowing command references.
   * @throws Exception if there is an error reading or executing the script.
   */
  void executeScript(String scriptPath,
                     ImageUtil imageUtil,
                     Map<String, CustomImage> imageMap) throws Exception;
}
