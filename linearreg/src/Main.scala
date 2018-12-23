package linearreg

import io.Source
import breeze.linalg._
import breeze.plot._
import java.awt.{Color, Paint}
import org.jfree.util.ShapeUtilities

object Main extends App {
  // TODO: implement computeCost
  // computes gradient descent
  def gradDescent(X: DenseMatrix[Double],
                  y: DenseMatrix[Double],
                  theta: DenseMatrix[Double],
                  alpha: Double,
                  iters: Int) = {
    val m = y.toDenseVector.length
    for (_ <- 1 to iters) {
      val h = X.t * theta
      val errors = h - y.t
      val thetaChange = alpha / m * (X * errors)
      theta := theta - thetaChange
    }
    theta
  }

  // load the data
  val reg = "(-?[0-9]*\\.[0-9]+)\\,(-?[0-9]*\\.[0-9]+)*".r
  val raw = Source
    .fromFile("data/ex1data1.txt")
    .getLines()
    .toList
    .flatMap(_ match {
      case reg(x1, y) => Seq((x1.toDouble, y.toDouble))
      case _          => Seq.empty
    })
  val data = DenseMatrix(raw: _*)

  var X = data(::, 0)
  val y = data(::, 1)

  val m = y.length

  // plot it
  // TODO: how to change marker in breeze-viz?
  val f = Figure()
  val p = f.subplot(0)
  val series = scatter(X.toDenseVector, y.toDenseVector, { (_: Int) =>
    .1
  }, { (_: Int) =>
    Color.RED
  })
  p.xlabel = "Population of City in 10,000s"
  p.ylabel = "Profit in $10,000s"
  p += series

  // add a column of 1s to X
  val ones = DenseVector.ones[Double](m)
  val fooX = DenseMatrix(ones, X)
  val alpha = 0.01
  val iters = 1500
  val theta = DenseMatrix.zeros[Double](2, 1)
  val computedTheta = gradDescent(fooX, y.toDenseMatrix, theta, alpha, iters)

  val xx = fooX(1, ::).t
  val xy = fooX.t * computedTheta
  p += plot(xx.toDenseVector, xy.toDenseVector)

  // predict
  println(computedTheta)
  val predict1 = DenseVector(1, 3.5).t * computedTheta
  println(
    f"For population = 35,000, we predict a profit of $$${predict1(0) * 10000}%1.2f")

  val predict2 = DenseVector(1, 7.0).t * computedTheta

  println(
    f"For population = 70,000, we predict a profit of $$${predict2(0) * 10000}%1.2f")
  // TODO: determine why our estimates are slightly off
  // compared to octave version
}
