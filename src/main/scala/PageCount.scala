import org.apache.spark.rdd.RDD
import BigInt._
class PageCount {

  def geTotalPageCount(initialRDD : RDD[String]) = {
    val result = initialRDD.map { record =>
     record.split(" ")(3).toLong
    }
    result.reduce(_ + _)
  }

  def getTopTenPagesByHit(initialRDD : RDD[String]): Array[Long] = {
    initialRDD.map{ record =>
      record.split(" ")(3).toLong
    }.sortBy(-_).take(10)
  }

  def getEnglishPagesCount(initialRDD : RDD[String]): Long = {
    val result = initialRDD.filter { record =>
      val englishPage = record.split(" ")(1)
      englishPage.contains("/en")
    }
    result.map{record =>
      record.split(" ")(3).toLong
    }.reduce(_+_)
  }

  def getPageHit(initialRDD : RDD[String]) = {
   val pages: RDD[(String, Long)] = initialRDD.map{ record =>
     val name = record.split(" ")(1)
     val hits = record.split(" ")(3).toLong
     (name, hits)
   }
   pages.groupByKey().map(data => (data._1, data._2.sum)).filter(data => data._2 > 200000).count()
  }

  def getPageHitsOfAR(initialRDD : RDD[String]): BigInt = {
    val pageCount: RDD[String] = initialRDD.filter{ record =>
      val pages = record.split(" ")
      pages(0) == "ar"
    }
   val hits = pageCount.map{ record =>
      record.split(" ")(3).toLong
    }.reduce(_+_)
    BigInt(hits.toLong)
  }

}
