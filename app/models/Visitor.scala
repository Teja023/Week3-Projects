package models

import play.api.libs.functional.syntax._
import play.api.libs.json._
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

case class Visitor(
                    visitorId: Option[Long] = None,
                    name: String,
                    hostName: String,
                    building: String,
                    email: String,
                    contactNumber: String,
                    checkInTime: String = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),  // Default to current time
                    checkOutTime: Option[String] = None,
                    status: String = "Checked In"  // Default to "Checked In"
                  )

object Visitor {
  implicit val visitorReads: Reads[Visitor] = (
    (JsPath \ "visitorId").readNullable[Long] and
      (JsPath \ "name").read[String] and
      (JsPath \ "hostName").read[String] and
      (JsPath \ "building").read[String] and
      (JsPath \ "email").read[String] and
      (JsPath \ "contactNumber").read[String] and
      (JsPath \ "checkInTime").readWithDefault[String](LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)) and
      (JsPath \ "checkOutTime").readNullable[String] and
      (JsPath \ "status").readWithDefault[String]("Checked In")
    )(Visitor.apply _)

  // Use Json.writes to generate Writes automatically
  implicit val visitorWrites: Writes[Visitor] = Json.writes[Visitor]

  // Combine Reads and Writes into Format
  implicit val visitorFormat: Format[Visitor] = Format(visitorReads, visitorWrites)
}
