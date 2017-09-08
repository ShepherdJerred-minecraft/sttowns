package com.shepherdjerred.sttowns.objects.trackers;

import com.shepherdjerred.riotbase.commands.SpigotCommandSource;
import com.shepherdjerred.sttowns.objects.TownPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TownPlayers extends Entities<TownPlayer> {

    public TownPlayer get(Player player) {
        return get(player.getUniqueId());
    }

    public TownPlayer get(CommandSender sender) {
        if (sender instanceof Player) {
            return get(((Player) sender).getUniqueId());
        }
        return null;
    }

    public TownPlayer get(SpigotCommandSource source) {
        return get(source.getPlayer());
    }

    public void remove(Player player) {
        remove(get(player));
    }

}
