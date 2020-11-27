package com.zhang.list

/** *
 *
 * @author :  Brian
 *         Date:  2020/11/16
 *         Time: 19:58
 *         Description:
 * */
object ViewDemo01 {
  def main(args: Array[String]): Unit = {


    def eq(i:Int): Boolean ={
      i.toString.equals(i.toString.reverse)
    }

    val seq1 = (1 to 100).filter(eq)

    println(seq1)

    val value = (1 to 100).view.filter(eq)
    println(value)


  }

}
