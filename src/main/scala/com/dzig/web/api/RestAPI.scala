package com.dzig.web.api

import net.liftweb.http.rest.RestHelper
import net.liftweb.http.{BadResponse, ResponseWithReason, LiftResponse, GetRequest}
import net.liftweb.json.JsonAST._
import com.dzig.web.model.CoordinatesTest
import net.liftweb.common.{Failure, Full, Box}
import java.util.Date


object RestAPI extends RestHelper{


  // reacts to the PUT Request
  def addCoordinateTest(coordinate : Box[CoordinatesTest],
                 success : CoordinatesTest => LiftResponse): LiftResponse = coordinate match {
    case Full(coord) => {
      coord.dateOf(new Date())
      coord.validate match {
        case Nil => {
          coord.save
          success(coord)
        }
        case errors => {
          val message = errors.mkString("Validation failed:", ",","")
          ResponseWithReason(BadResponse(), message)
        }
      }
    }
    case Failure(msg, _, _) => {
      ResponseWithReason(BadResponse(), msg)
    }
    case error => {
      BadResponse()
    }
  }
}
