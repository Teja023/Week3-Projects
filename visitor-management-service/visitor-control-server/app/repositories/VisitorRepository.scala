package repositories

import models.Visitor
import models.db.VisitorTable
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}


class VisitorRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._

  private val visitors = TableQuery[VisitorTable]

  def create(visitor: Visitor): Future[Long] = {
    val insertQueryThenReturnId = visitors
      .map(v => (v.name, v.hostName, v.hostMail, v.building, v.email, v.contactNumber, v.checkInTime, v.status))
      .returning(visitors.map(_.visitorId))  // Ensure this returns a Long value

    // Execute the query and return the inserted visitor's ID
    db.run(insertQueryThenReturnId += (
      visitor.name,
      visitor.hostName,
      visitor.hostMail,
      visitor.building,
      visitor.email,
      visitor.contactNumber,
      visitor.checkInTime,
      visitor.status
    )).map(_.head)  // Extract the first element from the result (the ID)
  }

  def list(): Future[Seq[Visitor]] = db.run(visitors.result)

  def getById(id: Long): Future[Option[Visitor]] = db.run(visitors.filter(_.visitorId === id).result.headOption)

  // Update the status of a visitor
  def updateVisitorStatus(visitorId: Long, newStatus: String): Future[Option[Visitor]] = {
    val query = visitors.filter(_.visitorId === visitorId).map(_.status).update(newStatus)

    db.run(query).flatMap { _ =>
      // After updating, fetch the updated visitor to return it
      db.run(visitors.filter(_.visitorId === visitorId).result.headOption)
    }
  }

  def updateCheckOut(visitorId: Long): Future[Option[Visitor]] = {
    val currentTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)  // Current time for checkOutTime
    val defaultStatus = "Checked-Out"  // Default status
    // Construct the update query
    val updateQuery = visitors.filter(visitor => visitor.visitorId === visitorId && visitor.status === "Approved")
      .map(v => (v.checkOutTime, v.status))
      .update((Some(currentTime), defaultStatus))  // Set checkOutTime to current time and status to "Checked Out"

    // Execute the update and handle the result
    db.run(updateQuery).flatMap {
      case 0 => Future.successful(None)  // No rows updated, return None
      case _ =>
        println("Prints This")
        // After updating, fetch the updated visitor to return it
        db.run(visitors.filter(_.visitorId === visitorId).result.headOption)
    }
  }
}

