package mods.skipsign.forge.client;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmlclient.registry.ClientRegistry;

import org.lwjgl.glfw.GLFW;

import mods.skipsign.forge.SkipSignMod;
import mods.skipsign.forge.client.gui.GuiConfigScreen;
import mods.skipsign.forge.client.renderer.ChestRendererEx;
import mods.skipsign.forge.client.renderer.ItemFrameRendererEx;
import mods.skipsign.forge.client.renderer.SignRendererEx;
import mods.skipsign.forge.client.renderer.SkullRendererEx;

@OnlyIn(Dist.CLIENT)
public final class SkipSignClient {
    private boolean key_down = false;
    private boolean is_zooming = false;

    public static final KeyMapping keyMappingOption = new KeyMapping("skipsign.keybinding.desc.option", GLFW.GLFW_KEY_F8, "skipsign.keybinding.category");
    public static final KeyMapping keyMappingZoom = new KeyMapping("skipsign.keybinding.desc.zoom", GLFW.GLFW_KEY_RIGHT_ALT, "skipsign.keybinding.category");

    public SkipSignClient(){
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, TickEvent.ClientTickEvent.class, this::onTick);
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        SkipSignMod.logger.info("ClientEventHandler::FMLClientSetupEvent");
 
        ClientRegistry.registerKeyBinding(keyMappingOption);
        ClientRegistry.registerKeyBinding(keyMappingZoom);

        BlockEntityRenderers.register(BlockEntityType.SIGN, SignRendererEx::new);
        BlockEntityRenderers.register(BlockEntityType.CHEST, ChestRendererEx::new);
        BlockEntityRenderers.register(BlockEntityType.TRAPPED_CHEST, ChestRendererEx::new);
        BlockEntityRenderers.register(BlockEntityType.SKULL, SkullRendererEx::new);

        EntityRenderers.register(EntityType.ITEM_FRAME, ItemFrameRendererEx::new);
    }

    public boolean isZooming() {
        Minecraft client = Minecraft.getInstance();
        return is_zooming || client.player.isScoping();
   }

   public void onTick(TickEvent.ClientTickEvent event) {
        Minecraft client = Minecraft.getInstance();
        if (client.screen != null) {
            return;
        }

        is_zooming = keyMappingZoom.isDown();

        if (!key_down && keyMappingOption.consumeClick()) {
            client.setScreen(new GuiConfigScreen(new TextComponent("SkipSign")));
            key_down = true;
        } else if (key_down && !keyMappingOption.consumeClick()) {
            key_down = false;
        }
    }
}
