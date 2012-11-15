package com.dzig.web.snippet

import xml.{Text, NodeSeq}
import net.liftweb.util.Helpers._
import net.liftweb.http.{DispatchSnippet, SHtml}
import com.dzig.web.model.CoordinatesTest

class Coordinates  extends DispatchSnippet {

  def dispatch : DispatchIt = {
    case "list" => render _
  }

  def render (xhtml : NodeSeq) : NodeSeq = {

    CoordinatesTest.findAll() flatMap ({line =>
        bind("e", chooseTemplate("coords", "entry", xhtml),
          "date" -> line.dateOf.asHtml,
          "creator" -> Text(line.creator.is),
          "coords" -> { Text(line.lat.is.toString) ++ Text(line.lon.is.toString) ++ Text(" (") ++ Text(line.accuracy.is.toString) ++ Text(")")})
      })
  }

}
