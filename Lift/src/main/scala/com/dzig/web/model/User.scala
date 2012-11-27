package com.dzig.web.model

import net.liftweb.mapper.{KeyedMetaMapper, MetaMegaProtoUser, ProtoUser}


class User extends ProtoUser[User]{
   def getSingleton = User
}


object User extends User with KeyedMetaMapper[Long, User]{

  override def dbTableName = "users"

}