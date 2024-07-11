package com.mysaml.mc.tp.figures;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

import com.mysaml.mc.tp.geometry.Figure;
import com.mysaml.mc.tp.geometry.Point;
import com.mysaml.mc.tp.geometry.Position;

public abstract class SummonableFigure extends Figure {
    protected World world;
    protected Particle particle;
    public SummonableFigure(Location location, Particle particle) {
        super(new Position(location.getX(), location.getY(), location.getZ()));
        this.world = location.getWorld();
        this.particle = particle;
    }
    public void spawn() {
        if (! isInitialized()) initialize();;
        for (Point point : points) {
            Location summonLocation = new Location(world, point.getX(), point.getY(), point.getZ());
            world.spawnParticle(this.particle, summonLocation, 1, 0, 0, 0, 0, null);
        }
    }
}
