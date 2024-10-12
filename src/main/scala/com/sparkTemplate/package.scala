package com

import org.apache.spark.sql.SparkSession

package object sparkTemplate {
  def createSparkSession(appName: String, isLocal: Boolean): SparkSession = {
    if (isLocal) {
      SparkSession
        .builder()
        .config("spark.sql.caseSensitive", value = true)
        .config("spark.sql.session.timeZone", value = "UTC")
        .config("spark.driver.memory", value = "8G")
        .config("spark.driver.bindAddress", "127.0.0.1")
        .appName(appName)
        .master("local[*]")
        .getOrCreate()
    } else {
      SparkSession
        .builder()
        .config("spark.sql.caseSensitive", value = true)
        .config("spark.sql.session.timeZone", value = "UTC")
        .appName(appName)
        .getOrCreate()
    }
  }

  def parseArgs(args: Array[String]): (String, String) = {

    // parse arguments with functional approach
    def getArgumentsMap(
        args: List[String],
        acc: Map[String, String]
    ): Map[String, String] = args match {
      case Nil => acc
      case arg :: value :: tail if arg.startsWith("--") => {
        getArgumentsMap(tail, acc + (arg -> value))
      }
      case _ =>
        throw new IllegalArgumentException("Invalid argument format")
    }

    val parsedArgsMap = getArgumentsMap(args.toList, Map.empty)

    val inputPath = parsedArgsMap.getOrElse(
      "--input-path",
      throw new IllegalArgumentException("--input-path is required")
    )
    val outputPath = parsedArgsMap.getOrElse(
      "--output-path",
      throw new IllegalArgumentException("--output-path is required")
    )

    return (inputPath, outputPath)
  }
}
