package bootstrap.liftweb

import _root_.net.liftweb.common._
import _root_.net.liftweb.util._
import _root_.net.liftweb.http._
import _root_.net.liftweb.sitemap._
import _root_.net.liftweb.sitemap.Loc._
import Helpers._
import net.liftweb.db._
import _root_.java.sql._
import net.liftweb.mapper.Schemifier
import com.dzig.web.model.CoordinatesTest
import com.dzig.web.api.RestAPI

/**
  * A class that's instantiated early and run.  It allows the application
  * to modify lift's environment
  */
class Boot {
  val logger = Logger(classOf[Boot])

  DefaultConnectionIdentifier.jndiName = "jdbc/dzigdb"

  def boot {
    // where to search snippet
    LiftRules.addToPackages("com.dzig.web")

    val vendor =
      new StandardDBVendor(Props.get("db.driver") openOr "org.h2.Driver",
        Props.get("db.url") openOr
          "jdbc:h2:lift_proto.db;AUTO_SERVER=TRUE",
        Props.get("db.user"), Props.get("db.password"))

    LiftRules.unloadHooks.append(vendor.closeAllConnections_! _)

    DB.defineConnectionManager(DefaultConnectionIdentifier, vendor)

    if (!DB.jndiJdbcConnAvailable_?) DB.defineConnectionManager(DefaultConnectionIdentifier, vendor)

    def schemeLogger (msg : => AnyRef) = {
      logger.info(msg)
    }

   Schemifier.schemify(true, schemeLogger _, CoordinatesTest)

    // Build SiteMap
    val entries = Menu(Loc("Home", List("index"), "Home")) :: Nil

    LiftRules.setSiteMap(SiteMap(entries:_*))
    LiftRules.statelessDispatchTable.append(RestAPI) // stateless -- no session created
  }
  


}

