package com.dzig.web.model

import net.liftweb.mapper.{MetaMegaProtoUser, MegaProtoUser}
import com.dzig.web.api.{RestFormatters, Convertable}
import net.liftweb.json.JsonDSL._
import net.liftweb.json.JsonAST._

object User extends User with MetaMegaProtoUser[User] {
  override def dbTableName = "users"

  override def skipEmailValidation = true
}

class User extends MegaProtoUser[User] with Convertable{
  def getSingleton = User

  override def toJson =  (
    ("user" -> "fixme"))

  override def toXml =
    <user>
        fixme
    </user>
}
