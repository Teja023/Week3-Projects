package models

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Notification(notificationId: Long,
                        visitorId: Long,
                        email: String,
                        notificationType: String,
                        timestamp:String
                       )

object Notification {
  // Combine all the reads
  implicit val notificationReads: Reads[Notification] = (
    (JsPath \ "notificationId").read[Long] and
      (JsPath \ "visitorId").read[Long] and
      (JsPath \ "email").read[String] and
      (JsPath \ "notificationType").read[String] and
      (JsPath \ "timestamp").read[String]
    )(Notification.apply _)

  // Use Json.writes to generate Writes automatically
  implicit val notificationWrites: Writes[Notification] = Json.writes[Notification]

  // Combine Reads and Writes into Format
  implicit val notificationFormat: Format[Notification] = Format(notificationReads, notificationWrites)
}
