package org.dka.tutorial.javafuture

import scala.concurrent.Future

/**
 * represents a service that wraps an aws service, modeled as a fire and forget
 *
 * @tparam T domain object used as input (not the aws object)
 * @tparam I external request object
 * @tparam O external response object
 */
trait Service[T, I, O] {

  /**
   * represents the service from a domain perspective
   * this type of service is a fire and forget
   * all handling of retries, error processing, notification are handled internally to the service
   *
   * @param input domain model input to the service
   * @return nothing, since this is a 'fire and forget' style
   */
  def action(input: T): Future[Unit]

  /**
   * convert the domain input to the wrapped aws service request
   *
   * @param domain domian model linput
   * @return aws request object
   */
  def prepareRequest(domain: T):  I

  /**
   * process the response from aws
   *
   * since this is a fire and forget style, nothing is returned to the code calling the 'action' method
   * all processing of the aws response (logging, notification such as slack channel or other wise) is done in this method
   *
   * @param awsResponse response back from aws
   * @return nothing as this is a fire and forget service
   */
  def processResponse(awsResponse: O): Unit
}


