package components

import java.awt.geom.Rectangle2D
import java.awt.{AlphaComposite, RenderingHints}
import javax.swing.JComponent

import scala.swing.{Component, Graphics2D}

class GridPanel extends Component{

  val gridSize = 64

  override def paint(g2: Graphics2D): Unit = {
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    g2.setColor(background)
    val shape = new Rectangle2D.Float(0, 0, size.width, size.height)
    g2.fill(shape)

    g2.setColor(foreground)
    val w = size.width
    val h = size.height
    for(i <- 0 until gridSize) {
      val yStep = i * h / gridSize
      g2.drawLine(0, yStep, w, yStep)

      val xStep = i * w / gridSize
      g2.drawLine(xStep, 0, xStep, h)
    }
  }
}
