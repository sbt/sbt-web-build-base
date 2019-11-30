import buildinfo.BuildInfo._

lazy val `sbt-web-build-base` = project in file(".")
enablePlugins(SbtWebBase)

description := "Base build plugin for all sbt-web plugins"

addSbtPlugin("com.github.gseitz" % "sbt-release" % sbtReleaseVersion)
addSbtPlugin("io.crashbox" % "sbt-gpg" % sbtGpgVersion)
addSbtPlugin("org.foundweekends" % "sbt-bintray" % sbtBintrayVersion)
libraryDependencies += "org.scala-sbt" %% "scripted-plugin" % scriptedPluginVersion

crossSbtVersions := Seq("1.3.4")

addCommandAlias("validate", ";clean;test;scripted")
