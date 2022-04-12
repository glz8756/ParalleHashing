name := "parallelHashing"

version := "0.1"

scalaVersion := "2.13.8"

val zioVersion = "1.0.10"


libraryDependencies += "org.dispatchhttp"  %% "dispatch-core"   % "1.2.0"
libraryDependencies += "io.circe" %% "circe-core" % "0.13.0"
libraryDependencies += "dev.zio"  %% "zio"   % zioVersion


