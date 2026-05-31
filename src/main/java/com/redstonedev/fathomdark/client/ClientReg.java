package com.redstonedev.fathomdark.client;

import com.redstonedev.fathomdark.Fathomdark;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Fathomdark.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientReg {
    @SubscribeEvent
    public static void onRegisterOverlays(RegisterGuiOverlaysEvent event) {
        // Render below the hotbar/HUD so the dimming sits behind the HUD elements.
        event.registerBelow(VanillaGuiOverlay.HELMET.id(), "fathomdark_depth", DepthOverlay.INSTANCE);
    }
}
