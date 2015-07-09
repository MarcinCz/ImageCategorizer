package pl.mczerwi.imagecategorizer

import java.awt.image.BufferedImage
import java.awt.Graphics2D
import scala.swing.Panel
import java.awt.Dimension

/**
 * @author marcin
 */

class ImagePanel extends Panel                                                
{
  private var _image: BufferedImage = null
  
  def image: BufferedImage = null
  def image_=(image: BufferedImage) = {
   _image = image
   repaint()
  }
  
  def size_=(dimension: Dimension) = {
    self.setSize(dimension)
  }

  override def paintComponent(g:Graphics2D) =                                 
  {                                                                           
    if (null != _image) g.drawImage(_image, 0, 0, null)         
  }                                                                           
}                                                                             
