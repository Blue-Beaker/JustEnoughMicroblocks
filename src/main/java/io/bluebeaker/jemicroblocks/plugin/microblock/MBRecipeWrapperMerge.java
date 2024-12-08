package io.bluebeaker.jemicroblocks.plugin.microblock;

import codechicken.microblock.IMicroMaterial;
import codechicken.microblock.ItemMicroPart;
import io.bluebeaker.jemicroblocks.utils.MicroBlockShape;
import io.bluebeaker.jemicroblocks.utils.Shape;
import io.bluebeaker.jemicroblocks.utils.ShapeUtils;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.recipes.BrokenCraftingRecipeException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MBRecipeWrapperMerge extends MBRecipeWrapperBase {

    public MBRecipeWrapperMerge(IJeiHelpers jeiHelpers, IMicroMaterial material, MicroBlockShape input) {
        super(jeiHelpers, material, input);
    }

    @Override
    public void processItems() {
        if(this.input.shape.canShapeUp())
            this.output=new MicroBlockShape(this.input.shape.shapeUp(),this.input.size);
        else
            this.output=new MicroBlockShape(this.input.shape,this.input.size*2);

        inputStack = createMicroblockStack(1,this.input);
        outputStack = createMicroblockStack(1,this.output);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {

        try {
            List<List<ItemStack>> inputLists = new ArrayList<>();

            inputLists.add(Collections.singletonList(inputStack));
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
        return 2;
    }

    @Override
    public int getHeight() {
        return 1;
    }
}
