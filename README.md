# sbt-web build base

This is an sbt plugin that sbt-web plugins can use to get their configuration.

## Usage

Ensure that `project/build.properties` is configured for sbt 0.13.8:

```
sbt.version=0.13.8
```

Add the following to `project/plugins.sbt`:

```scala
addSbtPlugin("com.typesafe.sbt" % "sbt-web-build-base" % "1.0.0")
```

Now create a `version.sbt` with the version declared in it, for example:

```scala
version := "1.0.0-SNAPSHOT"
```

And finally, create a `build.sbt` that declares the `name`, `description` and `libraryDependencies`.  For example:

```scala
lazy val `sbt-project-name` = project in file(".")

description := "My project description"

libraryDependencies ++= Seq(
  "org.webjars" % "my-web-jar" % "1.2.3
)
```

Generally, no other settings should be needed, all required settings, such as `organization`, scripted configuration, release and publishing configuration are provided by `sbt-web-build-base`.

## Utilities

A few utility methods are provided:

* `addSbtWeb` and `addSbtJsEngine` - these add dependencies on sbt-web and sbt-js-engine respectively.