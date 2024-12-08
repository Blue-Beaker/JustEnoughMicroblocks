package io.bluebeaker.jemicroblocks.plugin;

import codechicken.microblock.handler.MicroblockProxy$;
import io.bluebeaker.jemicroblocks.JEMicroblocks;
import io.bluebeaker.jemicroblocks.plugin.microblock.MBRecipeCategory;
import io.bluebeaker.jemicroblocks.plugin.microblock.MBRecipeMaker;
import io.bluebeaker.jemicroblocks.utils.MicroblockProxyUtils;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.transfer.PlayerRecipeTransferHandler;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

@JEIPlugin
public class MicroblockPlugin implements IModPlugin {

    private static IJeiRuntime jeiRuntime = null;
    public static IModRegistry modRegistry;

    public MicroblockPlugin() {

    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        subtypeRegistry.registerSubtypeInterpreter(MicroblockProxy$.MODULE$.itemMicro(), new MBSubtypeInterpreter());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new MBRecipeCategory(guiHelper));
    }

    @Override
    public void register(IModRegistry registry) {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        modRegistry = registry;
        JEMicroblocks.getLogger().info("Started loading Microblock recipes...");
        registry.addRecipes(MBRecipeMaker.getRecipes(jeiHelpers), MBRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(Blocks.CRAFTING_TABLE), MBRecipeCategory.UID);
        for (Item saw : MicroblockProxyUtils.getSaws()) {
            registry.addRecipeCatalyst(new ItemStack(saw), MBRecipeCategory.UID);
        }
        JEMicroblocks.getLogger().info("Loaded MB recipes!");

        modRegistry.getRecipeTransferRegistry().addRecipeTransferHandler(ContainerWorkbench.class, MBRecipeCategory.UID, 1, 9, 10, 36);
        modRegistry.getRecipeTransferRegistry().addRecipeTransferHandler(new PlayerRecipeTransferHandler(jeiHelpers.recipeTransferHandlerHelper()), MBRecipeCategory.UID);
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntimeIn) {
        MicroblockPlugin.jeiRuntime = jeiRuntimeIn;
    }

    public static void setFilterText(@Nonnull String filterText) {
        jeiRuntime.getIngredientFilter().setFilterText(filterText);
    }

    public static String getFilterText() {
        return jeiRuntime.getIngredientFilter().getFilterText();
    }

    public static void showCraftingRecipes() {
    }

    @Override
    public void registerIngredients(IModIngredientRegistration ingredientRegistration) {
    }
}
