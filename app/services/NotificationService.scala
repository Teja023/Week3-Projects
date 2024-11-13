package services

import javax.inject._
import models.Notification
import repositories.NotificationRepository

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class NotificationService @Inject()(notificationRepository: NotificationRepository) {

  private var notifications: List[Notification] = List()

  //  def checkIn(visitorData: play.api.libs.json.JsValue): Future[Option[Visitor]] = Future {
  //    val visitor = visitorData.as[Visitor]
  //    visitors = visitors :+ visitor
  //    Some(visitor)
  //  }
  //
  //  def checkOut(visitorId: Long): Future[Boolean] = Future {
  //    visitors.find(_.visitorId.contains(visitorId)) match {
  //      case Some(_) =>
  //        visitors = visitors.filterNot(_.visitorId.contains(visitorId))
  //        true
  //      case None => false
  //    }
  //  }

  def list(): Future[Seq[Notification]] = notificationRepository.list()

  def get(id: Long): Future[Option[Notification]] = notificationRepository.getById(id)

  //  def create(Visitor: Visitor): Future[Long] = VisitorRepository.create(Visitor)

  //  def update(id: Long, Visitor: Visitor): Future[Option[Visitor]] =
  //    VisitorRepository.update(id, Visitor)
  //
  //  def delete(id: Long): Future[Boolean] = VisitorRepository.delete(id)
}
