import org.apache.spark.{SparkConf, SparkContext}

object GlobalObject {
  val conf = new SparkConf().setAppName("Spark Config").setMaster("local[*]").set("spark.executor.memory", "1g")
  val sparkContext = new SparkContext(conf)
  val filePath = "src/main/resources/pagecounts.txt"


}
object PageCountData extends App{
  import GlobalObject._
  val initialRDD = sparkContext.textFile(filePath)
  val pageObject = new PageCount

  val totalPages = pageObject.geTotalPageCount(initialRDD)
  println(s"Total page count is : $totalPages")

  val top10Pages = pageObject.getTopTenPagesByHit(initialRDD)
  println("Top 10 pages by hit in the list are :")
  top10Pages.foreach(println(_))

  val englishPageCount = pageObject.getEnglishPagesCount(initialRDD)
  println(s"Total English page count is : $englishPageCount")

  val pageHits = pageObject.getPageHit(initialRDD)
  println(s"Total number of page hits greater than 200k are : $pageHits")

  val pageHitsOfAR = pageObject.getPageHitsOfAR(initialRDD)
  println(s"Total number of hits of AR type pages : $pageHitsOfAR")







}
