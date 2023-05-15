lazy val `release-process` = project in file(".")
enablePlugins(SbtWebBase)

InputKey[Unit]("contains") := {
  val args = Def.spaceDelimited().parsed
  val contents = IO.read(file(args.head))
  val expected = args.tail.mkString(" ")
  if (!contents.contains(expected)) {
    throw sys.error(s"File ${args.head} does not contain '$expected':\n$contents")
  }
}

publish := {
  IO.write(target.value / s"publish-version-${(pluginCrossBuild / sbtBinaryVersion).value}", version.value)
}

// Pass the file for the scripted test to write to so that we can check that it ran
scriptedLaunchOpts += s"-Dscripted-file=${target.value / s"scripted-ran-${(pluginCrossBuild / sbtBinaryVersion).value}"}"
