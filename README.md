# sbt-web build base

This is an sbt plugin that sbt-web plugins can use to get their configuration.

[![Build Status](https://travis-ci.org/sbt/sbt-web-build-base.svg?branch=master)](https://travis-ci.org/sbt/sbt-web-build-base) [![Download](https://api.bintray.com/packages/sbt-web/sbt-plugin-releases/sbt-web-build-base/images/download.svg)](https://bintray.com/sbt-web/sbt-plugin-releases/sbt-web-build-base/_latestVersion)

## Usage

Ensure that `project/build.properties` is configured for sbt minimum version of 1.2.1:

```
sbt.version=1.2.1
```

Add the following to `project/plugins.sbt`:

```scala
addSbtPlugin("com.typesafe.sbt" % "sbt-web-build-base" % "1.2.3")
```

Now create a `version.sbt` with the version declared in it, for example:

```scala
version := "1.0.0-SNAPSHOT"
```

And finally, create a `build.sbt` that declares the `name`, `description` and `libraryDependencies` and enables the plugin.  For example:

```scala
lazy val `sbt-project-name` = project in file(".")
enablePlugins(SbtWebBase)

description := "My project description"

libraryDependencies ++= Seq(
  "org.webjars" % "my-web-jar" % "1.2.3
)
```

Generally, no other settings should be needed, all required settings, such as cross building, `organization`, scripted configuration, release and publishing configuration are provided by `sbt-web-build-base`.

## Utilities

A few utility methods are provided:

* `addSbtWeb` and `addSbtJsEngine` - these add dependencies on sbt-web and sbt-js-engine respectively.
