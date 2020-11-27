package com.zhang.akka.actors

import akka.actor.Actor

/** *
 *
 * @author :  Brian
 *         Date:  2020/11/22
 *         Time: 20:07
 *         Description:
 * */
class BActor extends Actor {
  override def receive: Receive = {
    case "我打" => {
      println("BActor(乔峰) 挺猛 看我降龙十八掌")
      Thread.sleep(1000)
      sender() ! "我打"
    }

  }
}
