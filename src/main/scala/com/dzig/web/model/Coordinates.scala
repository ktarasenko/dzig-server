package com.dzig.web.model

import net.liftweb.mapper._
import java.text.DateFormat
import xml.{Elem, Text}
import com.dzig.web.api.Convertable
import com.dzig.web.api.RestFormatters
import net.liftweb.json.Xml
import net.liftweb.json.JsonDSL._
import net.liftweb.json.JsonAST._


object CoordinatesTest extends CoordinatesTest with LongKeyedMetaMapper[CoordinatesTest] {
  override def fieldOrder = List(dateOf, creator, lat, lon, accuracy)
}

class CoordinatesTest extends LongKeyedMapper[CoordinatesTest] with IdPK with Convertable  {
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


  override def toJson =   ("coorditateTest" ->
    ("id" -> RestFormatters.restId(this)) ~
      ("date" -> RestFormatters.restTimestamp(this)) ~
      ("creator" -> this.creator.is) ~
      ("lat" -> this.lat.is) ~
      ("lon" -> this.lon.is) ~
      ("accuracy" -> accuracy.is))
  override def toXml =
    <coordinate>
      <id>{RestFormatters.restId(this)}</id>
      <date>{RestFormatters.restTimestamp(this)}</date>
      <creator>{this.creator.is}</creator>
      <lat>{this.lat.is}</lat>
      <lon>{this.lon.is}</lon>
      <accuracy>{this.accuracy.is}</accuracy>
    </coordinate>
}
