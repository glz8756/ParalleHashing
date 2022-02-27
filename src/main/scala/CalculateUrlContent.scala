import java.math.BigInteger
import dispatch._
import Defaults._
import java.security._

object CalculateUrlContent {

  def convertToMD5(input: Either[Throwable, Array[Byte]]): String = {
    input match {
      case Right(in) =>
        val md5Str = MessageDigest.getInstance("MD5").digest(in)
        new BigInteger(1, md5Str).toString(16)
      case Left(e) => s"invalidate url ${e.getMessage}"
    }
  }

  def calculateUrlContent(url: String): Future[String] = {
    val svc = dispatch.url(url)
    val response = Http.default(svc OK as.Bytes).either
    response.map(convertToMD5(_))
  }

}
