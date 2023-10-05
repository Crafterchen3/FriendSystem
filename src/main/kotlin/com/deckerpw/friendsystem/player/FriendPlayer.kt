package com.deckerpw.friendsystem.player

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import java.util.UUID

class FriendPlayer(var player: Player) {
    companion object {
        private val players: MutableList<FriendPlayer> = mutableListOf()

        fun get(player: Player): FriendPlayer {
            var existingPlayer = players.firstOrNull { it.player.uniqueId == player.uniqueId }
            if (existingPlayer == null){
                existingPlayer = FriendPlayer(player)
                players.add(existingPlayer)
            }
            existingPlayer.player = player
            return existingPlayer
        }
    }
    val friends : MutableList<OfflinePlayer> = mutableListOf()
    val onlineFriends : MutableList<Player>
        get() = friends.filter { it.isOnline }.map { it.player!! }.toMutableList()
    val pendingFriends : MutableList<OfflinePlayer> = mutableListOf()
}
