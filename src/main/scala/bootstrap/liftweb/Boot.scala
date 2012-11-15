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

/**
  * A class that's instantiated early and run.  It allows the application
  * to modify lift's environment
  */
class Boot {
  val logger = Logger(classOf[Boot])

  def boot {
    // where to search snippet
    LiftRules.addToPackages("com.dzig.web")
    
    if (!DB.jndiJdbcConnAvailable_?) DB.defineConnectionManager(DefaultConnectionIdentifier, DBVendor)

    def schemeLogger (msg : => AnyRef) = {
      logger.info(msg)
    }

   // Schemifier.schemify(true, schemeLogger _, ListEntry)

    // Build SiteMap
    val entries = Menu(Loc("Home", List("index"), "Home")) :: Nil

    LiftRules.setSiteMap(SiteMap(entries:_*))
  }
  
  object DBVendor extends ConnectionManager {
  def newConnection(name: ConnectionIdentifier): Box[Connection] = {
    try {
      /** Uncomment if you really want Derby
       * 
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver")
      val dm = DriverManager.getConnection("jdbc:derby:pca_example;create=true")
      */

      Class.forName("org.h2.Driver")
      val dm = DriverManager.getConnection("jdbc:h2:pca_example")
      Full(dm)
    } catch {
      case e : Exception => e.printStackTrace; Empty
    }
  }
  def releaseConnection(conn: Connection) {conn.close}
}

}

