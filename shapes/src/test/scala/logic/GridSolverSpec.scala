package logic

import logic.GridSolver.Coord
import org.scalatest.FunSpec

class GridSolverSpec extends FunSpec {

  describe("Find non empty") {

    val taken2x2 = Seq(Coord(0, 0), Coord(0, 1), Coord(1, 0), Coord(1, 1))
    taken2x2.foreach(c => {
      it(s"Verify that $c is taken for 2x2") {
        val board = Array.ofDim[Int](2, 2)
        board(c.x)(c.y) = 1

        val notEmpty = GridSolver.findNonEmpty(0, 0, 2, 2, board)

        assert(notEmpty === Some(c))
      }
    })

    val taken4x4 = Seq(Coord(0, 0), Coord(0, 2), Coord(2, 0), Coord(2, 2))
    taken4x4.foreach(c => {
      it(s"Verify that $c is taken for 4x4") {
        val board = Array.ofDim[Int](4, 4)
        board(c.x)(c.y) = 1

        val notEmpty = GridSolver.findNonEmpty(0, 0, 4, 4, board)

        assert(notEmpty === Some(c))
      }
    })

  }

  case class WallsTestInputOutput(input: Coord, result: Array[Array[Int]])

  describe("Placing walls 2x2") {
    val taken2x2 = Seq(Coord(0, 0), Coord(1, 0), Coord(0, 1), Coord(1, 1))
    val output2x2 = Seq(
      Array(
        Array(1, 7),
        Array(11, 12)
      ),
      Array(
        Array(7, 1),
        Array(10, 13)
      ),
      Array(
        Array(11, 5),
        Array(1, 14)
      ),
      Array(
        Array(3, 13),
        Array(14, 1)
      )).map(a => a.transpose)

    val testData = taken2x2.zip(output2x2)

    testData.foreach(td => {
      it(s"Can place walls in 2x2 case taken ${td._1}") {
        val c = td._1

        val board = Array.ofDim[Int](2, 2)
        board(c.x)(c.y) = 1
        GridSolver.populateGrid(0, 0, 2, 2, board)

        val answer = td._2
        for (i <- 0 until board.size; j <- 0 until board(i).size) {
          assert(board(i)(j) === answer(i)(j))
        }
      }
    })
  }

  describe("Finding the right sub groups") {

  }

  describe("Placing walls all over") {
    Seq(2, 4, 8, 16).foreach(
      size =>
        it(s"doesn't leave any empty places in a ${size}x$size grid.") {
          val board = Array.ofDim[Int](size, size)
          board(size / 2)(size / 2) = 15
          GridSolver.populateGrid(0, 0, size, size, board)

          for (i <- 0 until board.size; j <- 0 until board(i).size) {
            assert(board(i)(j) !== 0, s"board empty at $i, $j")
          }
        }
    )

  }

  describe("Placing walls 4x4") {
    val taken4x4 = Seq(Coord(0, 0))
    val output4x4 = Seq(
      Array(
        Array(1, 7, 11, 5),
        Array(11, 12, 7, 14),
        Array(7, 11, 12, 7),
        Array(10, 13, 11, 12)
      )).map(a => a.transpose)

    val testData = taken4x4.zip(output4x4)

    testData.foreach(td => {
      it(s"Can place walls in 4x4 case taken ${td._1}") {
        val c = td._1

        val board = Array.ofDim[Int](4, 4)
        board(c.x)(c.y) = 1
        GridSolver.populateGrid(0, 0, 4, 4, board)

        val answer = td._2
        for (i <- 0 until board.size; j <- 0 until board(i).size) {
          assert(board(i)(j) === answer(i)(j), s"when checking coord ($i, $j)")
        }
      }
    })
  }
}
