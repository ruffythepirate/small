package components

import java.awt.geom.Rectangle2D
import java.awt.{AlphaComposite, Color, RenderingHints}
import javax.swing.JComponent

import logic.GridSolver

import scala.swing.event.{MouseClicked, MouseEntered, MousePressed}
import scala.swing.{Component, Graphics2D}

class GridPanel extends Component{

  val gridSize = 64

  var board : Option[Array[Array[Int]]] = _

  listenTo(mouse.clicks)

  reactions += {
    case mc : MouseClicked =>
      val c = getClickCoord(mc)
      board = Some(generatePlayField(c))
      repaint()
  }

  def generatePlayField(takenPoint : (Int, Int)) : Array[Array[Int]] = {
    val board = Array.ofDim[Int](gridSize, gridSize)
    board(takenPoint._1)(takenPoint._2) = 15

    GridSolver.populateGrid(0, 0, gridSize, gridSize, board)

    board
  }

  def getClickCoord (mc:MouseClicked) = {
    val w = size.width
    val h = size.height
    val xCoord = mc.point.x * gridSize / w
    val yCoord = mc.point.y * gridSize / h
    (xCoord, yCoord)
  }

  override def paint(g2: Graphics2D): Unit = {
    paintBackground(g2)
    paintGrid(g2)
    paintBoard(g2)
  }

  def paintBoard(g2: Graphics2D): Unit = {
    board match {
      case Some(board) =>
        val w = size.width
        val h = size.height

        g2.setColor(foreground)

        for (i <- 0 until board.size; j <- 0 until board(i).size) {
          if (board(i)(j) != 0) {
            val cValue = board(i)(j)
            if (cValue != 0) {
              drawWall(g2, i, j, cValue & 0x1 )
              drawWall(g2, i, j, cValue & 0x2 )
              drawWall(g2, i, j, cValue & 0x4 )
              drawWall(g2, i, j, cValue & 0x8 )
              //g2.fillRect(i * w / gridSize, j * h / gridSize, w / gridSize, h / gridSize)
            }
          }
        }
      case _ =>
    }
  }

  def cellWidth = size.width / gridSize

  def cellHeight = size.height / gridSize

  def drawHorizontalLine(g2 : Graphics2D, x:Int, y: Int): Unit = {
    g2.drawLine(cellWidth * x, cellHeight * y, cellWidth * (x+1), cellHeight * y)
  }

  def drawVerticalLine(g2 : Graphics2D, x:Int, y: Int): Unit = {
    g2.drawLine(cellWidth * x, cellHeight * y, cellWidth * x, cellHeight * (y+1))
  }

  def drawWall(g2 : Graphics2D, x: Int, y: Int, wall: Int): Unit = {
    if(wall == 1) {
      drawHorizontalLine(g2, x, y)
    } else if(wall == 2) {
      drawVerticalLine(g2, x, y)
    } else if(wall == 4) {
      drawVerticalLine(g2, x+1, y)
    } else if(wall == 8) {
      drawHorizontalLine(g2, x, y+1)
    }
  }


  def paintGrid(g2: Graphics2D): Unit = {
    val w = size.width
    val h = size.height
    val transparent = new Color(foreground.getRed, foreground.getGreen, foreground.getBlue, 24)
    g2.setColor(transparent)
    for (i <- 0 until gridSize) {
      val yStep = i * h / gridSize
      g2.drawLine(0, yStep, w, yStep)

      val xStep = i * w / gridSize
      g2.drawLine(xStep, 0, xStep, h)
    }
  }

  def paintBackground(g2: Graphics2D): Unit = {
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    g2.setColor(background)
    val shape = new Rectangle2D.Float(0, 0, size.width, size.height)
    g2.fill(shape)

  }
}
