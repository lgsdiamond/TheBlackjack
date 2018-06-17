package com.lgsdiamond.theblackjack.blackjackelement

import com.lgsdiamond.theblackjack.toAllinBet
import com.lgsdiamond.theblackjack.toValidBet

/**
 * Created by lgsdiamond on 2018-03-10.
 */

class Box(val index: Int) : ArrayList<PlayerHand>() {
    var player: Player = Table.ghostPlayer
    var better: Better = Table.ghostBetter

    val playerSeated: Boolean
        get() = (player != Table.ghostPlayer)

    var bet: Float = 0.0f               // betting for current round

    override fun toString() = "Box($index)"

    fun readyRound() {
        clear()
        bet = 0.0f

        if (playerSeated) {         // keep the player
            val nextBet = better.calcNextBet().toValidBet()

            if (player.hasBalance(nextBet)) {
                addInitialBet(nextBet)
            } else {
                addInitialBet(player.balance.toAllinBet())   // all-in
            }
            add(PlayerHand(this, nextBet))
        } else {
            better = Table.ghostBetter
        }
    }

    fun addInitialBet(betAmount: Float) {
        require(playerSeated) { "ERROR: Betting with no player" }

        player.takeOutBalance(betAmount)
        bet += betAmount
    }

    fun cancelBet() {
        require(playerSeated) { "ERROR: Betting with no player" }

        player.putInBalance(bet)
        bet = 0.0f
    }
}