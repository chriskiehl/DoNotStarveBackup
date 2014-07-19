package model

import java.io.{IOException, File}
import java.nio.file.{Files, Paths}

/**
 * TODO: Basic Search/Advanced Search
 *
 * Created by Chris on 6/20/2014.
 */

class BetterFile(val file: File) {
  def listDirs =
    if (driveIsUnreadable(file)) List()
    else file.listFiles.filter(validFile).toList

  private def driveIsUnreadable(file: File): Boolean =
    file.listFiles == null

  private def validFile(f: File): Boolean =
    f.isDirectory && !f.isHidden &&
    Files.isReadable(Paths.get(f.getAbsolutePath)) &&
    !f.getAbsolutePath.matches("[A-Z]:\\\\Windows.*|.*Temp.*")
}

object GameFinder {
  private val assumedLocalDir = new File(Paths.get(System.getenv("HOMEPATH"), "Documents", "Klei", "DoNotStarve").toString)
  private val remotePath = Paths.get("Steam", "userdata", "32945317", "219740", "remote").toString
  private val assumedRemoteDir = List(
    new File(Paths.get(System.getenv("PROGRAMFILES"), remotePath).toString),
    new File(Paths.get(System.getenv("PROGRAMFILES(X86)"), remotePath).toString)
//    new File("F:\\Program Files\\Steam\\userdata\\32945317\\219740\\remote")
  )
  private val userHome = new File(System.getProperty("user.home"))
  private val rootDirectory = findRootDirFrom(this.userHome)
  private val localGameDirName = "DoNotStarve"
  private val remoteGameDirName = "219740"

  private implicit def fileToBetterFile(f: File) = new BetterFile(f)

  def findRootDirFrom(file: File): File =
    if (file.getParentFile == null) file
    else findRootDirFrom(file.getParentFile)

  def findLocalGameFiles(): File =
    new File(findGameDir(this.rootDirectory, localGameDirName))

  def findRemoteGameDir(): File = {
    val roots = File.listRoots.toList
    val result = roots.par.map(root => new File(findGameDir(root, remoteGameDirName))).filter(s => s.exists())
    if (!result.isEmpty) result(0)
    else new File("")
  }

  def findGameDir(root: File, target: String): String = {
    var result = ""
    val dirs = root.listDirs
    if (dirs.map(_.getName).contains(target)) {
      result = getMatch(root, target).getAbsolutePath
      result
    }
    else {
      for  (f <- dirs)
        result += findGameDir(f, target)
    }
    if (result == System.getProperty("user.home")) ""
    else result
  }

  def getMatch(file: File, target: String): File =
    file.listDirs.find(f => f.getName.equalsIgnoreCase(target)).get

  def hasSettingsFile(directory: String): Boolean = {
    return new File(directory).listFiles.map(_.getName).contains("settings.ini")
  }

  def performInitialGameSearch(): Unit = {
    val backupSettings = Settings.createEmptySettings();

    val localFiles = if (assumedLocalDir.exists()) assumedLocalDir else findLocalGameFiles()
    if (!localFiles.exists()) throw new IOException("Could not locate local game files")

    val settingsFile = new File(localFiles, "settings.ini")
    if (!settingsFile.exists) throw new IOException("Settings.ini not found")

    backupSettings.put("INI_PATH", settingsFile.getAbsolutePath)
    val gameSettings = Settings.loadSettings(settingsFile)
    if (isStoredLocally(gameSettings))
      backupSettings.put("SAVE_FILE_PATH", Paths.get(localFiles.getAbsolutePath, "save").toString)
    else {
      val remoteLocation = assumedRemoteDir match {
        case x if x(0).exists => x(0)
        case x if x(1).exists => x(1)
        case _ => findRemoteGameDir()
      }
      if (!remoteLocation.exists())
        throw new IOException("Could not find remote Steam directory!")
      backupSettings.put("SAVE_FILE_PATH", Paths.get(remoteLocation.getAbsolutePath, "remote").toString)
    }
    backupSettings.save()
  }

  def isStoredLocally(gameSettings: Settings): Boolean = {
    gameSettings.get("DISABLECLOUD").toBoolean
  }

}







