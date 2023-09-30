package org.dka.tutorial.javafuture

/**
 * Base class for AwsSleepTimeService
 * all implementations of the AwsSleepTimeService wrappers
 * - prepare the aws request from the domain (SleepTime)
 * - process the aws response from aws
 * in the same way
 *
 */
abstract class AbstractAwsSleepTimeService extends Service[SleepTime, Int, String] with SimpleLogging {
  override def prepareRequest(sleepTime: SleepTime): Int = {
    log(s"prepareRequest($sleepTime)")
    sleepTime.timeMS
  }

  override def processResponse(response: String): Unit = {
    // just printing for now...
    log(s"processResponse($response)")
  }
}
