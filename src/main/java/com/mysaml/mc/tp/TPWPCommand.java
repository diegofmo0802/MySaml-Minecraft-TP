package com.mysaml.mc.tp;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mysaml.mc.api.CommandExecutor;

public class TPWPCommand implements CommandExecutor {
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    Teleport.secureTeleportWithPets(player, target);
                } else {
                    sender.sendMessage("Player not found.");
                }
            } if (args.length == 2) {
                Player subject = Bukkit.getPlayer(args[0]);
                Player target = Bukkit.getPlayer(args[1]);
                if (subject != null && target != null) {
                    Teleport.secureTeleportWithPets(subject, target);
                } else {
                    sender.sendMessage("Player not found.");
                }
            } else if (args.length == 3) {
                Double x = args[0] == "~" ? player.getLocation().getX() : Double.parseDouble(args[0]) + 0.5;
                Double y = args[1] == "~" ? player.getLocation().getY() : Double.parseDouble(args[1]) + 0.5;
                Double z = args[2] == "~" ? player.getLocation().getZ() : Double.parseDouble(args[2]) + 0.5;
                Location location = new Location(player.getWorld(), x, y, z);
                Teleport.secureTeleportWithPets(player, location);
            } else if (args.length == 4) {
                Player subject = Bukkit.getPlayer(args[0]);
                Double x = args[1] == "~" ? player.getLocation().getX() : Double.parseDouble(args[1]) + 0.5;
                Double y = args[2] == "~" ? player.getLocation().getY() : Double.parseDouble(args[2]) + 0.5;
                Double z = args[3] == "~" ? player.getLocation().getZ() : Double.parseDouble(args[3]) + 0.5;
                Location location = new Location(player.getWorld(), x, y, z);
                Teleport.secureTeleportWithPets(subject, location);
            } else {
                sender.sendMessage("Usage: /tp <player> <x> <y> <z> | /tp <player> <player>");
            }
        } else {
            if (args.length == 2) {
                Player subject = Bukkit.getPlayer(args[0]);
                Player target = Bukkit.getPlayer(args[1]);
                if (subject != null && target != null) {
                    Teleport.secureTeleportWithPets(subject, target);
                } else {
                    sender.sendMessage("Player not found.");
                }
            } else if (args.length == 4) {
                Player subject = Bukkit.getPlayer(args[0]);
                Double x = Double.parseDouble(args[1]) + 0.5;
                Double y = Double.parseDouble(args[2]) + 0.5;
                Double z = Double.parseDouble(args[3]) + 0.5;
                Location location = new Location(subject.getWorld(), x, y, z);
                Teleport.secureTeleportWithPets(subject, location);
            }  else if (args.length == 5) {
                Player subject = Bukkit.getPlayer(args[0]);
                World world = Bukkit.getWorld(args[1]);
                Double x = Double.parseDouble(args[2]) + 0.5;
                Double y = Double.parseDouble(args[3]) + 0.5;
                Double z = Double.parseDouble(args[4]) + 0.5;
                Location location = new Location(world, x, y, z);
                Teleport.secureTeleportWithPets(subject, location);
            } else {
                sender.sendMessage("Usage: /tp <player> <x> <y> <z> | /tp <player> <player>");
            }
        }
    }
}
