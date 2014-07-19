package model

import java.io.File
import org.joda.time.LocalDateTime

/**
 * Simple data object for properly displaying
 * the backup dir as a date time in the Restore Window's
 * JList object
 *
 * Created by Chris on 7/6/2014.
 */
class SaveFile(val file: File) {
  val contents = file
  val timestamp = this.contents.getName.toLong
  private val date = new LocalDateTime(this.contents.getName.toLong)
  private val template = "Backup - %s/%s/%S - %02d:%02d:%02d %s"

  override def toString(): String = {
    template.format(
      date.getMonthOfYear,
      date.getDayOfMonth,
      date.getYear,
      date.getHourOfDay,
      date.getMinuteOfHour,
      date.getSecondOfMinute,
      if (date.getHourOfDay < 12) "am" else "pm"
    )
  }


}
