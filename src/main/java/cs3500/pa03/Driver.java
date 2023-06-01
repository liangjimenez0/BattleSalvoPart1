package cs3500.pa03;

import cs3500.pa03.controller.Controller;
import cs3500.pa03.controller.ControllerImp;
import cs3500.pa03.model.Board;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    Readable input = new InputStreamReader(System.in);
    Appendable output = System.out;
    Controller controller =
        new ControllerImp(input, output, new Random(), new Board(), new Board());

    try {
      controller.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}