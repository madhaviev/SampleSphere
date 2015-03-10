name := "SampleSphere"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache
)     

scalacOptions += "-target:jvm-1.7"

libraryDependencies += "io.sphere" %% "sphere-play-sdk" % "0.71.0" exclude("org.scala-stm", "scala-stm_2.10.0")  withSources()

play.Project.playJavaSettings
