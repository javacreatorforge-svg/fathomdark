package com.redstonedev.fathomdark.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

/** Overall scene dimming so even nearby blocks fade as you sink (fog alone leaves close blocks lit). */
@OnlyIn(Dist.CLIENT)
public class DepthOverlay implements IGuiOverlay {
    public static final DepthOverlay INSTANCE = new DepthOverlay();

    @Override
    public void render(net.minecraftforge.client.gui.overlay.ForgeGui gui, PoseStack pose,
                       float partialTick, int width, int height) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui) return;
        if (!DepthMath.cameraInWater(mc.gameRenderer.getMainCamera())) return;

        double t = DepthMath.depthFactor(mc.gameRenderer.getMainCamera());
        if (t <= 0.01) return;

        // Cap so the HUD stays readable; deep water gets strongly dimmed but not fully black.
        int alpha = (int) (Math.min(t, 1.0) * 0.55 * 255.0);
        if (alpha <= 0) return;
        int color = (alpha << 24); // black with computed alpha
        GuiComponent.fill(pose, 0, 0, width, height, color);
    }
}
