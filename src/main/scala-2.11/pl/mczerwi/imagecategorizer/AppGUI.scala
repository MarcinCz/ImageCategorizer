package pl.mczerwi.imagecategorizer

import java.awt.Dimension
import java.io.File

import scala.swing.BorderPanel
import scala.swing.Label
import scala.swing.MainFrame
import scala.swing.SimpleSwingApplication
import scala.swing.event.Key
import scala.swing.event.KeyPressed

import org.slf4j.LoggerFactory

import com.typesafe.scalalogging.Logger

import javax.imageio.ImageIO

object AppGUI extends SimpleSwingApplication {

  private val logger = Logger(LoggerFactory.getLogger(getClass.getName))

  private[this] var imageIterator: ImagesInDirIterator = null
  private[this] var currentImage: ImageWithName = null
  
  override def main(args: Array[String]): Unit = {
    if (args.length < 1) {
      logger.error("You have to specify folder name")
    } else {
      val dir = args(0)
    	imageIterator = new ImagesInDirIterator(dir)
      ImageCategory.values.foreach { cat => new File(dir + "\\" + cat.folderName).mkdir() }
      super.main(args)      
    }
  }

  def top = new MainFrame {
    val helpLabel = new Label {
      text = "Press: 1 - watermark || 2 - no-watermark || 3 - unkown"
    }
    val imagePanel: ImagePanel = new ImagePanel;
    val imageNameLabel = new Label

    contents = new BorderPanel {
      layout(imageNameLabel) = BorderPanel.Position.North
      layout(imagePanel) = BorderPanel.Position.Center
      layout(helpLabel) = BorderPanel.Position.South
      
      listenTo(keys)
      reactions += {
        case KeyPressed(_, Key.Key1, _, _) => moveImageToFolder(ImageCategory.WATERMARK.folderName)
        case KeyPressed(_, Key.Key2, _, _) => moveImageToFolder(ImageCategory.NO_WATERMARK.folderName)
        case KeyPressed(_, Key.Key3, _, _) => moveImageToFolder(ImageCategory.UNKOWN.folderName)
        case _ =>
      }
      focusable = true
      requestFocus
    }

    def loadNextImage() {
      if(imageIterator.hasNext()) {
        val image = imageIterator.next()
    		imageNameLabel.text = image.name
        imagePanel.image = image.image
    		size = new Dimension(image.image.getWidth + 20, image.image.getHeight + 80)
        currentImage = image
      }
    }

    def moveImageToFolder(folderName: String) {
      if(currentImage != null) {
        val file = new File(imageIterator.dir + "\\" + folderName, currentImage.name)
        if(currentImage.name.endsWith("jpg")) {
        	ImageIO.write(currentImage.image, "jpg", file);
        } else {
          ImageIO.write(currentImage.image, "png", file);
        }
        logger.info(s"Image ${currentImage.name} moved to ${file.getAbsolutePath}")
      }
      loadNextImage()
    }
    
    loadNextImage()
  }

}