package org.dka.tutorial.javafuture


trait SimpleLogging {
  private val logName = this.getClass.getSimpleName

  def log(message: String): Unit = println(s"$logName: $message")
}

