package net.packages.seasonal_adventures.event;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.loader.api.FabricLoader;

public class EventHandler {
    public static void initialize() {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            HudRenderCallback.EVENT.register(new HudRenderCallbackHandler());
        }
    }
}