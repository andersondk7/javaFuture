package org.dka.tutorial.javafuture

import java.util.concurrent.ExecutorService
import scala.concurrent.duration._
import java.util.concurrent.Executors
//import java.util.concurrent.{CompletableFuture, ExecutorService, Executors, Future => JavaFuture}
import scala.concurrent.ExecutionContext
//import scala.jdk.FutureConverters._

object ExecutionConfig {
  //
  // execution contexts, one for each system we communciate with
  //

  // aws java exectutor  --  aws async would be using this one
  val aws_ES: ExecutorService = Executors.newFixedThreadPool(4)

  // the service representing the external system (eventbridge, etc.) would be using these
  val service_EC: ExecutionContext = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(4))
  val aws_EC: ExecutionContext = ExecutionContext.fromExecutor(aws_ES)

  // represents the global context ( created by play ), only used for short running non-blocking tasks as per play documentation
  val play_EC: ExecutionContext = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(4))

  val timeOut: FiniteDuration = 2.seconds

}
