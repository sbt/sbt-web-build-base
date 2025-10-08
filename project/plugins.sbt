import java.util.Locale

lazy val build = (project in file(".")).
  enablePlugins(BuildInfoPlugin).
  settings(
    buildInfoKeys := libraryDependencies.value.map { module =>
      val key = "-([a-z])".r.replaceAllIn(module.name, matched => matched.group(1).toUpperCase(Locale.ENGLISH)) + "Version"
      (key -> module.revision): BuildInfoKey
    }
  )

(Compile / unmanagedSourceDirectories) += baseDirectory.value.getParentFile / "src" / "main" / "scala"

addSbtPlugin("com.github.sbt" % "sbt-ci-release" % "1.11.2")
