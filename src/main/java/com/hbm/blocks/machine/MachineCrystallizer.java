package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.lib.ForgeDirection;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.TileEntityProxyInventory;
import com.hbm.tileentity.machine.TileEntityMachineCrystallizer;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MachineCrystallizer extends BlockDummyable {

	public MachineCrystallizer(Material mat, String s) {
		super(mat, s);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int meta) {
		if(meta >= 12)
			return new TileEntityMachineCrystallizer();

		if(meta >= 8 && meta <= 11)
			return new TileEntityProxyInventory();

		if(meta == 7)
			return new TileEntityProxyCombo(false, true, true);

		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			int[] pos1 = this.findCore(world, pos.getX(), pos.getY(), pos.getZ());

			if(pos1 == null)
				return false;

			TileEntityMachineCrystallizer entity = (TileEntityMachineCrystallizer) world.getTileEntity(new BlockPos(pos1[0], pos1[1], pos1[2]));
			if(entity != null)
			{
				player.openGui(MainRegistry.instance, ModBlocks.guiID_crystallizer, world, pos1[0], pos1[1], pos1[2]);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int[] getDimensions() {
		return new int[] { 6, 0, 1, 1, 1, 1 };
	}
	
	@Override
	public int getOffset() {
		return 1;
	}
	
	@Override
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		this.makeExtra(world, x + dir.offsetX * o + 1, y, z + dir.offsetZ * o);
		this.makeExtra(world, x + dir.offsetX * o - 1, y, z + dir.offsetZ * o);
		this.makeExtra(world, x + dir.offsetX * o, y, z + dir.offsetZ * o + 1);
		this.makeExtra(world, x + dir.offsetX * o, y, z + dir.offsetZ * o - 1);

		if(dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH) {
			this.makeExtra(world, x + dir.offsetX * o + 1, y + 5, z + dir.offsetZ * o);
			this.makeExtra(world, x + dir.offsetX * o - 1, y + 5, z + dir.offsetZ * o);
		}

		if(dir == ForgeDirection.EAST || dir == ForgeDirection.WEST) {
			this.makeExtra(world, x + dir.offsetX * o, y + 5, z + dir.offsetZ * o + 1);
			this.makeExtra(world, x + dir.offsetX * o, y + 5, z + dir.offsetZ * o - 1);
		}
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}
	
}