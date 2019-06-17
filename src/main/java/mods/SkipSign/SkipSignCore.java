package mods.skipsign;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SkipSignCore.modId)
public class SkipSignCore
{
    public static final String modId = "skipsign";
    public static final String buildId = "2019-6";
    public static String modVersion;

    private boolean key_down = false;
    private int HoldTime = 0;

    public static float renderPartialTicks;

    public SkipSignCore() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SkipSignConfig.SPEC);
        modVersion = ModLoadingContext.get().getActiveContainer().getModInfo().getVersion().toString();
        ModLog.Log("*** SkipSign " + modVersion + " ***");
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySign.class, new TileEntitySignRendererEx());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChest.class, new TileEntityChestRendererEx());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySkull.class, new TileEntitySkullRendererEx());
    }

    @SubscribeEvent
    public void onRegistry(RegistryEvent event) {
        RenderManager renderManager = Minecraft.getInstance().getRenderManager();
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        RenderItemFrameEx renderItemFrame = new RenderItemFrameEx(renderManager, itemRenderer);
        renderManager.entityRenderMap.remove(EntityItemFrame.class);
        renderManager.entityRenderMap.put(EntityItemFrame.class, renderItemFrame);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getInstance().currentScreen == null) {
            EntityPlayer player = Minecraft.getInstance().player;

            if (!key_down && InputMappings.isKeyDown(SkipSignConfig.GENERAL.visibleKeyId.get())) {
                Minecraft.getInstance().displayGuiScreen(new GuiOption());
                key_down = true;
            }
            else if (key_down && !InputMappings.isKeyDown(SkipSignConfig.GENERAL.visibleKeyId.get())) {
                key_down = false;
            }
        }
    }

    public boolean GetHoldKey() {
         return key_down;
    }

    @SubscribeEvent
    public void RenderTickEvent(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            renderPartialTicks = event.renderTickTime;
            if (Minecraft.getInstance().player != null)
                DrawableApi.beginFrustum();
        }
        else if (event.phase == TickEvent.Phase.END) {
        }
    }
}
