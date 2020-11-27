package com.zhang.list

/** *
 *
 * @author :  Brian
 *         Date:  2020/11/22
 *         Time: 10:57
 *         Description:
 **/
object RecursiveMaxList {
  def main(args: Array[String]): Unit = {


  }

  def max(xs: List[Int]): Int = {
    if (xs.isEmpty)
      throw new NoSuchElementException
    if (xs.size == 1)
      xs.head
    else if (xs.head > max(xs.tail)) xs.head else max(xs.tail)
  }

  def reverse(str: String): String = {
    if (str.length == 1) str else reverse(str.tail) + str.head
  }

  def factorial(n: BigInt): BigInt = {
    if (n == 0) 1 else n * factorial(n - 1)
  }

}
