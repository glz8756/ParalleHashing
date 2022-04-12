import Utils._
import zio.{ Runtime, Task,  ZIO}

object ParallelHashing extends App {

    if (args.length < 2) {
      println("insufficient arguments")
      System.exit(0)
    }

     val filename = args(0)

     val numberOfParallel = args(1).toInt

    val urlLists = load(filename)

    val md5Result = ZIO.foreachParN(numberOfParallel)(urlLists.toList.zipWithIndex) { url =>
      for {
        md5Str <- Task.fromFuture { implicit ec => calculateUrlContent(url._1, url._2) }
      } yield md5Str
    }

    val runtime = Runtime.default
    runtime.unsafeRun(md5Result).sortWith(_._2 < _._2).map(_._1).foreach(println)


    dispatch.Http.default.shutdown()




}
