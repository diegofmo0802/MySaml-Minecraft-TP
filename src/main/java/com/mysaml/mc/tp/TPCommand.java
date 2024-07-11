package com.mysaml.mc.tp;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import com.mysaml.mc.api.CommandExecutor;
import com.mysaml.mc.tp.figures.Circle;
import com.mysaml.mc.tp.figures.Polygon;
import com.mysaml.mc.tp.figures.SummonableFigure;

public class TPCommand implements CommandExecutor {
    public BukkitTask task = null;
    public void CreateParticles(Player player) {
        if (task != null) {
            task.cancel();
            task = null;
            return;
        }
        Location actualLocation = player.getLocation().clone();
        Particle particleToRender = Particle.END_ROD;
        int sleep = 1000;
        SummonableFigure circle1 = new Circle(
            actualLocation,
            particleToRender,
            5, 100
        ), polygon1 = new Polygon(
            actualLocation,
            particleToRender,
            5, 4, 100
        ), polygon2 = new Polygon(
            actualLocation,
            particleToRender,
            5, 4, 100
        ), circle2 = new Circle(
            actualLocation,
            particleToRender,
            3.5, 100
        ), starP1 = new Polygon(
            actualLocation,
            particleToRender,
            3.5, 3, 100
        ), starP2 = new Polygon(
            actualLocation,
            particleToRender,
            3.5, 3, 100
        ), starP3 = new Polygon(
            actualLocation,
            particleToRender,
            3.5, 3, 100
        ), starP4 = new Polygon(
            actualLocation,
            particleToRender,
            3.5, 3, 100
        ), corner1 = new Circle(
            actualLocation,
            particleToRender,
            1, 100
        ), corner2 = new Circle(
            actualLocation,
            particleToRender,
            1, 100
        ), corner3 = new Circle(
            actualLocation,
            particleToRender,
            1, 100
        ), corner4 = new Circle(
            actualLocation,
            particleToRender,
            1, 100
        ), polygon3 = new Polygon(
            actualLocation,
            particleToRender,
            1.6, 12, 100
        );

        polygon2.rotate(0, 45, 0);

        starP2.rotate(0, 90, 0);
        starP3.rotate(0, 180, 0);
        starP4.rotate(0, 270, 0);

        corner1.translate(-5, 0, 0);
        corner2.translate(5, 0, 0);
        corner3.translate(0, 0, -5);
        corner4.translate(0, 0, 5);

        polygon3.rotate(0, 45, 0);

        try {
            task = Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance().getCore(), new Runnable() {
                @Override
                public void run() {
                    while (task != null) {
                        circle1.spawn();
                        polygon1.spawn();
                        polygon2.spawn();
                        circle2.spawn();
                        starP1.spawn();
                        starP2.spawn();
                        starP3.spawn();
                        starP4.spawn();
                        corner1.spawn();
                        corner2.spawn();
                        corner3.spawn();
                        corner4.spawn();
                        polygon3.spawn();
                        TPCommand.wait(sleep);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            CreateParticles(player);
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    Teleport.secureTeleport(player, target);
                } else {
                    sender.sendMessage("Player not found.");
                }
            } if (args.length == 2) {
                Player subject = Bukkit.getPlayer(args[0]);
                Player target = Bukkit.getPlayer(args[1]);
                if (subject != null && target != null) {
                    Teleport.secureTeleport(subject, target);
                } else {
                    sender.sendMessage("Player not found.");
                }
            } else if (args.length == 3) {
                Double x = args[0] == "~" ? player.getLocation().getX() : Double.parseDouble(args[0]) + 0.5;
                Double y = args[1] == "~" ? player.getLocation().getY() : Double.parseDouble(args[1]) + 0.5;
                Double z = args[2] == "~" ? player.getLocation().getZ() : Double.parseDouble(args[2]) + 0.5;
                Location location = new Location(player.getWorld(), x, y, z);
                Teleport.secureTeleport(player, location);
            } else if (args.length == 4) {
                Player subject = Bukkit.getPlayer(args[0]);
                Double x = args[1] == "~" ? player.getLocation().getX() : Double.parseDouble(args[1]) + 0.5;
                Double y = args[2] == "~" ? player.getLocation().getY() : Double.parseDouble(args[2]) + 0.5;
                Double z = args[3] == "~" ? player.getLocation().getZ() : Double.parseDouble(args[3]) + 0.5;
                Location location = new Location(player.getWorld(), x, y, z);
                Teleport.secureTeleport(subject, location);
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
                Teleport.secureTeleport(subject, location);
            }  else if (args.length == 5) {
                Player subject = Bukkit.getPlayer(args[0]);
                World world = Bukkit.getWorld(args[1]);
                Double x = Double.parseDouble(args[2]) + 0.5;
                Double y = Double.parseDouble(args[3]) + 0.5;
                Double z = Double.parseDouble(args[4]) + 0.5;
                Location location = new Location(world, x, y, z);
                Teleport.secureTeleport(subject, location);
            } else {
                sender.sendMessage("Usage: /tp <player> <x> <y> <z> | /tp <player> <player>");
            }
        }
    }
    private static void wait(int timeMS) {
        if (timeMS <= 0) return;
        try {
            Thread.sleep(timeMS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
