package controllers

import models.Visitor
import play.api.mvc._
import services.VisitorService
import play.api.libs.json._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class VisitorController @Inject()(
  val cc: ControllerComponents,
  visitorService: VisitorService
)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def checkInVisitor(): Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[Visitor] match {
      case JsSuccess(visitor, _) =>
        visitorService.checkIn(visitor).map { created =>
          Created(Json.toJson(created))
        }
      case JsError(errors) =>
        Future.successful(BadRequest(Json.obj(
          "message" -> "Invalid visitor data",
          "errors" -> JsError.toJson(errors))))
    }
  }

  def checkOutVisitor(visitorId: Long): Action[AnyContent] = Action.async {
    visitorService.checkOut(visitorId).map {
      case true => Ok("Visitor checked out successfully.")
      case false => InternalServerError("Failed to check out the visitor.")
    }
  }

  def list(): Action[AnyContent] = Action.async{
    visitorService.list().map(visitors => Ok(Json.toJson(visitors)))
  }

  def getVisitorDetails(visitorId: Long): Action[AnyContent] = Action.async{
    visitorService.get(visitorId).map {
      case Some(visitor) => Ok(Json.toJson(visitor))
      case None => NotFound(Json.obj("message" -> s"visitor with id $visitorId not found"))
    }
  }
}
