package mods.skipsign;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.tileentity.TileEntity;

public class SkipSignHelper
{
    public static double GetDistancePlayerToTileEntity(TileEntity tileEntity)
    {
        EntityPlayer player = Minecraft.getInstance().player;
        BlockPos pos = tileEntity.getPos();

        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();

        return player.getDistance(x, y, z);
    }

    public static double GetDistancePlayerToEntity(Entity entity)
    {
        EntityPlayer player = Minecraft.getInstance().player;
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
