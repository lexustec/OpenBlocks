package openblocks.common.block;

import net.minecraft.block.material.Material;
import openblocks.OpenBlocks;
import openblocks.common.tileentity.TileEntitySponge;

public class BlockSponge extends OpenBlock {

	public BlockSponge() {
		super(OpenBlocks.Config.blockSpongeId, Material.sponge);
		setupBlock(this, "sponge", TileEntitySponge.class);
	}

}
