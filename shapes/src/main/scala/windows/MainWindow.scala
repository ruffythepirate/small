package windows

import java.awt.{Color, Toolkit}

import components.GridPanel

import scala.swing.event.MouseClicked
import scala.swing.{BorderPanel, MainFrame}

class MainWindow extends MainFrame {

  val gridPanel = new GridPanel

  gridPanel.background = Color.RED;
  import scala.swing.BorderPanel.Position._

  contents = new BorderPanel {
    layout(gridPanel) = Center
  }

  reactions += {
    case mc : MouseClicked => {

    }
  }

  import javax.swing.WindowConstants.EXIT_ON_CLOSE
  peer.setDefaultCloseOperation(EXIT_ON_CLOSE)

  val frameWidth = 800
  val frameHeight = 600
  val screenSize = Toolkit.getDefaultToolkit().getScreenSize()
  peer.setBounds( (screenSize.getWidth().toInt - frameWidth)/2 , 0, frameWidth, frameHeight)

}
