import java.util.Locale

libraryDependencies += "org.scala-sbt" %% "scripted-plugin" % sbtVersion.value

addSbtPlugin("com.github.sbt" % "sbt-release" % "1.1.0")
addSbtPlugin("io.crashbox" % "sbt-gpg" % "0.2.1")
addSbtPlugin("org.foundweekends" % "sbt-bintray" % "0.6.1")

lazy val build = (project in file(".")).
  enablePlugins(BuildInfoPlugin).
  settings(
    buildInfoKeys := libraryDependencies.value.map { module =>
      val key = "-([a-z])".r.replaceAllIn(module.name, matched => matched.group(1).toUpperCase(Locale.ENGLISH)) + "Version"
      (key -> module.revision): BuildInfoKey
    }
  )

(Compile / unmanagedSourceDirectories) += baseDirectory.value.getParentFile / "src" / "main" / "scala"
