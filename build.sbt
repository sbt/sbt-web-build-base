import buildinfo.BuildInfo._

lazy val `sbt-web-build-base` = project in file(".")

description := "Base build plugin for all sbt-web plugins"

addSbtPlugin("com.github.gseitz" % "sbt-release" % sbtReleaseVersion)
addSbtPlugin("com.jsuereth" % "sbt-pgp" % sbtPgpVersion)
addSbtPlugin("me.lessis" % "bintray-sbt" % bintraySbtVersion)
libraryDependencies += "org.scala-sbt" % "scripted-plugin" % scriptedPluginVersion

addCommandAlias("validate", ";clean;test;scripted")
