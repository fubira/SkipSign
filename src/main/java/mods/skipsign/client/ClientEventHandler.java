package mods.skipsign.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

import mods.skipsign.Config;
import mods.skipsign.SkipSignConfig;
import mods.skipsign.SkipSignMod;
import mods.skipsign.client.DrawableApi;
import mods.skipsign.client.gui.GuiConfigScreen;

@OnlyIn(Dist.CLIENT)
public final class ClientEventHandler {
    private boolean key_down = false;
    private int HoldTime = 0;

    public static void register(){
		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
	}

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        ClientKeyBindings.register();
        ClientRenderer.register();
    }

    @SubscribeEvent
    public void onInterModEnqueue(InterModEnqueueEvent event) {
        SkipSignMod.logger.info("SkipSignMod::onInterModEnqueue");
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getInstance().currentScreen != null) {
            return;
        }

        if (InputMappings.isKeyDown(297)) {
            SkipSignMod.logger.info("keydown 297");
        }
        if (!key_down && InputMappings.isKeyDown(Config.keyCodeVisible.get())) {
            Minecraft.getInstance().displayGuiScreen(new GuiConfigScreen(null));
            key_down = true;
        }
        else if (key_down && !InputMappings.isKeyDown(Config.keyCodeVisible.get())) {
            key_down = false;
        }
    }

    public boolean GetHoldKey() {
         return key_down;
    }

    @SubscribeEvent
    public void RenderTickEvent(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (Minecraft.getInstance().player != null) {
                DrawableApi.beginFrustum(event.renderTickTime);
            }
        } else if (event.phase == TickEvent.Phase.END) {
        }
    }
}
