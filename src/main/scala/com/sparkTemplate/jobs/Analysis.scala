package com.sparkTemplate

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{count}

object Analysis {
  def showDataFrame(data: DataFrame): DataFrame = {
    data.show();
    return data
  }

  def aggByGameId(data: DataFrame): Option[DataFrame] = {
    try {
      return Some(data.groupBy("GameID").agg(count("GameID") as "GameIDCount"))
    } catch {
      case _: Exception => None
    }
  }
}
