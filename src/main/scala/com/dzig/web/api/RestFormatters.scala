package com.dzig.web.api

import java.text.SimpleDateFormat

import scala.xml.{Node, NodeSeq, Text}

import net.liftweb.common.{Box,Empty,Failure,Full}
import net.liftweb.mapper.{By,MaxRows}
import net.liftweb.json.JsonAST.{JValue, JObject}
import com.dzig.web.model.CoordinatesTest

/**
 * This object provides some conversion and formatting specific to our
 * REST API.
 */
object RestFormatters {
  /* The REST timestamp format. Not threadsafe, so we create
   * a new one each time. */
  def timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

  // A simple helper to generate the REST ID of an Expense
  def restId (c : CoordinatesTest) =
    "http://www.dzig.com/api/coorditatestest/" + c.id

  // A simple helper to generate the REST timestamp of an Expense
  def restTimestamp (e : CoordinatesTest) : String =
    timestamp.format(e.dateOf.is)

  import net.liftweb.json.Xml
  /**
   * Generates the XML REST representation of an Expense
   */
  def toXML (c : CoordinatesTest) : Node = Xml.toXml(toJSON(c)).head

  /**
   * Generates the JSON REST representation of an Expense
   */
  def toJSON (c : CoordinatesTest) : JValue = {
    import net.liftweb.json.JsonDSL._
    import net.liftweb.json.JsonAST._

    ("coorditateTest" ->
      ("id" -> restId(c)) ~
        ("date" -> restTimestamp(c)) ~
        ("creator" -> c.creator.is) ~
        ("lat" -> c.lat.is) ~
        ("lon" -> c.lon.is) ~
        ("accuracy" -> c.accuracy.is))
  }

}



