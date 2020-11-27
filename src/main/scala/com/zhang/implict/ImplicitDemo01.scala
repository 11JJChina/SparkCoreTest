package com.zhang.implict

/** *
 *
 * @author :  Bin
 *         Date:  2020/11/1
 *         Time: 22:56
 *         Description:
 * */
object ImplicitDemo01 {
  def main(args: Array[String]): Unit = {

        implicit def f1(d: Double): Int = {
          d.toInt
        }

    val num: Int = 3.5
    println(num)

  }

}
