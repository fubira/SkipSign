package mods.skipsign.client;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DrawableApi
{
    public static boolean isInRangeToRenderDist(World w, int x, int y, int z, double par1)
    {
        Block c = Blocks.STONE;
        AxisAlignedBB bb = new AxisAlignedBB(new BlockPos(x, y, z));
        double d1 = bb.getAverageEdgeLength();
        d1 *= 64.0D * 1.0D;
        return par1 < d1 * d1;
    }

    public static boolean isInRangeToRenderDist(TileEntity chest, double par1)
    {
        Block c = Blocks.STONE;
        AxisAlignedBB bb = new AxisAlignedBB(chest.getPos());
        double d1 = bb.getAverageEdgeLength();
        d1 *= 64.0D * 1.0D;
        return par1 < d1 * d1;
    }
}
