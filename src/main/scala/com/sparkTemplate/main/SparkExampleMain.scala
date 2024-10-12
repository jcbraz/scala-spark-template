package com.sparkTemplate
import com.sparkTemplate.Analysis.{showDataFrame, aggByGameId}
import org.apache.spark.sql.DataFrame

object SparkExampleMain extends App {
  val spark = createSparkSession("Spark test", isLocal = true)

  val (inputPath, outputPath) = parseArgs(args = args)
  val data: DataFrame = spark.read.parquet(s"${inputPath}/*.parquet")
  val shownData: DataFrame = showDataFrame(data = data)
  val groupByGameID: DataFrame = aggByGameId(shownData) match {
    case Some(df) => df
    case None => {
      spark.emptyDataFrame
    }
  }

  groupByGameID.write
    .mode("overwrite")
    .option("header", "true")
    .csv(outputPath)
}
