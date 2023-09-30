package org.dka.tutorial.javafuture

import scala.concurrent._


/**
 * Represents the processing of UI requests in the play app
 *
 * this example is just one flow that would be calling an external aws service
 * it demonstrates how to use, mix and match different services
 */
object Main extends App with SimpleLogging {
  import ExecutionConfig._

  // used by the for comprehension,
  // only used for short running non-blocking tasks as per play documentation
  implicit val ec: ExecutionContext = play_EC

  private val blockingService = BlockingAWSService(service_EC, aws_EC )
  private val asyncJavaService = AsyncJavaService(service_EC, aws_ES)

  private def printLog(s: String): Future[Unit] = Future.successful(log(s))

  //
  // when these vals are created, the futures start...
  // the await at the end is just to keep the main from terminating before these vals have completed
  //

  private val runBlocking: Future[Unit] = {
    // with the sleep times here, this is the longest as far as run time is concerned
    for {
      _ <- printLog(s"running blockingService...")
      _ <- blockingService.action(SleepTime(30))
      _ <- blockingService.action(SleepTime(40))
    } yield { log(s"runBlocking yielding")}
  }

  private val runAsyncJava: Future[Unit] = {
    // with the sleep times here, this is the fastest as far as run time is concerned
    for {
      _ <- printLog(s"running asyncJavaService...")
      _ <- asyncJavaService.action(SleepTime(5))
      _ <- asyncJavaService.action(SleepTime(10))
    } yield { log(s"runAsyncJava yielding")}
  }

  private val runBoth: Future[Unit] = {
    // with the sleep times here, this is in the middle as far as run time is concerned
    for {
      _ <- printLog(s"running both...")
      _ <- blockingService.action(SleepTime(15))
      _ <- asyncJavaService.action(SleepTime(20))
    } yield { log(s"runBoth yielding")}
  }

  // the services are already running, we are just waiting for them to complete

  Await.result(runBlocking, timeOut) // fire and forget!
  Await.result(runAsyncJava, timeOut) // fire and forget!
  Await.result(runBoth, timeOut) // fire and forget!


  log(s"all runs: completed")


}
