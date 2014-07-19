package model

import java.io.File
import java.nio.file.{Paths, Path}

/**
 * Created by Chris on 6/29/2014.
 */
abstract class Directory(private val dir: File) {

  def getDateLastModified(): Long

  def getFiles = this.dir.listFiles.filter(!_.isDirectory).toList

  def getPath: Path = Paths.get(this.dir.getAbsolutePath)
}
