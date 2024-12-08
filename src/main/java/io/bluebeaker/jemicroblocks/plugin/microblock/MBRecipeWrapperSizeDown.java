package io.bluebeaker.jemicroblocks.plugin.microblock;

import codechicken.microblock.IMicroMaterial;
import io.bluebeaker.jemicroblocks.utils.MicroBlockShape;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.recipes.BrokenCraftingRecipeException;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MBRecipeWrapperSizeDown extends MBRecipeWrapperBase {

    public MBRecipeWrapperSizeDown(IJeiHelpers jeiHelpers, IMicroMaterial material, MicroBlockShape input) {
        super(jeiHelpers, material, input);
        outputSize=2;
    }

    @Override
    public void processItems() {
        this.output=new MicroBlockShape(this.input.shape,this.input.size/2);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        try {
            List<List<ItemStack>> inputLists = new ArrayList<>();
            inputLists.add(this.getValidSaws());
            inputLists.add(Collections.singletonList(inputStack));

            ingredients.setInputLists(VanillaTypes.ITEM, inputLists);

            ingredients.setOutput(VanillaTypes.ITEM, outputStack);
        } catch (RuntimeException e) {
            String info = "Error creating microblock recipe for "+this.material.getMaterialID();
            throw new BrokenCraftingRecipeException(info, e);
        }
    }

    @Override
    public int getWidth() {
        return 1;
    }

    @Override
    public int getHeight() {
        return 2;
    }
}
