package net.hecco.heccolib.lib.publicBlocks;

import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class PublicButtonBlock extends ButtonBlock {
    public PublicButtonBlock(BlockSetType type, int ticksToStayPressed, Properties properties) {
        super(type, ticksToStayPressed, properties);
    }
}
