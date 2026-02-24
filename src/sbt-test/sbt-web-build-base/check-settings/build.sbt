lazy val `check-settings` = project in file(".")
enablePlugins(SbtWebBase)
def assertEquals[T](t1: T, t2: T): Unit = {
  if (t1 != t2) {
    throw new AssertionError(s"$t1 does not equal $t2")
  }
}

TaskKey[Unit]("testSettings") := {
  assertEquals(homepage.value, Some(url("https://github.com/sbt/check-settings")))
  assertEquals(licenses.value.size, 1)
}

addSbtJsEngine("1.4.0-M1")
addSbtWeb("1.6.0-M1")
