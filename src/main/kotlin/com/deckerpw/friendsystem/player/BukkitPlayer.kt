package com.deckerpw.friendsystem.player

import org.bukkit.entity.Player

object BukkitPlayer {
    val Player.asFriendPlayer
        get() = FriendPlayer.get(this)
}