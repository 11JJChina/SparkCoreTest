package com.zhang.akka.yellowchicken.client

import akka.actor.{Actor, ActorRef, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import com.zhang.akka.yellowchicken.common.{ClientMessage, ServerMessage}
import com.zhang.akka.yellowchicken.server.YellowChickenServer.{host, port}

import scala.io.StdIn

/** *
 *
 * @author :  Brian
 *         Date:  2020/11/23
 *         Time: 12:18
 *         Description:
 * */
class CustomerActor(serverHost: String, serverPort: Int) extends Actor {

  var serverActorRef: ActorSelection = _


  override def preStart(): Unit = {
    println("preStart()执行")
    serverActorRef = context.actorSelection(s"akka.tcp://Server@${serverHost}:${serverPort}/user/yellowChickenServer")
    println("serverActorRef=" + serverActorRef)
  }

  override def receive: Receive = {
    case "start" => println("start,客户端运行，可以咨询问题...")
    case msg: String => {
      serverActorRef ! ClientMessage(msg)
    }
    case ServerMessage(msg) => {
      println(s"收到小黄鸡客服(Server)：$msg")
    }

  }
}

object CustomerActor extends App {

  val (clientHost, clientPort, serverHost, serverPort) = ("127.0.0.1", 9990, "127.0.0.1", 9999)

  val config = ConfigFactory.parseString(
    s"""
       |akka.actor.provider="akka.remote.RemoteActorRefProvider"
       |akka.remote.netty.tcp.hostname=$clientHost
       |akka.remote.netty.tcp.port=$clientPort
       |""".stripMargin
  )

  val clientActorSystem: ActorSystem = ActorSystem("client", config)

  val customerActorRef: ActorRef = clientActorSystem.actorOf(Props(new CustomerActor(serverHost, serverPort)), "customerActor")

  customerActorRef ! "start"
  println("请输入要咨询的问题")
  while (true) {
    val msg = StdIn.readLine()
    customerActorRef ! msg
  }

}
