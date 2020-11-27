package com.zhang.akka.sparkcommunic.worker

import akka.actor.{Actor, ActorRef, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import com.zhang.akka.sparkcommunic.common.{HeartBeat, RegisterWorkerInfo, RegisteredWorkerInfo, SendHeartBeat}
import scala.concurrent.duration._

import scala.language.postfixOps

/** *
 *
 * @author :  Brian
 *         Date:  2020/11/23
 *         Time: 19:08
 *         Description:
 **/
class SparkWorker(masterHost: String, masterPort: Int, masterName: String) extends Actor {

  var masterProxy: ActorSelection = _

  var id = java.util.UUID.randomUUID().toString


  override def preStart(): Unit = {
    masterProxy = context.actorSelection(s"akka.tcp://SparkMaster@$masterHost:$masterPort/user/$masterName")
    println("masterProxy=" + masterProxy)
  }

  override def receive: Receive = {

    case "start" => {
      println("worker启动了")
      masterProxy ! RegisterWorkerInfo(id, 16, 16 * 1024)
    }
    case RegisteredWorkerInfo => {
      println("workerId=" + id + "注册成功~")

      import context.dispatcher

      context.system.scheduler.schedule(0 millis, 3000 millis, self, SendHeartBeat)

    }
    case SendHeartBeat => {
      println("worker=" + id + "给master发送心跳")
      masterProxy ! HeartBeat(id)
    }


  }
}

object SparkWorker {
  def main(args: Array[String]): Unit = {
    if (args.length != 6) {
      println("请输入参数workerHost workerPort workerName masterHost masterPort masterName")
      sys.exit()
    }

    val workerHost = args(0)
    val workerPort = args(1).toInt
    val workerName = args(2)
    val masterHost = args(3)
    val masterPort = args(4).toInt
    val masterName = args(5)
    val config = ConfigFactory.parseString(
      s"""
         |akka.actor.provider="akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname=$workerHost
         |akka.remote.netty.tcp.port=$workerPort
         |""".stripMargin
    )

    val sparkWorkerSystem: ActorSystem = ActorSystem("SparkWorker", config)
    val sparkWorkerRef: ActorRef = sparkWorkerSystem.actorOf(Props(new SparkWorker(masterHost, masterPort, masterName)), workerName)

    sparkWorkerRef ! "start"

  }


}
