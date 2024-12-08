package io.bluebeaker.jemicroblocks.plugin.microblock;

import codechicken.microblock.handler.MicroblockProxy$;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.config.Constants;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.List;

public class MBRecipeCategory implements IRecipeCategory<IRecipeWrapper> {
    static IJeiHelpers jeiHelpers;

	private static final int craftOutputSlot = 0;
	private static final int craftInputSlot1 = 1;
    public static final String UID = "jemicroblocks.microblock";

	public static final int width = 116;
	public static final int height = 54;

	private final IDrawable background;
	private final String localizedName;
	private final ICraftingGridHelper craftingGridHelper;
	private final IDrawable icon;

    public MBRecipeCategory(IGuiHelper guiHelper) {
		ResourceLocation location = Constants.RECIPE_GUI_VANILLA;
		background = guiHelper.createDrawable(location, 0, 60, width, height);
		localizedName = I18n.format("category.jemicroblocks.microblock");
		craftingGridHelper = guiHelper.createCraftingGridHelper(craftInputSlot1, craftOutputSlot);

		this.icon = guiHelper.createDrawableIngredient(new ItemStack(MicroblockProxy$.MODULE$.sawDiamond()));
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
        
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(craftOutputSlot, false, 94, 18);

		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 3; ++x) {
				int index = craftInputSlot1 + x + (y * 3);
				guiItemStacks.init(index, true, x * 18, y * 18);
			}
		}
		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);

		if (recipeWrapper instanceof MBRecipeWrapperBase) {
			MBRecipeWrapperBase rw = (MBRecipeWrapperBase)recipeWrapper;
			craftingGridHelper.setInputs(guiItemStacks, inputs, rw.getWidth(), rw.getHeight());
		}
		guiItemStacks.set(craftOutputSlot, outputs.get(0));
    }

	@Nullable
	public IDrawable getIcon() {
		return this.icon;
	}

	@Override
	public String getTitle() {
		return localizedName;
	}

	@Override
	public String getModName() {
		return "microblockcbe";
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

    
}
