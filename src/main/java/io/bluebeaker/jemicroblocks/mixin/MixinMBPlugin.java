package io.bluebeaker.jemicroblocks.mixin;

import codechicken.microblock.jei.MicroblockJEIPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MicroblockJEIPlugin.class,remap = false)
public class MixinMBPlugin {
    // Prevent the plugin bundled with Microblock
    @Inject(method = "register(Lmezz/jei/api/IModRegistry;)V", at = @At("HEAD"),cancellable = true)
    public void preventRegister(IModRegistry registry, CallbackInfo ci){
        ci.cancel();
    }

    // Prevent the problematic subtypes registry bundled with Microblock
    @Inject(method = "registerItemSubtypes(Lmezz/jei/api/ISubtypeRegistry;)V", at = @At("HEAD"),cancellable = true)
    public void overrideItemSubtypes(ISubtypeRegistry subtypeRegistry, CallbackInfo ci){
        ci.cancel();
    }
}
