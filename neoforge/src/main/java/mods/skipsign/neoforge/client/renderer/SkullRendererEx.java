package mods.skipsign.neoforge.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.blockentity.state.SkullBlockRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SkullBlockEntity;

import com.mojang.blaze3d.vertex.PoseStack;

import mods.skipsign.neoforge.NeoForgeConfig;
import mods.skipsign.neoforge.SkipSignMod;
import mods.skipsign.neoforge.ViewMode;

public class SkullRendererEx extends SkullBlockRenderer
{
    public SkullRendererEx(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void submit(SkullBlockRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState)
    {
        if (!NeoForgeConfig.enableMod.get() || renderState.blockPos == BlockPos.ZERO || Minecraft.getInstance().player == null || isVisible(renderState.blockPos))
        {
            super.submit(renderState, poseStack, submitNodeCollector, cameraRenderState);
        }
    }

    public boolean isVisible(SkullBlockEntity entity)
    {
        return isVisible(entity.getBlockPos());
    }

    public boolean isVisible(BlockPos pos)
    {
        if (NeoForgeConfig.viewModeSkull.get() == ViewMode.FORCE) {
            return true;
        }
        if (NeoForgeConfig.viewModeSkull.get() == ViewMode.NONE) {
            return false;
        }

        if (SkipSignMod.client.isZooming()) {
            return true;
        }

        return RendererHelper.IsInRangeToRenderDist(
            RendererHelper.GetDistancePlayerToBlockPos(pos),
            NeoForgeConfig.viewRangeSkull.get()
        );
    }
}
