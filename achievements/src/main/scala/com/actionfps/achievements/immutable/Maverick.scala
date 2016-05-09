package com.actionfps.achievements.immutable

import com.actionfps.gameparser.enrichers.{JsonGame, JsonGamePlayer}

/**
  * Created by William on 11/11/2015.
  */
sealed trait Maverick {
  def title = "Maverick"

  def description = "Achieve all winning team's flags, 5 minimum"
}

object Maverick {

  case object NotAchieved extends Maverick with AwaitingAchievement {
    def processGame(game: JsonGame,
                    player: JsonGamePlayer,
                    isRegisteredPlayer: JsonGamePlayer => Boolean): Option[Achieved] = {
      for {
        "ctf" <- List(game.mode)
        winningTeam <- game.teams
        if winningTeam.players.contains(player)
        losingTeam <- game.teams
        winningTeamFlags <- winningTeam.flags
        if winningTeam != losingTeam
        losingTeamFlags <- losingTeam.flags
        // require enemy player who is registered, not random noob
        if losingTeam.players.exists(isRegisteredPlayer)
        if winningTeamFlags > losingTeamFlags
        playerFlags <- player.flags
        if winningTeamFlags == playerFlags
        if winningTeamFlags >= 5
      } yield Achieved(
        flags = playerFlags
      )
    }.headOption
  }

  case class Achieved(flags: Int) extends Maverick with CompletedAchievement

}

