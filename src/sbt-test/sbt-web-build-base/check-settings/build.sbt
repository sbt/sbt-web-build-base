lazy val `check-settings` = project in file(".")

def assertEquals[T](t1: T, t2: T): Unit = {
  if (t1 != t2) {
    throw new AssertionError(s"$t1 does not equal $t2")
  }
}

TaskKey[Unit]("testSettings") := {
  assertEquals(homepage.value, Some(url("https://github.com/sbt/check-settings")))
  assertEquals(licenses.value.size, 1)
  assertEquals(licenses.value.head._1, "Apache-2.0")
  assertEquals(bintrayPackage.value, "check-settings")
  assertEquals(releaseVersion.value("1.0"), "1.0")
}

addSbtJsEngine("1.1.1")
addSbtWeb("1.2.0")
