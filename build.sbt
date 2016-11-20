name := "RemoteDataBaseTest"
organization  := "com.saniasutula"

version := "1.0"

scalaVersion := "2.11.8"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV = "2.3.9"
  val sprayV = "1.3.3"
  val log4jV = "2.7"
  val slf4jV: String = "1.7.21"

  Seq(
    "io.spray"                  %%  "spray-can"           % sprayV,
    "io.spray"                  %%  "spray-routing"       % sprayV,
    "io.spray"                  %%  "spray-testkit"       % sprayV  % "test",
    "com.typesafe.akka"         %%  "akka-actor"          % akkaV,
    "com.typesafe.akka"         %   "akka-slf4j_2.11"     % akkaV,
    "com.typesafe.akka"         %%  "akka-testkit"        % akkaV   % "test",
    "org.specs2"                %%  "specs2-core"         % "2.3.11" % "test",
    "org.reactivemongo"         %   "reactivemongo_2.11"  % "0.12.0",
    "log4j"                     %   "log4j"               % "1.2.17",
    "org.slf4j"                 %   "slf4j-api"           % slf4jV,
    "org.slf4j"                 %   "slf4j-log4j12"       % slf4jV
  )
}

Revolver.settings
    