TaskKey[Unit]("touchFile") := {
  IO.write(file(sys.props("scripted-file")), "ran")
}

ThisBuild / scalaVersion := {
  if (sbtVersion.value.startsWith("1.")) "2.12.21" else "3.9.0"
}
