package com.dzig.web.api


import net.liftweb.http._
import net.liftweb.json.JsonAST._
import com.dzig.web.model._
import net.liftweb.common.{Empty, Failure, Full, Box}
import java.util.Date
import net.liftweb.mapper.By
import net.liftweb.http.BadResponse
import net.liftweb.json.JsonAST.JString
import net.liftweb.http.ResponseWithReason
import net.liftweb.util.BasicTypesHelpers.AsLong
import net.liftweb.http.rest._
import xml.{Elem, Node}


object RestAPI extends RestHelper{

  implicit def cvt: JxCvtPF[Any] = {
    //Generic list templates
    case (JsonSelect, c : List[Convertable], _) => JArray(for{item <- c} yield item.toJson)
    case (XmlSelect, c : List[Convertable], _) => <list>{c.mapConserve(f => f.toXml)}</list>

    //Single-items of convertable
    case (JsonSelect, c : Convertable, _) => c.toJson
    case (XmlSelect, c : Convertable, _) => c.toXml
  }



//
  serveJx[Any] {
    case "api":: "coordinates" ::  _ Get _  => listCoordinates
    case "api" :: "coordinates" :: _ Post _ => addCoordinateTest
//
//
  }

  //
  def listCoordinates = S.param("creator") match {
    case Full(c) => Full(CoordinatesTest.findAll(By(CoordinatesTest.creator, c)))
    case _ => Full(CoordinatesTest.findAll())
  }


  def addCoordinateTest : Box[Convertable] =
    for {
      lat <- S.param("lat") ?~ "lat parameter missing" ~> 400
      lon <- S.param("lon") ?~ "lon parameter missing"
      acc <- S.param("accuracy") ?~ "accuracy parameter missing"
      creator <- S.param("creator") ?~ "creator parameter missing"
    } yield {
      val c = CoordinatesTest.create
        .dateOf(new Date)
        .creator(creator)
        .lat(lat.toDouble)
        .lon(lon.toDouble)
        .accuracy(acc.toInt)
      c.saveMe
    }


}


trait Convertable {
  def toXml: Elem
  def toJson: JValue
}