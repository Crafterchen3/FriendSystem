package com.deckerpw.friendsystem

import com.deckerpw.friendsystem.commands.FriendCommand
import com.deckerpw.friendsystem.listeners.JoinListener
import net.kyori.adventure.text.Component
import org.bukkit.plugin.java.JavaPlugin


class FriendSystem : JavaPlugin() {

    override fun onEnable() {
        server.pluginManager.registerEvents(JoinListener(), this)
        FriendCommand(this)
        server.sendMessage(Component.text("FriendSystem v1 by Crafterchen3 was succesfully loaded!"))
    }



    override fun onDisable() {

    }

}