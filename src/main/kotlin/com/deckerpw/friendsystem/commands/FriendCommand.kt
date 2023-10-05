package com.deckerpw.friendsystem.commands

import com.deckerpw.friendsystem.player.BukkitPlayer.asFriendPlayer
import com.deckerpw.friendsystem.player.FriendPlayer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.PluginCommand
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class FriendCommand(plugin: JavaPlugin) {

    init {
        val command: PluginCommand? = plugin.getCommand("friend")
        command?.setTabCompleter { sender, command, label, args ->
            if (args.size < 2){
                return@setTabCompleter mutableListOf("add","remove","accept","decline","list","msg")
            }else{
                when(args[0]){
                    "remove" -> {
                        val player : FriendPlayer = (sender as? Player?: return@setTabCompleter null).asFriendPlayer
                        val friendNames : MutableList<String?> = player.friends.map { it.name }.toMutableList()
                        return@setTabCompleter friendNames
                    }
                    "accept","decline" -> {
                        val player : FriendPlayer = (sender as? Player?: return@setTabCompleter null).asFriendPlayer
                        val prendingFriendNames : MutableList<String?> = player.pendingFriends.map { it.name }.toMutableList()
                        return@setTabCompleter prendingFriendNames
                    }
                    "msg" -> {
                        val player : FriendPlayer = (sender as? Player?: return@setTabCompleter null).asFriendPlayer
                        val onlineFriendNames : MutableList<String> = player.onlineFriends.map { it.name }.toMutableList()
                        return@setTabCompleter onlineFriendNames
                    }
                }
            }
            return@setTabCompleter null;
        }
        command?.setExecutor { sender, command, label, args ->

            when(args[0]){
                "add" -> {
                    val player : FriendPlayer = (sender as? Player?: return@setExecutor false).asFriendPlayer
                    val requestedPlayer : FriendPlayer = (Bukkit.getOfflinePlayers().find { it.name.equals(args[1]) }?.player ?: return@setExecutor false).asFriendPlayer;
                    requestedPlayer.pendingFriends.add(player.player)
                    val playerName = player.player.name
                    player.player.sendMessage("Sent §6${args[1]}§r a friend request!")
                    requestedPlayer.player.sendMessage(Component.text()
                        .append(Component.text(playerName,NamedTextColor.GOLD))
                        .append(Component.text(" wants to add you as a friend"))
                        .appendNewline()
                        .append(Component.text("["))
                        .append(Component.text("ACCEPT",NamedTextColor.GREEN,TextDecoration.BOLD).clickEvent(ClickEvent.runCommand("/friend accept $playerName")).hoverEvent(Component.text("Accept friend request").asHoverEvent()))
                        .append(Component.text("] ["))
                        .append(Component.text("DECLINE",NamedTextColor.RED,TextDecoration.BOLD).clickEvent(ClickEvent.runCommand("/friend decline $playerName")).hoverEvent(Component.text("Decline friend request").asHoverEvent()))
                        .append(Component.text("]")))
                }
                "remove" -> {
                    val player : FriendPlayer = (sender as? Player?: return@setExecutor false).asFriendPlayer
                    val requestedPlayer : OfflinePlayer = player.friends.find { it.name.equals(args[1]) } ?: return@setExecutor false;
                    requestedPlayer.player?.asFriendPlayer?.friends?.remove(player.player)
                    player.friends.remove(requestedPlayer)
                    player.player.sendMessage("§6${args[1]}§r is no longer your friend.")
                }
                "accept" -> {
                    val player : FriendPlayer = (sender as? Player?: return@setExecutor false).asFriendPlayer
                    val requestingPlayer : OfflinePlayer = (player.pendingFriends.find { it.name.equals(args[1]) }?.player ?: return@setExecutor false);
                    player.friends.add(requestingPlayer);
                    requestingPlayer.player?.asFriendPlayer?.friends?.add(player.player)
                    player.pendingFriends.remove(requestingPlayer)
                    player.player.sendMessage("§6${args[1]}§r is now your friend.")
                    requestingPlayer.player?.sendMessage("§6${player.player.name}§r is now your friend.")
                }
                "decline" -> {
                    val player : FriendPlayer = (sender as? Player?: return@setExecutor false).asFriendPlayer
                    val requestingPlayer : OfflinePlayer = (player.pendingFriends.find { it.name.equals(args[1]) }?.player ?: return@setExecutor false);
                    player.pendingFriends.remove(requestingPlayer)
                    player.player.sendMessage("§6${args[1]}'s§r friend request was declined.")
                    requestingPlayer.player?.sendMessage("your friend request was declined.")
                }
            }

            return@setExecutor false
        }
    }

}