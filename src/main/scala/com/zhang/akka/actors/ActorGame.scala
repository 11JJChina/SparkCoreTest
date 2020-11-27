package com.zhang.akka.actors

import akka.actor.{ActorRef, ActorSystem, Props}

/** *
 *
 * @author :  Brian
 *         Date:  2020/11/22
 *         Time: 20:43
 *         Description:
 * */
object ActorGame extends App {

  private val actorFactory: ActorSystem = ActorSystem("actorFactory")

  private val bActorRef: ActorRef = actorFactory.actorOf(Props[BActor], "bActor")

  private val aActorRef: ActorRef = actorFactory.actorOf(Props(new AActor(bActorRef)), "aActor")

  aActorRef ! "我打"

}
