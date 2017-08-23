package com.typesafe.sbt.web.build

import bintray.BintrayPlugin
import bintray.BintrayPlugin.autoImport._
import com.typesafe.sbt.SbtPgp
import com.typesafe.sbt.pgp.PgpKeys
import sbt.Keys._
import sbt._
import sbtrelease.ReleasePlugin
import sbtrelease.ReleasePlugin.autoImport._

object SbtWebBase extends AutoPlugin {
  override def trigger = allRequirements
  override def requires = SbtPgp && ReleasePlugin && BintrayPlugin

  /**
   * Work around for https://github.com/sbt/sbt/issues/3393.
   * Public method so that other sbt web plugins can use it.
   */
  def addSbtPlugin(dependency: ModuleID): Setting[Seq[ModuleID]] =
    libraryDependencies += {
      val sbtV = (sbtBinaryVersion in pluginCrossBuild).value
      val scalaV = (scalaBinaryVersion in update).value
      Defaults.sbtPluginExtra(dependency, sbtV, scalaV)
    }

  object autoImport {
    def addSbtJsEngine(version: String): Setting[_] = SbtWebBase.addSbtPlugin("com.typesafe.sbt" % "sbt-js-engine" % version)
    def addSbtWeb(version: String): Setting[_] = SbtWebBase.addSbtPlugin("com.typesafe.sbt" % "sbt-web" % version)
  }

  override def projectSettings = ScriptedPlugin.scriptedSettings ++ Seq(
    // General settings
    organization := "com.typesafe.sbt",
    homepage := Some(url(s"https://github.com/sbt/${name.value}")),
    licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html")),
    sbtPlugin := true,
    scalacOptions ++= Seq("-deprecation", "-feature", "-Xfatal-warnings"),

    crossSbtVersions := Seq("0.13.16", "1.0.0"),

    ScriptedPlugin.scriptedLaunchOpts ++= Seq(
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
