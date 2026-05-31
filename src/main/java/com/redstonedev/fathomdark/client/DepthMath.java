package com.redstonedev.fathomdark.client;

import net.minecraft.client.Camera;
import net.minecraft.world.level.material.FogType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class DepthMath {
    private DepthMath() {}

    // Sunlight effectively reaches ~sea level; below that it falls off.
    private static final double SEA_LEVEL = 62.0;
    // Blocks below sea level at which it becomes effectively pitch black.
    private static final double MAX_DEPTH = 45.0;

    public static boolean cameraInWater(Camera cam) {
        return cam != null && cam.getFluidInCamera() == FogType.WATER;
    }

    /** 0.0 at the surface, ramping to 1.0 at MAX_DEPTH below sea level. */
    public static double depthFactor(Camera cam) {
        if (cam == null) return 0.0;
        double depth = SEA_LEVEL - cam.getPosition().y;
        if (depth <= 0) return 0.0;
        double t = depth / MAX_DEPTH;
        if (t > 1.0) t = 1.0;
        // Ease in so the upper water stays fairly clear and it darkens with depth.
        return t * t;
    }
}
