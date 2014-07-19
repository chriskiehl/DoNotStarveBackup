package model

import java.io.File

/**
 * Created by Chris on 6/29/2014.
 */
class GameDirectory(private val dir: File) extends Directory(dir) {
  require(this.dir.exists())

  override def getDateLastModified(): Long = {
    this.dir.listFiles.filter(!_.isDirectory).map(_.lastModified()).max
  }



}
