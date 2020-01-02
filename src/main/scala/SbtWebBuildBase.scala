package com.typesafe.sbt.web.build

import bintray.BintrayPlugin
import bintray.BintrayPlugin.autoImport._
import com.jsuereth.sbtpgp.SbtPgp
import sbt.Keys._
import sbt.{Def, _}
import sbtrelease.ReleasePlugin
import sbtrelease.ReleasePlugin.autoImport._

object SbtWebBase extends AutoPlugin {
  override def trigger = noTrigger

  override def requires = SbtPgp && ReleasePlugin && BintrayPlugin && ScriptedPlugin

  @deprecated("No longer needed since sbt 1.0.1 has been released.", "1.2.0")
  def addSbtPlugin(dependency: ModuleID): Setting[Seq[ModuleID]] = sbt.addSbtPlugin(dependency)

  object autoImport {
    def addSbtJsEngine(version: String): Setting[_] = sbt.addSbtPlugin("com.typesafe.sbt" % "sbt-js-engine" % version)
    def addSbtWeb(version: String): Setting[_] = sbt.addSbtPlugin("com.typesafe.sbt" % "sbt-web" % version)
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
    organization := "com.typesafe.sbt",
    homepage := Some(url(s"https://github.com/sbt/${name.value}")),
    licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html")),
    sbtPlugin := true,
    scalacOptions ++= Seq("-deprecation", "-feature", "-Xfatal-warnings"),

    // Do not switch to sbt 1.3.x (or greater) as long as Play is build with sbt 1.2.x
    // See https://github.com/sbt/sbt/issues/5049
    crossSbtVersions := Seq("0.13.18", "1.2.8"),

    ScriptedPlugin.autoImport.scriptedLaunchOpts ++= Seq(
      "-XX:MaxMetaspaceSize=256m",
      s"-Dproject.version=${version.value}"
    ),

    // Publish settings
    publishMavenStyle := false,
    bintrayOrganization := Some("sbt-web"),
    bintrayRepository := "sbt-plugin-releases",
    bintrayPackage := name.value,
    bintrayReleaseOnPublish := false,

    // Release settings
    releaseTagName := (version in ThisBuild).value,
    releaseProcess := {
      import ReleaseTransformations._

      Seq[ReleaseStep](
        checkSnapshotDependencies,
        inquireVersions,
        releaseStepCommandAndRemaining("^test"),
        releaseStepCommandAndRemaining("^scripted"),
        setReleaseVersion,
        commitReleaseVersion,
        tagRelease,
        releaseStepCommandAndRemaining("^publishSigned"),
        releaseStepTask(bintrayRelease in thisProjectRef.value),
        setNextVersion,
        commitNextVersion,
        pushChanges
      )
    }

  )

}
