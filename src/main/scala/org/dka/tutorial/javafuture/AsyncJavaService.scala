package org.dka.tutorial.javafuture

import scala.concurrent.{ExecutionContext, Future}
import java.util.concurrent.{CompletableFuture, ExecutorService => JavaExecutorService}
import scala.jdk.FutureConverters._


/**
 * Implementation of a wrapper around a ficticous aws service called SleepTime that uses the async aws call
 *
 * @param serviceEC  -- execution context for the service wrapper
 * @param externalES -- java ExecutorService for the aws call
 */
class AsyncJavaService(serviceEC: ExecutionContext,
                       externalES: JavaExecutorService) extends AbstractAwsSleepTimeService with SimpleLogging {

  override def action(sleepTime: SleepTime): Future[Unit] = {
    implicit val internalEC: ExecutionContext = serviceEC
    log(s"action($sleepTime): running with ${serviceEC.toString}")
    val awsRequest: Int = prepareRequest(sleepTime)
    val awsResponse:Future[String] = externalAction(awsRequest)(externalES).asScala
    awsResponse.map(processResponse)
  }

  private def externalAction(awsRequest: Int)(es: JavaExecutorService): CompletableFuture[String] =
    CompletableFuture.supplyAsync(() => {
    log(s"externalAction($awsRequest) running with ${es.toString}")
    val response = {
      Thread.sleep(awsRequest)
      awsRequest.toString
    }
    log(s"externalAction compeleted")
    response
  }, es)

}

object AsyncJavaService {
  def apply(serviceEC: ExecutionContext, externalES: JavaExecutorService) =
    new AsyncJavaService(serviceEC, externalES)
}
