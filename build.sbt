import buildinfo.BuildInfo._

lazy val `sbt-web-build-base` = project in file(".")
enablePlugins(SbtWebBase)

description := "Base build plugin for all sbt-web plugins"

addSbtPlugin("com.github.gseitz" % "sbt-release" % sbtReleaseVersion)
addSbtPlugin("io.crashbox" % "sbt-gpg" % sbtGpgVersion)
addSbtPlugin("org.foundweekends" % "sbt-bintray" % sbtBintrayVersion)
libraryDependencies += "org.scala-sbt" %% "scripted-plugin" % scriptedPluginVersion

// Do not switch to sbt 1.3.x (or greater) as long as Play is build with sbt 1.2.x
// See https://github.com/sbt/sbt/issues/5049
crossSbtVersions := Seq("1.2.8")

addCommandAlias("validate", ";clean;test;^scripted")
