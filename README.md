# sbt-web build base

This is a sbt plugin that sbt-web plugins can use to get their configuration.

[![Build Status](https://github.com/sbt/sbt-web-build-base/actions/workflows/build-test.yml/badge.svg)](https://github.com/sbt/sbt-web-build-base/actions/workflows/build-test.yml)

## Usage

Ensure that `project/build.properties` is configured for sbt minimum version of 1.9.6:

```
sbt.version=1.9.6
```

Add the following to `project/plugins.sbt`:

```scala
addSbtPlugin("com.github.sbt" % "sbt-web-build-base" % "<latest-version>")
```

where `<latest-version>` is the [latest version available](https://github.com/sbt/sbt-web-build-base/tags).

And finally, create a `build.sbt` that declares the `name`, `description` and `libraryDependencies` and enables the plugin.  For example:

```scala
lazy val `sbt-project-name` = project in file(".")
enablePlugins(SbtWebBase)

description := "My project description"

libraryDependencies ++= Seq(
  "org.webjars" % "my-web-jar" % "1.2.3"
)
```

Generally, no other settings should be needed, all required settings, such as cross building, `organization` and scripted configuration are provided by `sbt-web-build-base`.

## Utilities

A few utility methods are provided:

* `addSbtWeb` and `addSbtJsEngine` - these add dependencies on sbt-web and sbt-js-engine respectively.

# Releasing sbt-web build base

1. Tag the release: `git tag -s 1.2.3`
1. Push tag: `git push upstream 1.2.3`
1. GitHub action workflow does the rest: https://github.com/sbt/sbt-web-build-base/actions/workflows/publish.yml
