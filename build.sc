import mill._, scalalib._, scalafmt._

object linearreg extends ScalaModule with ScalafmtModule {
  def scalaVersion = "2.12.6"
  def mainClass = Some("linearreg.Main")
  def ivyDeps = Agg(
    ivy"org.scalanlp::breeze:0.13.2",
    ivy"org.scalanlp::breeze-natives:0.13.2",
    ivy"org.scalanlp::breeze-viz:0.13.2",
  )
  object test extends Tests {
    def ivyDeps = Agg(
      ivy"org.scalatest::scalatest:3.0.5",
    )
    def testFrameworks = Seq("org.scalatest.tools.Framework")
  }
}

object linearregdl4j extends ScalaModule with ScalafmtModule {
  def scalaVersion = "2.11.12"
  def mainClass = Some("linearregdl4j.Main")
  def ivyDeps = Agg(
    ivy"org.nd4j:nd4j-native-platform:1.0.0-alpha",
    ivy"org.nd4j::nd4s:1.0.0-alpha",
  )
  object test extends Tests {
    def ivyDeps = Agg(
      ivy"org.scalatest::scalatest:3.0.5",
    )
    def testFrameworks = Seq("org.scalatest.tools.Framework")
  }
}
