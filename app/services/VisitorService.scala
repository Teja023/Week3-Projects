package services

import javax.inject._
import models.Visitor
import repositories.VisitorRepository

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class VisitorService @Inject()(VisitorRepository: VisitorRepository) {

  private var visitors: List[Visitor] = List()

  def checkIn(visitorData: Visitor): Future[Long] = {
    VisitorRepository.create(visitorData)
  }

  def checkOut(visitorId: Long): Future[Boolean] = VisitorRepository.updateCheckOut(visitorId)

  def list(): Future[Seq[Visitor]] = VisitorRepository.list()

  def get(id: Long): Future[Option[Visitor]] = VisitorRepository.getById(id)


//  def update(id: Long, Visitor: Visitor): Future[Option[Visitor]] =
//    VisitorRepository.update(id, Visitor)
//
//  def delete(id: Long): Future[Boolean] = VisitorRepository.delete(id)
}
