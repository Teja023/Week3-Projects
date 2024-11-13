//package controllers
//
//import javax.inject._
//import play.api.mvc._
//import services.KafkaProducerService
//
//@Singleton
//class NotificationController @Inject()(cc: ControllerComponents, kafkaProducerService: KafkaProducerService) extends AbstractController(cc) {
//
//  def sendNotification(message: String): Action[AnyContent] = Action {
//    kafkaProducerService.sendToKafka(message)
//    Ok("Notification sent to Kafka.")
//  }
//}
