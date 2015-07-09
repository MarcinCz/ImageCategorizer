package pl.mczerwi.imagecategorizer

import java.awt.image.BufferedImage
import org.apache.commons.io.filefilter.RegexFileFilter
import org.apache.commons.io.FileUtils
import org.apache.commons.io.filefilter.DirectoryFileFilter
import java.io.File
import org.slf4j.LoggerFactory
import com.typesafe.scalalogging.Logger
import javax.imageio.ImageIO


/**
 * @author marcin
 */
case class ImagesInDirIterator(dir: String) {
  
  private val logger = Logger(LoggerFactory.getLogger(getClass.getName))

  private var counter: Int = 1;
  private val filesIterator = {
    val files = FileUtils.listFiles(new File(dir), Array("png", "jpg"), true)
    logger.info(s"Found ${files.size()} images")
    files.iterator()
  }
  
  def next() = {
    logger.info(s"Reading image #$counter");
    counter += 1;
    val file = filesIterator.next()
    new ImageWithName(ImageIO.read(file), file.getName)
  }
  
  def hasNext() = {
    filesIterator.hasNext()
  }
}

case class ImageWithName(image: BufferedImage, name: String)