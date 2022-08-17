package hello.agd

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.Http

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt
import scala.io.StdIn

object Server {

	def start(
	         host: String,
	         port: Int,
	         system: ActorSystem[_],
	         service: proto.Greeter
	         ): Unit = {
		implicit val actorSystem: ActorSystem[_] = system

		implicit val ec: ExecutionContext = actorSystem.executionContext

		val bindingFuture = Http()
			.newServerAt(host, port)
			.bind(proto.GreeterHandler(service))
			.map(_.addToCoordinatedShutdown(3.seconds))

		val cmd = s"grpcurl -plaintext -import-path ./src/main/protobuf -proto hello.proto localhost:$port hello.Greeter/Greet"

		println(s"Server now online. Try the following command in another terminal\n  $cmd\nPress RETURN to stop...")

		StdIn.readLine() // let it run until user presses return
		bindingFuture
			.flatMap(_.unbind()) // trigger unbinding from the port
			.onComplete(_ => system.terminate()) // and shutdown when done
	}
}