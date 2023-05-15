import buildinfo.BuildInfo._

lazy val `sbt-web-build-base` = project in file(".")
enablePlugins(SbtWebBase)

description := "Base build plugin for all sbt-web plugins"

libraryDependencies += "org.scala-sbt" %% "scripted-plugin" % scriptedPluginVersion

crossSbtVersions := Seq("1.9.0-RC3")

addCommandAlias("validate", ";clean;test;^scripted")
