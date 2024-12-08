package io.bluebeaker.jemicroblocks.plugin.microblock;

import codechicken.microblock.IMicroMaterial;
import codechicken.microblock.ItemMicroPart;
import codechicken.microblock.ItemSaw;
import io.bluebeaker.jemicroblocks.utils.MicroBlockShape;
import io.bluebeaker.jemicroblocks.utils.MicroblockProxyUtils;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class MBRecipeWrapperBase implements IShapedCraftingRecipeWrapper {

	protected final IJeiHelpers jeiHelpers;
	protected final IMicroMaterial material;
	public MicroBlockShape input;
	public MicroBlockShape output;
	public ItemStack inputStack;
	public ItemStack outputStack;

	public MBRecipeWrapperBase(IJeiHelpers jeiHelpers, IMicroMaterial material, MicroBlockShape input) {
		this.jeiHelpers = jeiHelpers;
		this.material = material;
		this.input=input;
		this.processItems();
		this.makeItemStacks();
	}

	public abstract void processItems();

	public abstract int getOutputSize();

	public void makeItemStacks(){
		inputStack = createMicroblockStack(1,this.input);
		outputStack = createMicroblockStack(this.getOutputSize(),this.output);
	}

	@Override
	public abstract void getIngredients(IIngredients ingredients);

	public List<ItemStack> getValidSaws(){
		List<ItemStack> sawStacks = new ArrayList<>();

		for(Item saw : MicroblockProxyUtils.getSaws()){
			if(saw instanceof ItemSaw){
				if(((ItemSaw)saw).harvestLevel()>=this.material.getCutterStrength()){
					sawStacks.add(new ItemStack(saw));
				}
			}
		}
		return sawStacks;
	}

	public ItemStack createMicroblockStack(int amount,MicroBlockShape shape){
		ItemStack stack;
		if(!shape.isValid())
			stack = shape.isFullBlock() ? this.material.getItem() : ItemStack.EMPTY;
		else
			stack = ItemMicroPart.createStack(amount,shape.getMeta(),this.material.getMaterialID());
		return stack;
	}

	public int getWidth(){
		return 0;
	}

	public int getHeight(){
		return 0;
	}
	public boolean isShapeless(){
		return false;
	}
}