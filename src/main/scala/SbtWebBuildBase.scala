package com.typesafe.sbt.web.build

import sbt.Keys._
import sbt.{Def, _}

object SbtWebBase extends AutoPlugin {
  override def trigger = noTrigger

  override def requires = ScriptedPlugin

  object autoImport {
    def addSbtJsEngine(version: String): Setting[?] = sbt.addSbtPlugin("com.github.sbt" % "sbt-js-engine" % version)
    def addSbtWeb(version: String): Setting[?] = sbt.addSbtPlugin("com.github.sbt" % "sbt-web" % version)
  }

  override def globalSettings: Seq[Def.Setting[?]] = Seq(
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
    licenses := Seq(License.Apache2),
    sbtPlugin := true,
    scalacOptions ++= Seq("-deprecation", "-feature", "-Werror"),

    crossSbtVersions := Seq("1.12.4"),

    ScriptedPlugin.autoImport.scriptedLaunchOpts ++= Seq(
      "-XX:MaxMetaspaceSize=256m",
      s"-Dproject.version=${version.value}"
    ),
  )

}
