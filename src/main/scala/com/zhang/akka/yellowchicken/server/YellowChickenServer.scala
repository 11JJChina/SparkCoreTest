package com.zhang.akka.yellowchicken.server

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import com.zhang.akka.yellowchicken.common.{ClientMessage, ServerMessage}

/** *
 *
 * @author :  Brian
 *         Date:  2020/11/23
 *         Time: 11:56
 *         Description:
 * */
class YellowChickenServer extends Actor {
  override def receive: Receive = {
    case "start" => println("start 小黄鸡客服开始工作了...")
    case ClientMessage(msg) =>{
      msg match {
        case "大数据学费" => sender() ! ServerMessage("150000RMB")
        case "学校地址" => sender() ! ServerMessage("上海张江路申城佳苑200弄")
        case "学习什么技术" => sender() ! ServerMessage("大数据 前端 Python Java")
        case _  => sender() ! ServerMessage("你说的啥玩意儿")

      }
    }
  }
}

object YellowChickenServer extends App {
  val host = "127.0.0.1"
  val port = 9999

  val config = ConfigFactory.parseString(
    s"""
       |akka.actor.provider="akka.remote.RemoteActorRefProvider"
       |akka.remote.netty.tcp.hostname=$host
       |akka.remote.netty.tcp.port=$port
       |""".stripMargin
  )

  val serverActorSystem: ActorSystem = ActorSystem("Server", config)

  val yellowChickenServerRef: ActorRef = serverActorSystem.actorOf(Props[YellowChickenServer], "yellowChickenServer")

  yellowChickenServerRef ! "start"

}
