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

public class MBRecipeWrapperMerge extends MBRecipeWrapperBase {
    private int count;
    private int outputSize;
    public MBRecipeWrapperMerge(IJeiHelpers jeiHelpers, IMicroMaterial material, MicroBlockShape input) {
        super(jeiHelpers, material, input);
        this.count=2;
        this.init();
    }
    public MBRecipeWrapperMerge(IJeiHelpers jeiHelpers, IMicroMaterial material, MicroBlockShape input, int count) {
        super(jeiHelpers, material, input);
        this.count=count;
        this.init();
    }

    @Override
    public void processItems() {
        if(this.input.shape.canShapeUp()){
            this.output=new MicroBlockShape(count==4?this.input.shape.shapeUp().shapeUp():this.input.shape.shapeUp(),this.input.size);
            this.outputSize = 1;
        } else {
            if (count == 6) {
                this.output = new MicroBlockShape(this.input.shape, this.input.size * 2);
                this.outputSize = 3;
            } else {
                this.output = new MicroBlockShape(this.input.shape, this.input.size * count);
                this.outputSize = 1;
            }
        }
    }

    @Override
    public int getOutputSize() {
        return this.outputSize;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {

        try {
            List<List<ItemStack>> inputLists = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                inputLists.add(Collections.singletonList(inputStack));
            }

            ingredients.setInputLists(VanillaTypes.ITEM, inputLists);

            ingredients.setOutput(VanillaTypes.ITEM, outputStack);
        } catch (RuntimeException e) {
            String info = "Error creating microblock recipe for "+this.material.getMaterialID();
            throw new BrokenCraftingRecipeException(info, e);
        }
    }

    @Override
    public int getWidth() {
        return this.count<=4 ? 2 : 3;
    }

    @Override
    public int getHeight() {
        return this.count<=4 ? 2 : 3;
    }

    @Override
    public boolean isShapeless(){
        return true;
    }
}
