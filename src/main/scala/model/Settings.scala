package model

import java.io.{PrintWriter, File}

class Settings(private var settings: Map[String, String]) {


  def get(key: String): String =
    this.settings(key)

  def contains(key: String): Boolean =
    this.settings.contains(key)

  def put(key: String, value: String): Unit =
    this.settings += (key -> value)

  def save(): Unit = {
    Settings.settingsLocation.mkdirs()
    val out = new PrintWriter(Settings.settingsFile.getAbsolutePath)
    out.println(this.formatSettingsAsINIString(this.settings))
    out.close()
  }

  def formatSettingsAsINIString(settings: Map[String, String]): String =
    settings.mkString("\n\n").replace("->", "=")
}


object Settings {
  private val settingsLocation = new File(System.getenv("APPDATA"), "DoNotStarveBackup")
  private val settingsFile = new File(settingsLocation, "settings.txt")

  def exists(): Boolean = settingsFile.exists()

  def loadAppSettings(): Settings = {
    loadSettings(settingsFile)
  }

  def loadSettings(settingsFile: File): Settings = {
    val settings = parse(openSettingsFile(settingsFile))
    new Settings(settings)
  }

  def openSettingsFile(settingsFile: File): List[String] =
    scala.io.Source.fromFile(settingsFile).getLines.toList

  def parse(lines: List[String]): Map[String, String] = {
    def toMap(s: String): Map[String,String] = {
      val Array(key, value) = s.trim.split(" = ")
      Map(key -> value)
    }
    def loop(lines: List[String], data: Map[String, String]): Map[String, String] = {
      if (lines.size == 1) data ++ toMap(lines.head)
      else loop(lines.tail, data ++ toMap(lines.head))
    }
    val data = lines.filter(line => !line.contains("[") && !line.isEmpty).toList
    data.foreach(println)
    loop(data, Map[String,String]())
  }

  def createEmptySettings(): Settings = {
    new Settings(Map[String, String]())
  }


}