import java.math.BigInteger
import dispatch._
import Defaults._

import java.security._
import scala.util.{Failure, Success, Try}

object Utils {

  def debug(s: String):Unit = println(s"${Thread.currentThread.getName}: $s")

  def convertToMD5(in: Either[Throwable, Array[Byte]], index: Int): (String, Int) = {
    in match {
      case Right(in) =>
        val md5Str = MessageDigest.getInstance("MD5").digest(in)
        (new BigInteger(1, md5Str).toString(16), index)
      case Left(e) => (s"invalid url ${e.getMessage}", -1)
    }
  }

  def calculateUrlContent(url: String, index: Int): Future[(String, Int)] = {
    debug(s"$url#$index")
    Thread.sleep(1000)
    val svc = dispatch.url(url)
    val response = Http.default(svc OK as.Bytes).either
    response.map(x => convertToMD5(x, index))
  }

  def load(path: String): Iterator[String] = {
    val result = Try(scala.io.Source.fromFile(path)) match {
      case Success(source) =>
        Some(source.getLines())
      case Failure(exception) =>
        println(s"Failed to read file '$path' - Reason: $exception")
        None
    }
    result.getOrElse(Iterator.empty)
  }


}
