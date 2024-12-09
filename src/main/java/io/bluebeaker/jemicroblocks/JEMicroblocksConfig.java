package io.bluebeaker.jemicroblocks;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = JEMicroblocks.MODID,type = Type.INSTANCE,category = "general")
public class JEMicroblocksConfig {
    
    @Config.RequiresMcRestart
    @Comment("Show recipes for only selected blocks to reduce startup time and memory usage. ")
    @LangKey("config.jemicroblocks.show_less.name")
    public static boolean show_less = false;

    @Config.RequiresMcRestart
    @Comment("Blocks to show recipes for, when show_less is enabled. Format:'modid:id', metadata is ignored")
    @LangKey("config.jemicroblocks.shown_blocks.name")
    public static String[] shown_blocks = {
        "minecraft:stone",
        "minecraft:diamond_block",
        "thermalfoundation:storage_alloy",
        "minecraft:iron_block",
        "minecraft:coal_block",
        "minecraft:gold_block",
        "thermalfoundation:storage",
        "minecraft:redstone_block",
        "minecraft:emerald_block"
    };
}