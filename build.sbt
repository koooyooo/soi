
lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging, AshScriptPlugin, DockerPlugin)
  .settings(
    name := "soi",
    version := "0.1",
    scalaVersion := "2.12.4",
    libraryDependencies += "com.typesafe.akka" %% "akka-http"   % "10.1.5",
    libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.12",
    libraryDependencies += "com.typesafe.play" %% "play-json"   % "2.6.10",
    mainClass in assembly := Some("soy.soi.Main"),

    dockerBaseImage := "java:8-jdk-alpine",
    executableScriptName := "soi"
)