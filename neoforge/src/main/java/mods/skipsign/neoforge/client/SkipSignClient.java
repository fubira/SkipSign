package mods.skipsign.neoforge.client;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.ticks.TickPriority;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import org.lwjgl.glfw.GLFW;

import mods.skipsign.neoforge.SkipSignMod;
import mods.skipsign.neoforge.client.gui.GuiConfigScreen;
import mods.skipsign.neoforge.client.renderer.ChestRendererEx;
import mods.skipsign.neoforge.client.renderer.ItemFrameRendererEx;
import mods.skipsign.neoforge.client.renderer.SignRendererEx;
import mods.skipsign.neoforge.client.renderer.SkullRendererEx;

@OnlyIn(Dist.CLIENT)
public final class SkipSignClient {
    private boolean key_down = false;
    private boolean is_zooming = false;

    public static final KeyMapping keyMappingOption = new KeyMapping("skipsign.keybinding.desc.option", GLFW.GLFW_KEY_F8, "skipsign.keybinding.category");
    public static final KeyMapping keyMappingZoom = new KeyMapping("skipsign.keybinding.desc.zoom", GLFW.GLFW_KEY_RIGHT_ALT, "skipsign.keybinding.category");

    public SkipSignClient(IEventBus modBus){
        modBus.addListener(this::onClientSetup);
        modBus.addListener(this::onRegisterKeyMappings);

        NeoForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, ClientTickEvent.class, this::onTick);
    }

    @OnlyIn(Dist.CLIENT)
    public void onClientSetup(FMLClientSetupEvent event) {
        SkipSignMod.logger.info("ClientEventHandler::FMLClientSetupEvent");
 
        BlockEntityRenderers.register(BlockEntityType.SIGN, SignRendererEx::new);
        BlockEntityRenderers.register(BlockEntityType.CHEST, ChestRendererEx::new);
        BlockEntityRenderers.register(BlockEntityType.TRAPPED_CHEST, ChestRendererEx::new);
        BlockEntityRenderers.register(BlockEntityType.SKULL, SkullRendererEx::new);

        EntityRenderers.register(EntityType.ITEM_FRAME, ItemFrameRendererEx::new);
    }

    @OnlyIn(Dist.CLIENT)
    public void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(keyMappingOption);
        event.register(keyMappingZoom);
    }


    public boolean isZooming() {
        Minecraft client = Minecraft.getInstance();
        return is_zooming || client.player.isScoping();
   }

   public void onTick(ClientTickEvent event) {
        Minecraft client = Minecraft.getInstance();
        if (client.screen != null) {
            return;
        }

        is_zooming = keyMappingZoom.isDown();

        if (!key_down && keyMappingOption.consumeClick()) {
            client.setScreen(new GuiConfigScreen(Component.Serializer.fromJson("{\"text\":\"SkipSign\"}", client.player.registryAccess())));
            key_down = true;
        } else if (key_down && !keyMappingOption.consumeClick()) {
            key_down = false;
        }
    }
}
