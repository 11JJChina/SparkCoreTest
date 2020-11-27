package com.zhang.sparksql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession


object SparkSqlDemo01 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SparkSqlDemo01").setMaster("local[*]")

    val spark = SparkSession.builder().config(conf).getOrCreate()




  }

}
