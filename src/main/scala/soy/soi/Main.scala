package soy.soi

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import play.api.libs.json.Json
import soy.soi.model.{Link, Soi}
import soy.soi.service.SoiService
import scala.concurrent.duration._

import scala.concurrent.{Await, Future}

object Main {
  def main(args: Array[String]): Unit = {
    val soi = Soi("yahoo", Link("https://wwww.yahoo.co.jp"), Nil)
    SoiService.addSoi(soi)

    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val serverSource: Source[Http.IncomingConnection, Future[Http.ServerBinding]] =
      Http().bind(interface = "0.0.0.0", port = 9000)

    val requestHandler: HttpRequest => HttpResponse = {

      case req @ HttpRequest(POST, Uri.Path("/sois"), headers, entity, protocol) =>
        val result = getDataFromEntiy(entity).getOrElse("None")
        HttpResponse(200, entity = s"OK $result")

      case HttpRequest(GET, /*Uri.Path("/")*/ uri, headers, entity, protocol) =>
        HttpResponse(entity = HttpEntity(ContentTypes.`application/json`, Json.obj("Hello" -> "Get").toString()))

      case r: HttpRequest =>
        println("Request found: Others")
        r.discardEntityBytes()
        HttpResponse(404, entity = s"unknown resource! ${r.uri.path}")
    }

    val bindingFuture: Future[Http.ServerBinding] =
      serverSource.to(Sink.foreach { conn =>
        conn handleWith {
          Flow[HttpRequest] map requestHandler
        }
      }).run()
  }

  def getDataFromEntiy(entity: HttpEntity)(implicit mat: ActorMaterializer): Option[String] = {
    val contentFut = entity.dataBytes.map(_.utf8String).runWith(Sink.lastOption)
    Await.result(contentFut, 10.seconds)

  }
}
