package com.dzig.web.api


import net.liftweb.http._
import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonDSL._
import com.dzig.web.model._
import net.liftweb.common.{Empty, Failure, Full, Box}
import java.util.Date
import net.liftweb.mapper.By
import net.liftweb.http.BadResponse
import net.liftweb.http.ResponseWithReason
import net.liftweb.util.BasicTypesHelpers.AsLong
import net.liftweb.http.rest._
import xml.{Elem, Node}
import java.lang.String
import com.dzig.web.utils.ErrorMessages
import net.liftweb.util.HttpHelpers
import java.net.URLEncoder


object RestAPI extends RestHelper{


  val STATUS_OK = 200
  val STATUS_NOT_FOUND = 404

  def meta(status : Int): JValue = meta (status, Empty)

  def meta(status: Int, message : Box[String]) = status match {
    case STATUS_OK  =>
      (("status" -> status) ~
      ("asOf"   -> RestFormatters.restAsOf))
    case STATUS_NOT_FOUND =>
      (("status" -> status) ~
        ("asOf"   -> RestFormatters.restAsOf)
//        ~ ("error" -> message)
        )

  }


  implicit def cvt: JxCvtPF[Any] = {
    //Generic list templates
    case (JsonSelect, c : List[Convertable], _) => ("meta" -> meta(STATUS_OK) ++  JField("data",JArray(for{item <- c} yield item.toJson)))
    case (XmlSelect, c : List[Convertable], _) => <list>{c.mapConserve(f => f.toXml)}</list>

    //Single-items of convertable
    case (JsonSelect, c : Convertable, _) =>  ("meta" -> meta(STATUS_OK) ++  JField("data",c.toJson))        //TODO: wrap this
    case (XmlSelect, c : Convertable, _) => c.toXml

    case (JsonSelect, c : JValue, _) =>  c

    case (JsonSelect, c : Any, _) =>  meta(STATUS_NOT_FOUND)
  }



//

  serveJx[Any] {
    case "api":: "coordinates" ::  _ Get _  => listCoordinates
    case "api" :: "coordinates" :: _ Post _ => addCoordinateTest
    case "api" :: "user" :: _ Get _ =>  getCurrentUser
    case "api" :: "login" :: _ Get _ =>  doLogin
    case "api" :: "logout" :: _ Post _ => doLogout
    case "api" :: "auth" :: _ Post _ => doAuthentication
//
//
  }


  def doAuthentication =  User.currentUser
    S.param("email") match {
    case Full(c) => /* compare with current user, if current user is different - logout previous and update new. otherwise update user*/
    case _ => /* */
  }


  def doLogin = {
    val authUrl = "http://dzig-gae.appspot.com/_ah/login?auth=" +  S.param("token") +  "&continue=" + URLEncoder.encode("http://dzig-gae.appspot.com/auth", "UTF-8")
    S.redirectTo(authUrl)
  }
  /*
           if token matches user (send redirect to gae server,  - return ok
          else return error message (other user logged in/unable to login
   */

  def doLogout = meta(STATUS_OK)
  /*

   */

  def getCurrentUser = User.currentUser match {
    case Empty => ("meta" -> meta(STATUS_NOT_FOUND, Full(ErrorMessages.MESSAGE_NOT_FOUND)))
    case c => c

  }


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