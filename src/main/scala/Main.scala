package hello.agd

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success, Try}

object Main {

	def main(args: Array[String]): Unit =

		Try(args(0), args(1).toInt) match {
			case Failure(_) => throw new IllegalArgumentException("usage: <cmd> <host> <port>")
			case Success((host, port)) =>

				implicit val system: ActorSystem[Nothing] =
					ActorSystem[Nothing](Behaviors.empty[Nothing], "actor-system")

				implicit val ec: ExecutionContext = system.executionContext

				val grpcService = new GreeterImpl

				println(s"Starting server at $host:$port...")
				Server.start(host, port, system, grpcService)
		}

}