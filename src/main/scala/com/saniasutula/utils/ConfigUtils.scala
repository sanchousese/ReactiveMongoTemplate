package com.saniasutula.utils

import com.typesafe.config.ConfigFactory

object ConfigUtils {
  val mongodbConfig = ConfigFactory.load().getConfig("mongodb")
  val login         = mongodbConfig.getString("login")
  val password      = mongodbConfig.getString("password")
  val clusterName   = mongodbConfig.getString("clusterName")
  val port          = mongodbConfig.getString("port")
  val dbName        = mongodbConfig.getString("dbName")

  val databaseUri   = s"mongodb://$login:$password@$clusterName:$port/$dbName?authMode=scram-sha1"
}
