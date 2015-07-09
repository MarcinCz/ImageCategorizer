package pl.mczerwi.imagecategorizer

import scala.swing.event.Key


/**
 * @author marcin
 */
object ImageCategory extends Enumeration {

  val WATERMARK = new ImageCategoryVal("watermark")
  val NO_WATERMARK = new ImageCategoryVal("noWatermark")
  val UNKOWN = new ImageCategoryVal("unknown")
  
  
  implicit def valueToImageCategoryVal(x: Value) = x.asInstanceOf[ImageCategoryVal] 
  
  case class ImageCategoryVal(folderName: String) extends super.Val 
}