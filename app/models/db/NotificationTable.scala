package models.db

import models.Notification
import slick.jdbc.MySQLProfile.api._

class NotificationTable(tag: Tag) extends Table[Notification](tag, "notifications") {
  def notificationId = column[Long]("notification_id", O.PrimaryKey, O.AutoInc)
  def visitorId = column[Long]("visitor_id")
  def email = column[String]("email")
  def notificationType = column[String]("notification_type")
  def timestamp = column[String]("timestamp")

  def * = (notificationId, visitorId, email, notificationType, timestamp) <> ((Notification.apply _).tupled, Notification.unapply)
}
