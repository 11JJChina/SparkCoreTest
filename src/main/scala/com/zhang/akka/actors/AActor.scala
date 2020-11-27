package com.zhang.akka.actors

import akka.actor.{Actor, ActorRef}

/** *
 *
 * @author :  Brian
 *         Date:  2020/11/22
 *         Time: 20:07
 *         Description:
 * */
class AActor(actorRef: ActorRef) extends Actor{
  val bActorRef = actorRef
  override def receive: Receive = {
    case "start" =>{
      println("AActor 出招了，start ok")
      self ! "我打"
    }
    case "我打" =>{
      println("AActor(黄飞鸿)厉害，看我佛山无影脚")
      Thread.sleep(1000)
      bActorRef ! "我打"
    }
  }
}
