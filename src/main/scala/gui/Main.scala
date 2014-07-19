package gui

/**
 * Created by Chris on 7/6/2014.
 */
object Main {

  def main(args: Array[String]) {
    println("Helloo!")
    for (info <- javax.swing.UIManager.getInstalledLookAndFeels) {
      if ("Nimbus" == info.getName) {
        javax.swing.UIManager.setLookAndFeel(info.getClassName)
      }
    }
    java.awt.EventQueue.invokeLater(new Runnable {
      def run {
        new MainWindow().setVisible(true)
      }
    })
  }
}
