package org.dka.tutorial.javafuture

import scala.concurrent.{ExecutionContext, Future}

/**
 * Implementation of a wrapper around a ficticous aws service called SleepTime that uses the blocking aws call
 *
 * @param serviceEC -- exectution context for the service wrapper
 * @param externalEC -- execution context for the blocking aws call
 */
class BlockingAWSService(
                          serviceEC: ExecutionContext,
                          externalEC: ExecutionContext) extends AbstractAwsSleepTimeService with SimpleLogging {

  override def action(sleepTime: SleepTime): Future[Unit] = {
    implicit val internalEC: ExecutionContext = serviceEC
    log(s"action($sleepTime): running with ${serviceEC.toString}")
    val awsRequest: Int = prepareRequest(sleepTime)
    val awsResponse = externalAction(awsRequest)
    awsResponse.map(processResponse)
  }

  /**
   * represents the aws call
   * @param awsRequest (an Int)
   * @return awsResponse (a string)
   */
  private def externalAction(awsRequest: Int): Future[String] = Future {
    // all of our blocking functions ...
    log(s"externalAction($awsRequest) running with ${externalEC.toString}")
    // aws call (in this implementation a blocking call ...
    val response = {
      Thread.sleep(awsRequest)
      awsRequest.toString
    }
    log(s"externalAction completed")
    response
  }(externalEC)
}

object BlockingAWSService {
  def apply(serviceEC: ExecutionContext, externalEC: ExecutionContext) = new BlockingAWSService(serviceEC, externalEC)  // read from config external to creation
}
