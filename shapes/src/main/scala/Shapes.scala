import scala.swing.SimpleSwingApplication

object SolveShapes extends SimpleSwingApplication {

  def top = new MainWindow

  /** *
    * Convention
    * first Bit, wall north
    * second bit, wall west
    * third bit, wall east
    * fourth bit, wall south
    */

  case class Coord(x: Int, y: Int)

  def populateGrid(x: Int, y: Int, w: Int, h: Int, board: Array[Array[Int]]): Unit = {

    def populateGrid2x2(coord: Coord): Unit = {
      Coord(coord.x - x, coord.y - y) match {
        case Coord(0, 0) =>
          board(x + 1)(y) = 7
          board(x)(y + 1) = 11
          board(x + 1)(y + 1) = 12
        case Coord(1, 0) =>
          board(x)(y) = 7
          board(x)(y + 1) = 10
          board(x + 1)(y + 1) = 13
        case Coord(0, 1) =>
          board(x)(y) = 11
          board(x + 1)(y) = 5
          board(x + 1)(y + 1) = 14
        case Coord(1, 1) =>
          board(x)(y) = 3
          board(x + 1)(y) = 13
          board(x)(y + 1) = 14
      }
    }
    findNonEmpty(x, y, w, h, board) match {
      case Some(coord) => {
        if (w == 2) {
          populateGrid2x2(coord)
        } else {
          populateGrid(coord.x, coord.y, w/2, h/2, board)
          populateGrid(x + w/2 - 1, y + h/2 -1, 2, 2, board)
          populateGrid(x + compl(coord.x, w/2), y, w/2, h/2, board)
          populateGrid(x, y + compl(coord.y, h/2), w/2, h/2, board)
          populateGrid(x + compl(coord.x, w/2), y + compl(coord.y, h/2), w/2, h/2, board)
        }
      }
      case None =>
        throw new Exception("Unsolvable, no non-empty quadrant found!")
    }

    def compl(i : Int, l : Int): Int = {
      (i + l) % (l*2)
    }
  }

  def findNonEmpty(x: Int, y: Int, w: Int, h: Int, board: Array[Array[Int]]): Option[Coord] = {
    for (i <- 0 to 1; j <- 0 to 1) {
      val newX = x + i * w / 2
      val newY = y + j * h / 2
      if (!isEmpty(newX, newY, w / 2, h / 2, board)) {
        return Some(Coord(newX, newY))
      }
    }
    None
  }

  def isEmpty(x: Int, y: Int, w: Int, h: Int, board: Array[Array[Int]]): Boolean = {
    for (i <- 0 until w; j <- 0 until h) {
      if (board(x + i)(y + j) != 0) {
        return false
      }
    }
    true
  }

}

