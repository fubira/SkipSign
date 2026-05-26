package mods.skipsign.neoforge.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.renderer.blockentity.state.ChestRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;

import com.mojang.blaze3d.vertex.PoseStack;

import mods.skipsign.neoforge.NeoForgeConfig;
import mods.skipsign.neoforge.SkipSignMod;
import mods.skipsign.neoforge.ViewMode;

public class ChestRendererEx<T extends BlockEntity & LidBlockEntity> extends ChestRenderer<T>
{
    public ChestRendererEx(BlockEntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public void submit(ChestRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        Minecraft client = Minecraft.getInstance();
        if (!NeoForgeConfig.enableMod.get() || renderState.blockPos == BlockPos.ZERO || client.player == null || isVisible(renderState.blockPos)) {
            super.submit(renderState, poseStack, submitNodeCollector, cameraRenderState);
        }
    }

    public boolean isVisible(T entity)
    {
        return isVisible(entity.getBlockPos());
    }

    public boolean isVisible(BlockPos pos)
    {
        if (NeoForgeConfig.viewModeChest.get() == ViewMode.FORCE) {
            return true;
        }
        if (NeoForgeConfig.viewModeChest.get() == ViewMode.NONE) {
            return false;
        }

        if (SkipSignMod.client.isZooming()) {
            return true;
        }

        return RendererHelper.IsInRangeToRenderDist(
            RendererHelper.GetDistancePlayerToBlockPos(pos),
            NeoForgeConfig.viewRangeChest.get()
        );
    }
}
