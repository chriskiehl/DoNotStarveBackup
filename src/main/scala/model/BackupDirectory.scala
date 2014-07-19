package model

import java.io.File
import java.nio.file.{StandardCopyOption, Files, Paths}

/**
 * TODO:
 *  - Handle max number of backups to keep
 *
 * Created by Chris on 6/29/2014.
 */
class BackupDirectory(private val dir: File) extends Directory(dir) {
  require(this.dir.exists())

  def getSaveFiles(): List[SaveFile] =
    this.dir.listFiles.filter(_.isDirectory).map(f => new SaveFile(f)).toList

  override def getDateLastModified(): Long = {
    val files = this.dir.listFiles.filter(_.isDirectory).map(_.getName.toLong)
    if (files.isEmpty) 0
    else files.max
  }

  def removeOutdatedBackups() = {
    val backups = this.dir.listFiles
    val oldBackkups = backups.sortBy(_.lastModified()).take(backups.size - 20)
    oldBackkups.flatMap(d => d.listFiles()).foreach(_.delete())
    oldBackkups.foreach(_.delete())
  }

  def backupTo(gameDir: GameDirectory): Unit = {
    val toBackupDir = this.createTimestampedBackupDirectory(gameDir.getDateLastModified())
    toBackupDir.mkdir()
    this.copyFiles(gameDir.getFiles, toBackupDir)
//    removeOutdatedBackups()
  }

  def restoreTo(gameDir: GameDirectory, saveFile: SaveFile): Unit = {
    val backupFiles = saveFile.contents.listFiles()
    backupFiles.foreach(println)
    backupFiles.foreach(f =>
      Files.copy(Paths.get(f.getAbsolutePath), Paths.get(gameDir.getPath.toString, f.getName),
        StandardCopyOption.REPLACE_EXISTING)
    )
  }

  private def copyFiles(files: List[File], destination: File): Unit = {
    val toBackupDir = Paths.get(destination.getAbsolutePath).toString
    files.foreach(
      gameDir => Files.copy(Paths.get(gameDir.getAbsolutePath), Paths.get(toBackupDir, gameDir.getName))
    )
  }

  private def createTimestampedBackupDirectory(timestamp: Long): File = {
    val pathToNewDirectory = Paths.get(this.dir.getAbsolutePath, timestamp.toString).toString
    new File(pathToNewDirectory)
  }

}
