package com.zhang.akka.sparkcommunic.common

/** *
 *
 * @author :  Brian
 *         Date:  2020/11/23
 *         Time: 19:16
 *         Description:
 * */
case class RegisterWorkerInfo(id: String, cpu: Int, ram: Int)

case class WorkerInfo(val id: String, val cpu: Int, val ram: Int) {
  var lastHeartBeat: Long = System.currentTimeMillis()
}

case object RegisteredWorkerInfo

case object SendHeartBeat

case class HeartBeat(id: String)

case object StartTimeOutWorker

case object RemoveTimeOutWorker
