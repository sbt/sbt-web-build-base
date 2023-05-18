package com.typesafe.sbt.web.build

import sbt.Keys._
import sbt.{Def, _}

object SbtWebBase extends AutoPlugin {
  override def trigger = noTrigger

  override def requires = ScriptedPlugin

  object autoImport {
    def addSbtJsEngine(version: String): Setting[_] = sbt.addSbtPlugin("com.github.sbt" % "sbt-js-engine" % version)
    def addSbtWeb(version: String): Setting[_] = sbt.addSbtPlugin("com.github.sbt" % "sbt-web" % version)
  }

  override def globalSettings: Seq[Def.Setting[_]] = Seq(
    sbtVersion := {
      // Validate sbt version since addSbtWeb/JsEngine won't work without sbt 1.0.1.
      val version = sbtVersion.value
      if (version == "1.0.0") {
        sys.error("sbt-web-build-base requires at least sbt version 1.0.1")
      }
      version
    }
  )

  override def projectSettings = Seq(
    // General settings
    organization := "com.github.sbt",
    homepage := Some(url(s"https://github.com/sbt/${name.value}")),
    licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html")),
    sbtPlugin := true,
    scalacOptions ++= Seq("-deprecation", "-feature", "-Xfatal-warnings"),

    crossSbtVersions := Seq("1.9.0-RC3"),

    ScriptedPlugin.autoImport.scriptedLaunchOpts ++= Seq(
      "-XX:MaxMetaspaceSize=256m",
      s"-Dproject.version=${version.value}"
    ),
  )

}
