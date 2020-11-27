package com.zhang.list

/** *
 *
 * @author :  Brian
 *         Date:  2020/11/15
 *         Time: 20:46
 *         Description:
 **/
object ZipDemo {
  def main(args: Array[String]): Unit = {
    val list1 = List(1, 2, 3)
    val list2 = List(4, 5, 6)
    val list3 = list1.zip(list2)
    println(list3)
  }

}
