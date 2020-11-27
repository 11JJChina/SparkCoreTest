package com.zhang.dataskew

import org.apache.spark.sql.{Row, SparkSession}

import scala.collection.immutable
import scala.util.Random

object DataSkewSolution1 {

  def main(args: Array[String]): Unit = {

    /**
     * 案例3:通过sample采样，对倾斜key单独进行处理
     */

    val numbers = 10000
    val sml = 100
    val defPar = 100
    val skewPart = 7

    val spark = SparkSession.builder()
      .appName("DataSkewSolution3")
      .master("local[*]")
      .getOrCreate()

    val dataBig = getData(numbers,defPar,skewPart)

    val dataSml = getData(sml,defPar,skewPart)

    val smlDF = spark.createDataFrame(dataSml).toDF("id", "value")
    smlDF.createOrReplaceTempView("table_sml")

    val bigDF = spark.createDataFrame(dataBig).toDF("id", "value")
    bigDF.createOrReplaceTempView("table_big")

    import spark.sqlContext.implicits._

    val skewKeys = bigDF.sample(false, 0.2)
      .groupBy(bigDF.col("id"))
      .count().orderBy($"count".desc)
      .filter($"count" > 200)
      .collect().map(_.get(0))

    val noSkewSmlDF = smlDF.filter(row => !skewKeys.contains(row.get(0)))
    println("noSkewSmlDF----------------------")
    noSkewSmlDF.show(10)
    val skewSmlDF = smlDF.filter(row => skewKeys.contains(row.get(0)))
    println("skewSmlDF----------------------")
    skewSmlDF.show(10)

    val randomSkewSmlDF = skewSmlDF.flatMap { case Row(key: Int, value: Int) => {
      for (i <- 1 to 100) yield {
        val prefix = Random.nextInt(100)
        (prefix + "_" + key, value)
      }
    }
    }.toDF("id", "value")

    println("randomSkewSmlDF----------------------")
    randomSkewSmlDF.show(10)

    val noSkewBigDF = bigDF.filter(row => !skewKeys.contains(row(0)))

    val skewBigDF = bigDF.filter(row => skewKeys.contains(row(0)))

    val randomSkewBigDF = skewBigDF.map { case Row(key: Int, value: Int) =>

      val prefix = Random.nextInt(100) + 1

      (s"${prefix}_${key}", value)

    }.toDF("id", "value")

    println("randomSkewBigDF----------------------")
    randomSkewBigDF.show(10)

    val skewDF = randomSkewSmlDF.alias("a").join(randomSkewBigDF.alias("b"), "id")
      .selectExpr("split(a.id,'_')[1] as id", "b.value as val1", "a.value val2")
      .groupBy("id").agg("val1" -> "sum").alias("total")

    skewDF.show(10)

    val noSkewDF = noSkewSmlDF.alias("a").join(noSkewBigDF.alias("b"), "id")
      .groupBy("id").agg("b.value" -> "sum").alias("total")

    noSkewDF.show(10)

    noSkewDF.union(skewDF).show(20)

    Thread.sleep(1000 * 60 * 60)

    spark.stop()


  }

  def getData(number:Int,defPar:Int,skewPart:Int): immutable.Seq[(Int, Int)] ={
    val data = for (num <- 1 to number)
      yield (
        if (num < defPar) num
        else number + (new Random()).nextInt(skewPart) * (skewPart)
        , num)
    data
  }

}
