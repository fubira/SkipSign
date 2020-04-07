package mods.skipsign.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.tileentity.TileEntity;

public class RendererHelper
{
    public static double GetDistancePlayerToTileEntity(TileEntity tileEntity)
    {
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;
        BlockPos pos = tileEntity.getPos();

        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();

        return Math.sqrt(player.getDistanceSq(x, y, z));
    }

    public static double GetDistancePlayerToEntity(Entity entity)
    {
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;
        return player.getDistance(entity);
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
