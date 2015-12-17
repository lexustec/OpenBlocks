package openblocks.common.item;

import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.block.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import openblocks.OpenBlocks;
import openmods.infobook.BookDocumentation;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

@BookDocumentation
public class ItemWrench extends Item {

	private final Set<Class<? extends Block>> sneakOnly = Sets.newIdentityHashSet();

	public ItemWrench() {
		setMaxStackSize(1);
		setCreativeTab(OpenBlocks.tabOpenBlocks);
		setTextureName("openblocks:wrench");

		sneakOnly.add(BlockLever.class);
		sneakOnly.add(BlockButton.class);
		sneakOnly.add(BlockChest.class);
	}

	@Override
	public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player) {
		return true;
	}

	private boolean requiresSneaking(final Block block) {
		return Iterables.any(sneakOnly, new Predicate<Class<? extends Block>>() {
			@Override
			public boolean apply(@Nullable Class<? extends Block> input) {
				return input.isInstance(block);
			}
		});
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		final Block block = world.getBlock(x, y, z);

		if (block == null) return false;

		if (requiresSneaking(block) && !player.isSneaking()) return false;

		final ForgeDirection rotationAxis = ForgeDirection.getOrientation(side);

		final ForgeDirection[] rotations = block.getValidRotations(world, x, y, z);
		if (ArrayUtils.contains(rotations, rotationAxis)) {
			if (block.rotateBlock(world, x, y, z, rotationAxis)) {
				player.swingItem();
				return !world.isRemote;
			}
		}

		return false;
	}

}