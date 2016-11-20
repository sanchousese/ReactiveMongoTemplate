package com.saniasutula

import akka.actor.Actor
import com.saniasutula.utils.ConfigUtils
import org.slf4j.LoggerFactory
import spray.http.MediaTypes._
import spray.routing.HttpService

import scala.util.{Failure, Success}

class MyServiceActor extends Actor with MyService {
  def actorRefFactory = context

  def receive = runRoute(myRoute)
}

trait MyService extends HttpService {

  import scala.concurrent.ExecutionContext.Implicits.global

  private val logger = LoggerFactory.getLogger("MyService")

  val myRoute =
    path("") {
      get {
        respondWithMediaType(`text/plain`) {
          val user: User = User("gogi", "nogi")
          onComplete(MongoDao.createUser(user)) {
            case Failure(e) =>
              logger.error(e.getMessage)
              complete(s"error: ${e.getMessage}")
            case Success(writeResult) =>
              logger.info(writeResult.toString)
              complete(s"User ${user.email} was successfully created")
          }
        }
      }
    } ~ path("users") {
      get {
        respondWithMediaType(`text/plain`) {
          onComplete(MongoDao.findUserByEmail("gogi")) {
            case Failure(e) =>
              logger.error(e.getMessage)
              complete(s"error: ${e.getMessage}")
            case Success(users) =>
              logger.info(users.mkString(", "))
              complete(s"${users.mkString(", ")}")
          }
        }
      }
    }
}
