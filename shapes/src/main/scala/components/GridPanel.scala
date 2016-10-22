package components

import java.awt.geom.Rectangle2D
import java.awt.{AlphaComposite, RenderingHints}
import javax.swing.JComponent

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

        for (i <- 0 until board.size; j <- 0 until board(i).size) {
          if (board(i)(j) != 0) {
            val cValue = board(i)(j)
            if (cValue != 0) {
              g2.setColor(foreground)
              g2.fillRect(i * w / gridSize, j * h / gridSize, w / gridSize, h / gridSize)
            }
          }
        }
      case _ =>
    }
  }

  def paintGrid(g2: Graphics2D): Unit = {
    val w = size.width
    val h = size.height
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

    g2.setColor(foreground)
  }
}
