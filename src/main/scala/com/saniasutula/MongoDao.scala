package com.saniasutula

import com.saniasutula.utils.ConfigUtils
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.{Cursor, DefaultDB, MongoConnection, MongoDriver}
import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, Macros}

import scala.concurrent.Future

case class User(email: String, password: String)

object MongoDao {
  import scala.concurrent.ExecutionContext.Implicits.global

  private val driver = MongoDriver()

  private val database: Future[DefaultDB] =
    for {
      uri <- Future.fromTry(MongoConnection.parseURI(ConfigUtils.databaseUri))
      con = driver.connection(uri)
      dn <- Future(uri.db.get)
      db <- con.database(dn)
    } yield db

  private val userCollection: Future[BSONCollection] = database.map(_.collection("wcusers"))

  private implicit def personWriter: BSONDocumentWriter[User] = Macros.writer[User]

  def createUser(user: User): Future[WriteResult] =
    userCollection.flatMap(_.insert(user))

  private implicit def personReader: BSONDocumentReader[User] = Macros.reader[User]

  def findUserByEmail(email: String): Future[List[User]] = {
    userCollection.flatMap(_.find(BSONDocument("email" -> email))
      .cursor[User]()
      .collect[List](1, Cursor.FailOnError[List[User]]())
    )
  }
}