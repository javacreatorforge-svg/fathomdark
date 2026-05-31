package com.redstonedev.fathomdark.client;

import com.redstonedev.fathomdark.Fathomdark;
import net.minecraft.client.Camera;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Fathomdark.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DepthFog {

    /** Darken the underwater fog colour toward black as you descend. */
    @SubscribeEvent
    public static void onFogColor(ViewportEvent.ComputeFogColor event) {
        Camera cam = event.getCamera();
        if (!DepthMath.cameraInWater(cam)) return;
        double t = DepthMath.depthFactor(cam);
        float keep = (float) (1.0 - 0.95 * t); // up to 95% darker at the bottom
        event.setRed(event.getRed() * keep);
        event.setGreen(event.getGreen() * keep);
        event.setBlue(event.getBlue() * keep);
    }

    /** Tighten visibility (denser fog) the deeper you go. */
    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        Camera cam = event.getCamera();
        if (!DepthMath.cameraInWater(cam)) return;
        double t = DepthMath.depthFactor(cam);
        if (t <= 0.0) return;

        float far = event.getFarPlaneDistance();
        float near = event.getNearPlaneDistance();
        // Pull the far plane in toward a few blocks at maximum depth.
        float newFar = (float) Mth.lerp(t, far, 3.5);
        float newNear = Math.min(near, newFar * 0.2F);
        event.setFarPlaneDistance(newFar);
        event.setNearPlaneDistance(newNear);
        event.setCanceled(true);
    }
}
