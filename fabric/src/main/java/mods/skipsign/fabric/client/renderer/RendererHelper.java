package mods.skipsign.fabric.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

public class RendererHelper
{
    public static double GetDistancePlayerToBlockEntity(BlockEntity entity)
    {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        BlockPos pos = entity.getBlockPos();

        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();

        return Math.sqrt(player.distanceToSqr(x, y, z));
    }

    public static double GetDistancePlayerToEntityRenderState(EntityRenderState renderState)
    {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        return Math.sqrt(player.distanceToSqr(renderState.x, renderState.y, renderState.z));
    }

    public static double GetDistancePlayerToEntity(Entity entity)
    {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        return Math.sqrt(player.distanceToSqr(entity));
    }

    public static boolean IsInRangeToRenderDist(double dist, double range)
    {
        if (dist < range + 1 && dist > -range)
        {
            return true;
        }
        return false;
    }
}
