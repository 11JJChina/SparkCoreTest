package com.zhang.customercrm.bean

/** *
 *
 * @author :  Brian
 *         Date:  2020/11/22
 *         Time: 13:09
 *         Description:
 **/
class Customer {

  var id: Int = _
  var name: String = _
  var gender: Char = _
  var age: Short = _
  var tel: String = _
  var email: String = _

  def this(id: Int, name: String, gender: Char, age: Short, tel: String, email: String) {
    this
    this.id = id
    this.name = name
    this.gender = gender
    this.age = age
    this.tel = tel
    this.email = email

  }

  def this(name: String, gender: Char, age: Short, tel: String, email: String) {
    this
    this.name = name
    this.gender = gender
    this.age = age
    this.tel = tel
    this.email = email

  }


  override def toString = this.id + "\t\t" + this.name + "\t\t" + this.gender + "\t\t" + this.age + "\t\t" + this.tel + "\t\t" + this.email

}
