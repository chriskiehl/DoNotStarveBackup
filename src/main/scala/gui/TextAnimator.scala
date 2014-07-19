package gui

import scala.annotation.tailrec
import javax.swing.text.JTextComponent
import javax.swing.{SwingWorker, JComponent, JLabel}



class TextAnimator(val textObject: AnyRef, var pattern: String) extends SwingWorker[Void, Void] {
  require(textObject.isInstanceOf[JLabel] || textObject.isInstanceOf[JTextComponent])

  protected def doInBackground: Void = {
    appendEllipsis(1)
    return null;
  }

  @tailrec
  private def appendEllipsis(amount: Int): Unit = {
    if (!isCancelled) {
      this.setText(this.textObject, updateText(pattern, amount))
      Thread.sleep(350)
      if (amount > 4) appendEllipsis(0)
      else appendEllipsis(amount + 1)
    }
  }

  private def setText(obj: AnyRef, text: String): Unit = {
    if (obj.isInstanceOf[JLabel]) obj.asInstanceOf[JLabel].setText(text)
    else obj.asInstanceOf[JTextComponent].setText(text)
  }

  private def updateText(template: String, amount: Int): String = {
    if (isTemplateString(template)) template.format("." * amount)
    else template + "." * amount
  }

  private def isTemplateString(s: String): Boolean = {
    return s.contains("%s")
  }

  def setNewTemplate(template: String): Unit = {
    this.pattern = template
  }



}
