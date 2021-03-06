package com.actionfps.achievements.immutable

import com.actionfps.gameparser.enrichers.{JsonGame, JsonGamePlayer}

/**
  * Created by William on 12/11/2015.
  */
@SerialVersionUID(1001L)
object CubeAddict extends Incremental {

  def hourLevels = List(5, 10, 20, 50, 100, 200)

  override def levels = hourLevels.map(_ * 60)

  override type InputType = JsonGame

  override def filter(inputType: JsonGame): Option[Int] = {
    Option(inputType.duration)
  }

  override def levelDescription(level: Int): String = Map(
    5 -> "Hey, this game looks fun.",
    10 -> "I kinda like this game.",
    20 -> "Not stopping now!",
    50 -> "I love this game!",
    100 -> "Just how many hours??",
    200 -> "Wait, when did I start?"
  ).map { case (h, v) => (h * 60, v) }.getOrElse(level, s"Achieve ${level / 60} hours")

  override def eventLevelTitle(level: Int): String = s"achieved Cube Addict level ${level / 60}h"

  override def title: String = "Cube Addict"

  override def levelTitle(level: Int): String = s"$title: ${level / 60}h"
}

