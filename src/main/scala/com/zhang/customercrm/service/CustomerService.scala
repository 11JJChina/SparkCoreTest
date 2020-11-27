package com.zhang.customercrm.service

import com.zhang.customercrm.bean.Customer

import scala.collection.mutable.ArrayBuffer
import util.control.Breaks._

/** *
 *
 * @author :  Brian
 *         Date:  2020/11/22
 *         Time: 13:58
 *         Description:
 **/
class CustomerService {

  var customerNum = 1

  val customers = ArrayBuffer(new Customer(1, "Tom", '男', 10, "110", "tom@123.com"))

  def list(): ArrayBuffer[Customer] = {
    this.customers
  }

  def add(customer: Customer): Boolean = {
    customerNum += 1
    customer.id = customerNum
    customers.append(customer)
    true
  }

  def del(id: Int): Boolean = {
    val index = findIndexById(id)
    if (index != -1) {
      customers.remove(index)
      true
    } else {
      false
    }
  }

  def findIndexById(id: Int) = {
    var index = -1
    breakable {
      for (i <- 0 until customers.length) {
        if (customers(i).id == id) {
          index = i
          break()
        }
      }
    }
    index

  }

}
