package com.deckerpw.friendsystem.listeners

import com.deckerpw.friendsystem.player.BukkitPlayer.asFriendPlayer
import com.deckerpw.friendsystem.player.FriendPlayer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class JoinListener : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player: FriendPlayer = event.player.asFriendPlayer;
        val onlineFriendNames: MutableList<String> = player.onlineFriends.map { it.name }.toMutableList()
        val message: String
        if (onlineFriendNames.size == 0) {
            message = "§7There are no friends currently online ):"
        } else if (onlineFriendNames.size == 1) {
            message = "§6${onlineFriendNames[0]}§r is online!"
        } else {
            message = "§6${onlineFriendNames.size}§r Friends are online!"
        }
        event.player.sendMessage(message);
    }

}