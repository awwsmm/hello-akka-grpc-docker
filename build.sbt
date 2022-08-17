
ThisBuild / version := "0.0.0"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "hello-akka-grpc-docker",
    idePackagePrefix := Some("hello.agd"),
    assembly / assemblyJarName := "out.jar"
  )

val AkkaVersion = "2.6.19"
val AkkaHttpVersion = "10.2.9"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion
)

enablePlugins(AkkaGrpcPlugin)

ThisBuild / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case PathList("reference.conf") => MergeStrategy.concat
  case other => MergeStrategy.first
}