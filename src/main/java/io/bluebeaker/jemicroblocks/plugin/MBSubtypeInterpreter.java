package io.bluebeaker.jemicroblocks.plugin;

import codechicken.microblock.ItemMicroPart$;
import codechicken.microblock.handler.MicroblockProxy$;
import mezz.jei.api.ISubtypeRegistry;
import net.minecraft.item.ItemStack;

public class MBSubtypeInterpreter implements ISubtypeRegistry.ISubtypeInterpreter {
    @Override
    public String apply(ItemStack itemStack) {
        return ItemMicroPart$.MODULE$.getMaterialID(itemStack)+":"+MicroblockProxy$.MODULE$.itemMicro().getDamage(itemStack);
    }
}
