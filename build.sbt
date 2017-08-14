import buildinfo.BuildInfo._

lazy val `sbt-web-build-base` = project in file(".")

description := "Base build plugin for all sbt-web plugins"

addSbtPlugin("com.github.gseitz" % "sbt-release" % sbtReleaseVersion)
addSbtPlugin("com.jsuereth" % "sbt-pgp" % sbtPgpVersion)
addSbtPlugin("org.foundweekends" % "sbt-bintray" % sbtBintrayVersion)
libraryDependencies += "org.scala-sbt" % "scripted-plugin" % scriptedPluginVersion

crossSbtVersions := Seq("0.13.16")

addCommandAlias("validate", ";clean;test;scripted")
