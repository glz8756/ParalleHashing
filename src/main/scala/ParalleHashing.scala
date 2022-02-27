import CalculateUrlContent.calculateUrlContent
import ReadFileFromResource.resourceInto

import java.io.FileNotFoundException
import java.util.concurrent.Executors
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.Duration

object ParalleHashing extends App {

  if (args.length < 2) {
    println("insufficient arguments")
    System.exit(0)
  }

  val filename = args(0)
  val numberOfParalle = args(1).toInt
  val threadPool = Executors.newFixedThreadPool(numberOfParalle)
  implicit val ec = ExecutionContext.fromExecutor(threadPool)

  val urlsData = resourceInto(filename)
  val urlsLines = urlsData match {
    case Right(lines) => lines
    case Left(_) => throw new FileNotFoundException(filename)
  }

  val urlsList = urlsLines.split("\n").toList
  val urlFutures = urlsList.map(u => calculateUrlContent(u))
  val futureParalle = Future.traverse(urlsList)(u => calculateUrlContent(u))

  val md5Result = Await.result(futureParalle, Duration.Inf)
  md5Result.map(x => println(x))

  threadPool.shutdown()
  dispatch.Http.default.shutdown()

}
