import buildinfo.BuildInfo._

lazy val `sbt-web-build-base` = project in file(".")
enablePlugins(SbtWebBase)

sonatypeProfileName := "com.github.sbt.sbt-web-build-base" // See https://issues.sonatype.org/browse/OSSRH-77819#comment-1203625

description := "Base build plugin for all sbt-web plugins"

libraryDependencies += "org.scala-sbt" %% "scripted-plugin" % scriptedPluginVersion

crossSbtVersions := Seq("1.9.0-RC3")

// Customise sbt-dynver's behaviour to make it work with tags which aren't v-prefixed
ThisBuild / dynverVTagPrefix := false

// Sanity-check: assert that version comes from a tag (e.g. not a too-shallow clone)
// https://github.com/dwijnand/sbt-dynver/#sanity-checking-the-version
Global / onLoad := (Global / onLoad).value.andThen { s =>
  dynverAssertTagVersion.value
  s
}

addCommandAlias("validate", ";clean;test;^scripted")
