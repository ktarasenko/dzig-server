package com.dzig.web.model

import net.liftweb.mapper._
import java.text.DateFormat
import xml.Text


object CoordinatesTest extends CoordinatesTest with LongKeyedMetaMapper[CoordinatesTest] {
  override def fieldOrder = List(dateOf, creator, lat, lon, accuracy)
}

class CoordinatesTest extends LongKeyedMapper[CoordinatesTest] with IdPK {
  def getSingleton = CoordinatesTest

  object dateOf extends MappedDateTime(this) {
    final val dateFormat =
      DateFormat.getDateInstance(DateFormat.SHORT)
    override def asHtml = Text(dateFormat.format(is))
  }

  object lat extends MappedDouble(this)
  object lon extends MappedDouble(this)
  object accuracy extends MappedInt(this)

  object creator extends MappedString(this,64)

}
