package hello.agd

import scala.concurrent.Future

class GreeterImpl extends proto.Greeter {

	override def greet(in: proto.Empty): Future[proto.Greeting] =
		Future.successful(proto.Greeting("Hola, Mundo!"))

}