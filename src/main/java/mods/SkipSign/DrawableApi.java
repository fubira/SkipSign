package mods.SkipSign;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DrawableApi
{
    public static Frustum frustum = new Frustum();

    public static double DX = 0, DY = 0, DZ = 0;

    public static void beginFrustum()
    {
        float f = SkipSignCore.renderPartialTicks;

        EntityPlayer player = Minecraft.getMinecraft().player;

        DX = player.prevPosX + (player.posX - player.prevPosX) * (double)f;
        DY = player.prevPosY + (player.posY - player.prevPosY) * (double)f;
        DZ = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)f;

        frustum.setPosition(DX, DY, DZ);

        // Minecraft.getMinecraft().renderGlobal.clipRenderersByFrustum(frustum, f);
    }

    public static boolean isDraw(World w, int x, int y, int z)
    {
        Block chest = Blocks.STONE;
        AxisAlignedBB bb = chest.getCollisionBoundingBox(chest.getDefaultState(), w, new BlockPos(x, y, z));
        boolean ignoreFrustumCheck = true;

        return DrawableApi.isDraw1(w, x, y, z, DX, DY, DZ) && (ignoreFrustumCheck || frustum.isBoundingBoxInFrustum(bb));
    }

    public static boolean isDraw(TileEntityChest tileEntity, double x, double y, double z)
    {
        Block chest = Blocks.STONE;
        AxisAlignedBB bb = chest.getCollisionBoundingBox(chest.getDefaultState(), tileEntity.getWorld(), tileEntity.getPos());
        boolean ignoreFrustumCheck = true;

        return DrawableApi.isDraw1(tileEntity, DX, DY, DZ) && (ignoreFrustumCheck || frustum.isBoundingBoxInFrustum(bb));
    }

    public static boolean isDraw(TileEntitySign tileEntity, double x, double y, double z)
    {
        Block sign = Blocks.STONE;
        AxisAlignedBB bb = sign.getCollisionBoundingBox(sign.getDefaultState(), tileEntity.getWorld(), tileEntity.getPos());
        boolean ignoreFrustumCheck = true;

        return DrawableApi.isDraw1(tileEntity, DX, DY, DZ) && (ignoreFrustumCheck || frustum.isBoundingBoxInFrustum(bb));
    }

    public static boolean isDraw(TileEntitySkull tileEntity, double x, double y, double z)
    {
        Block sign = Blocks.STONE;
        AxisAlignedBB bb = sign.getCollisionBoundingBox(sign.getDefaultState(), tileEntity.getWorld(), tileEntity.getPos());
        boolean ignoreFrustumCheck = true;

        return DrawableApi.isDraw1(tileEntity, DX, DY, DZ) && (ignoreFrustumCheck || frustum.isBoundingBoxInFrustum(bb));
    }

    @SideOnly(Side.CLIENT)
    public static boolean isDraw1(World w, int x, int y, int z, double dx, double dy, double dz)
    {
        double d3 = x - dx;
        double d4 = y - dy;
        double d5 = z - dz;
        double d6 = d3 * d3 + d4 * d4 + d5 * d5;
        return isInRangeToRenderDist(w, x, y, z, d6);
    }

    @SideOnly(Side.CLIENT)
    public static boolean isDraw1(TileEntity chest, double dx, double dy, double dz)
    {
        double d3 = (double)chest.getPos().getX() - dx;
        double d4 = (double)chest.getPos().getY() - dy;
        double d5 = (double)chest.getPos().getZ() - dz;
        double d6 = d3 * d3 + d4 * d4 + d5 * d5;
        return isInRangeToRenderDist(chest, d6);
    }

    @SideOnly(Side.CLIENT)
    public static boolean isInRangeToRenderDist(World w, int x, int y, int z, double par1)
    {
        Block c = Blocks.STONE;
        AxisAlignedBB bb = c.getCollisionBoundingBox(c.getDefaultState(), w, new BlockPos(x, y, z));
        double d1 = bb.getAverageEdgeLength();
        d1 *= 64.0D * 1.0D;
        return par1 < d1 * d1;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isInRangeToRenderDist(TileEntity chest, double par1)
    {
        Block c = Blocks.STONE;
        AxisAlignedBB bb = c.getCollisionBoundingBox(c.getDefaultState(),
                                                     chest.getWorld(),
                                                     chest.getPos());
        double d1 = bb.getAverageEdgeLength();
        d1 *= 64.0D * 1.0D;
        return par1 < d1 * d1;
    }
}
