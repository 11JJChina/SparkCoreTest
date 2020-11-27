package com.zhang.list

import scala.collection.mutable

/** *
 *
 * @author :  Brian
 *         Date:  2020/11/15
 *         Time: 20:19
 *         Description:
 **/
object CharCountDemo {
  def main(args: Array[String]): Unit = {

    val sentence="AAAAAAAAAAasdadssdASDDDDFBBBCCCVVV"
    val map = sentence.foldLeft(Map[Char, Int]())(charCount)
    println(map)

    val map2 = mutable.Map[Char,Int]()
    sentence.foldLeft(map2)(charCount2)

  }

  def charCount(map: Map[Char, Int], c: Char): Map[Char, Int] = {
    map + (c -> (map.getOrElse(c, 0) + 1))
  }

  def charCount2(map: mutable.Map[Char, Int], c: Char): mutable.Map[Char, Int] = {
    map += (c -> (map.getOrElse(c, 0) + 1))
  }
}
