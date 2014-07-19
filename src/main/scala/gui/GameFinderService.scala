package gui

import javax.swing.{JOptionPane, SwingWorker}
import model.GameFinder
import java.io.IOException

/**
 * Created by Chris on 7/5/2014.
 */
class GameFinderService(val resultText: String) extends SwingWorker[Void, Void] {
  protected def doInBackground: Void = {
    try {
      GameFinder.performInitialGameSearch()
    } catch {
      case ex: IOException => showFailedDialog(ex.getMessage)
    }
    null
  }

  private def showFailedDialog(error: String): Unit = {
    val buggerMsg =
      "Bugger! Something is amiss! I was unable \n" +
      "to find your game files. This is likely my fault.\n" +
      "Feel free to drop me a line at audionautic@gmail.com \n" +
      "and I'll get this sorted out post haste!\n\n Reason For Error:\n" +
      error
    JOptionPane.showMessageDialog(null, buggerMsg, "Doh!", JOptionPane.ERROR_MESSAGE)
    System.exit(1);
  }
}