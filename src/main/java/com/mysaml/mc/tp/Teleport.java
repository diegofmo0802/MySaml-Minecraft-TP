package com.mysaml.mc.tp;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sittable;
import org.bukkit.entity.Tameable;

public class Teleport {
    public static void secureTeleport(Player player, Player target) {
        secureTeleport(player, target.getLocation());
    }
    public static void secureTeleport(Player player, Location location) {
        if (! isSecure(location)) {
            player.sendMessage("the location is not secure");
            return;
        }
        player.teleport(location);
    }
    public static void secureTeleportWithPets(Player player, Player target) {
        secureTeleportWithPets(player, target.getLocation(), 10);
    }
    public static void secureTeleportWithPets(Player player, Player target, int distance) {
        secureTeleportWithPets(player, target.getLocation(), distance);
    }
    public static void secureTeleportWithPets(Player player, Location location) {
        secureTeleportWithPets(player, location, 10);
    }
    public static void secureTeleportWithPets(Player player, Location location, int distance) {
        if (! isSecure(location)) {
            player.sendMessage("the location is not secure");
            return;
        }
        List<Entity> entities = player.getNearbyEntities(distance, distance, distance);
        for (Entity entity : entities) {
            if (! (entity instanceof Tameable)) continue;
            Tameable pet = (Tameable) entity;
            if (! pet.isTamed() || pet.getOwner() != player) continue;
            if (pet instanceof Sittable) {
                Sittable sittable = (Sittable) pet;
                if (sittable.isSitting()) continue;
            }
            pet.teleport(location);
        }
        player.teleport(location);
    }
    private static boolean isSecure(Location location) {
        Location underLocation = location.clone().subtract(0, 1, 0);
        Block underBlock = underLocation.getBlock();
        Block playerBlock1 = underBlock.getRelative(BlockFace.UP, 1);
        Block playerBlock2 = underBlock.getRelative(BlockFace.UP, 2);
        System.out.println(underBlock.getType() +" "+ playerBlock1.getType() +" "+ playerBlock2.getType());
        return (
            underBlock.getType() != Material.LAVA &&
            underBlock.getType() != Material.WATER &&
            underBlock.getType() != Material.AIR &&
            playerBlock1.getType() == Material.AIR &&
            playerBlock2.getType() == Material.AIR
        );
    }
}
