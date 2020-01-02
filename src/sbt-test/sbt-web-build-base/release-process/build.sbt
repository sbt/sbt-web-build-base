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

PgpKeys.publishSigned := {
  IO.write(target.value / s"publish-version-${(sbtBinaryVersion in pluginCrossBuild).value}", version.value)
}

bintrayRelease := {
  IO.write(target.value / "bintray-release-version", version.value)
}

// Pass the file for the scripted test to write to so that we can check that it ran
scriptedLaunchOpts += s"-Dscripted-file=${target.value / s"scripted-ran-${(sbtBinaryVersion in pluginCrossBuild).value}"}"

bintrayCredentialsFile := baseDirectory.value / "bintray.credentials"

// prevent failed build due to auto-generated project/build.properties
releaseIgnoreUntrackedFiles := true
