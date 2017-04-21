import org.apache.spark.rdd.RDD

class PageCount {

  /**
    * finds the total hits on all pages
    *
    */
  def geTotalPageCount(initialRDD : RDD[String]): Long = {
    val result = initialRDD.map { record =>
     record.split(" ")(3).toLong
    }
    result.reduce(_ + _)
  }

  /**
    * finds the top ten hits on the pages
    */
  def getTopTenHits(initialRDD : RDD[String]): Array[Long] = {
    initialRDD.map{ record =>
      record.split(" ")(3).toLong
    }.sortBy(-_).take(10)
  }

  /**
    * finds the hit count of English pages ie which contains "/en"
    */
  def getEnglishPagesCount(initialRDD : RDD[String]): Long = {
    val result = initialRDD.filter { record =>
      val englishPage = record.split(" ")(1)
      englishPage.contains("/en")
    }
    result.map{record =>
      record.split(" ")(3).toLong
    }.reduce(_+_)
  }

  /**
    * finds the combined hits greater than 200k on pages where a page may occur more than once in the list
    */

  def getPageHit(initialRDD : RDD[String]): Long = {
   val pages: RDD[(String, Long)] = initialRDD.map{ record =>
     val name = record.split(" ")(1)
     val hits = record.split(" ")(3).toLong
     (name, hits)
   }
   pages.groupByKey().map(data => (data._1, data._2.sum)).filter(data => data._2 > 200000).count()
  }

  /**
    * finds the total hits on ar pages
    */

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
