package io.bluebeaker.jemicroblocks.utils;

import codechicken.microblock.handler.MicroblockProxy$;
import net.minecraft.item.Item;
import scala.collection.JavaConversions;

import java.util.List;

public class MicroblockProxyUtils {
    public static List<Item> getSaws(){
        return JavaConversions.mutableSeqAsJavaList(MicroblockProxy$.MODULE$.saws());
    }
}
