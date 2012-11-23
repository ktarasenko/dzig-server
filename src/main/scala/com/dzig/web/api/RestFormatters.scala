package com.dzig.web.api

import java.text.SimpleDateFormat

import xml.{Elem, Node, NodeSeq, Text}

import net.liftweb.common.{Box,Empty,Failure,Full}
import net.liftweb.mapper.{By,MaxRows}
import net.liftweb.json.JsonAST.{JValue, JObject}
import com.dzig.web.model.CoordinatesTest
import java.util.Date

/**
 * This object provides some conversion and formatting specific to our
 * REST API.
 */
object RestFormatters {
  /* The REST timestamp format. Not threadsafe, so we create
   * a new one each time. */
  def timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

  // A simple helper to generate the REST ID of an Expense
  def restId (c : CoordinatesTest) = c.id.toString

  // A simple helper to generate the REST timestamp of an Expense
  def restTimestamp (e : CoordinatesTest) : String =
    timestamp.format(e.dateOf.is)

  def restAsOf : String = timestamp.format(new Date())


}
