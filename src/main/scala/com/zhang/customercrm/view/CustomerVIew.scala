package com.zhang.customercrm.view

import com.zhang.customercrm.bean.Customer
import com.zhang.customercrm.service.CustomerService

import scala.io.StdIn
import util.control.Breaks._

/** *
 *
 * @author :  Brian
 *         Date:  2020/11/22
 *         Time: 13:37
 *         Description:
 * */
class CustomerVIew {

  var loop = true

  var key = ' '

  val customerService = new CustomerService

  def mainMenu() = {
    do {
      println("-----------客户信息管理软件-------------")
      println("           1添加客户")
      println("           2修改客户")
      println("           3删除客户")
      println("           4客户列表")
      println("           5退    出")
      println("请选择(1-5):")
      key = StdIn.readChar()
      key match {
        case '1' => this.add()
        case '2' => println("修改客户")
        case '3' => this.del()
        case '4' => this.list()
        case '5' => this.loop = false
      }

    } while (loop)
    println("你退出了软件系统....")
  }

  def list(): Unit = {
    println()
    println("-----------客户列表-------------")
    println("编号\t姓名\t性别\t年龄\t电话\t邮箱")

    val customers = customerService.list()

    for (elem <- customers) {
      println(elem)
    }

    println("-------------客户列表完成--------------")

  }

  def add(): Unit = {
    println()
    println("-----------添加客户-------------")
    println("姓名：")
    val name = StdIn.readLine()
    println("性别：")
    val gender = StdIn.readChar()
    println("年龄：")
    val age = StdIn.readShort()
    println("电话：")
    val tel = StdIn.readLine()
    println("邮箱：")
    val email = StdIn.readLine()

    val customer = new Customer(name, gender, age, tel, email)
    customerService.add(customer)
    println("-----------添加完成-------------")
  }

  def del(): Unit = {
    println("-----------添加客户-------------")
    println("请选择待删除的客户编号(-1退出):")
    val id = StdIn.readInt()
    if (id == -1) {
      println("----------删除没有完成----------")
      return
    }

    println("确认是否删除(Y/N)：")
    var choice = ' '
    breakable{
      do {
        choice = StdIn.readChar().toLower
        if(choice == 'n' || choice == 'y'){
          break()
        }
        println("确认是否删除(Y/N)：")
      }while(true)
    }



    if (choice == 'y') {
      if (customerService.del(id)) {
        println("----------删除完成----------")
        return
      }

    }

    println("----------删除没有完成----------")

  }

}
