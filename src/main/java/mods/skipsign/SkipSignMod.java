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
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
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
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mods.skipsign.renderer.RenderItemFrameEx;
import mods.skipsign.renderer.TileEntityChestRendererEx;
import mods.skipsign.renderer.TileEntitySignRendererEx;
import mods.skipsign.renderer.TileEntitySkullRendererEx;

@Mod(SkipSignMod.modId)
public class SkipSignMod
{
    public static Logger logger = LogManager.getLogger();

    public static final String modId = "skipsign";
    public static final String buildId = "2019-6";
    public static String modVersion;

    public static SkipSignConfig config;

    private boolean key_down = false;
    private int HoldTime = 0;

    public static float renderPartialTicks;

    public SkipSignMod() {
        FMLJavaModLoadingContext.get().getModEventBus().register(this);

        Config.register(ModLoadingContext.get());
        modVersion = ModLoadingContext.get().getActiveContainer().getModInfo().getVersion().toString();

        SkipSignMod.logger.info("*** SkipSign " + modVersion + " ***");
    }
    
	@SubscribeEvent
	public void onConfigLoading(final ModConfig.Loading event){
        SkipSignMod.logger.info("SkipSignMod::onConfigLoading");

        config = new SkipSignConfig(event.getConfig());
        config.save();

        SkipSignMod.logger.info("*** SkipSign config saved");
	}

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        SkipSignMod.logger.info("SkipSignMod::onClientSetup");

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySign.class, new TileEntitySignRendererEx());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChest.class, new TileEntityChestRendererEx());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySkull.class, new TileEntitySkullRendererEx());
    }

    @SubscribeEvent
    public void onInterModEnqueue(InterModEnqueueEvent event) {
        SkipSignMod.logger.info("SkipSignMod::onInterModEnqueue");

        RenderManager renderManager = Minecraft.getInstance().getRenderManager();
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        RenderItemFrameEx renderItemFrame = new RenderItemFrameEx(renderManager, itemRenderer);

        SkipSignMod.logger.info(renderManager);
        SkipSignMod.logger.info(renderManager.entityRenderMap);
        renderManager.entityRenderMap.remove(EntityItemFrame.class);
        renderManager.entityRenderMap.put(EntityItemFrame.class, renderItemFrame);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getInstance().currentScreen == null) {
            if (!key_down && InputMappings.isKeyDown(Config.keyCodeVisible.get())) {
                Minecraft.getInstance().displayGuiScreen(new GuiOption());
                key_down = true;
            }
            else if (key_down && !InputMappings.isKeyDown(Config.keyCodeVisible.get())) {
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
