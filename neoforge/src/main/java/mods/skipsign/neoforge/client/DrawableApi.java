package mods.skipsign.neoforge.client;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

public class DrawableApi
{
    public static boolean isInRangeToRenderDist(int x, int y, int z, double dist)
    {
        AABB bb = new AABB(new BlockPos(x, y, z));
        double d1 = bb.getSize();
        d1 *= 64.0D * 1.0D;
        return dist < d1 * d1;
    }

    public static boolean isInRangeToRenderDist(BlockEntity chest, double dist)
    {
        AABB bb = new AABB(chest.getBlockPos());
        double d1 = bb.getSize();
        d1 *= 64.0D * 1.0D;
        return dist < d1 * d1;
    }
}
