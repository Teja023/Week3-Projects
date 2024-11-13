package repositories

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import models.Notification
import models.db.NotificationTable



class NotificationRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._


  private val notifications = TableQuery[NotificationTable]

//  def create(notification: Notification): Future[Int] = db.run(notifications += notification)
  def list(): Future[Seq[Notification]] = db.run(notifications.result)
  def getById(id: Long): Future[Option[Notification]] = db.run(notifications.filter(_.notificationId === id).result.headOption)
//  def update(id: String, updatedNotification: Notification): Future[Int] = db.run(notifications.filter(_.notificationId === id).update(updatedNotification))
//  def delete(id: String): Future[Int] = db.run(notifications.filter(_.notificationId === id).delete)
}
