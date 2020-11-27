package com.zhang.akka.actor

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/** *
 *
 * @author :  Brian
 *         Date:  2020/11/22
 *         Time: 17:03
 *         Description:
 * */
class SayHelloActor extends Actor{
  override def receive: Receive = {
    case "hello" => println("收到hello,回应hello too")
    case "ok" => println("收到ok,回应ok too")
    case "exit" => {
      println("接收到exit指令,退出系统")
      context.stop(self)
      context.system.terminate()
    }
    case _ => println("匹配不到")
  }
}

object SayHelloActor{

  private val actorFactory: ActorSystem = ActorSystem("actorFactory")

  private val sayHelloActorRef: ActorRef = actorFactory.actorOf(Props[SayHelloActor], "sayHelloActor")



  def main(args: Array[String]): Unit = {

    sayHelloActorRef ! "hello"
  }
}
