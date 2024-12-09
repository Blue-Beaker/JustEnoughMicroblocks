package io.bluebeaker.jemicroblocks.plugin.microblock;

import codechicken.microblock.IMicroMaterial;
import codechicken.microblock.MicroMaterialRegistry;
import io.bluebeaker.jemicroblocks.JEMicroblocksConfig;
import io.bluebeaker.jemicroblocks.utils.MicroBlockShape;
import io.bluebeaker.jemicroblocks.utils.Shape;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MBRecipeMaker {

	private MBRecipeMaker() {
	}
	public static List<IRecipeWrapper> getRecipes(IJeiHelpers jh) {
		List<IRecipeWrapper> recipes = new ArrayList<>();
		Tuple2<String, IMicroMaterial>[] idMap = MicroMaterialRegistry.getIdMap();

		List<Item> enabled_blocks = new ArrayList<>();
		Arrays.stream(JEMicroblocksConfig.shown_blocks).forEach((id)->{
			Item item = Item.REGISTRY.getObject(new ResourceLocation(id));
			if(item!=null){
				enabled_blocks.add(item);
			}
		});
		List<IMicroMaterial> materials = new ArrayList<>();

		if(!JEMicroblocksConfig.show_less){
			Arrays.stream(idMap).forEach((item)->{materials.add(item._2());});
		}else{
			Arrays.stream(idMap).forEach((item)-> {
				if (enabled_blocks.contains(item._2().getItem().getItem())) {
					materials.add(item._2());
				}
			});
		}

		for(IMicroMaterial mat:materials){

			// Block->Slab
			recipes.add(new MBRecipeWrapperSizeDown(jh, mat, new MicroBlockShape(Shape.FACE,8)));

			for(Shape shape : Shape.values()){
				// Slice down
				recipes.add(new MBRecipeWrapperSizeDown(jh, mat, new MicroBlockShape(shape,4)));
				recipes.add(new MBRecipeWrapperSizeDown(jh, mat, new MicroBlockShape(shape,2)));

				if(shape.canShapeDown()){
					for (int i :new int[]{1,2,4}) {
						recipes.add(new MBRecipeWrapperShapeDown(jh, mat, new MicroBlockShape(shape,i)));
					}
				}
				for (int i :new int[]{1,2,4}) {
					recipes.add(new MBRecipeWrapperMerge(jh, mat, new MicroBlockShape(shape,i)));
				}
			}

			// Hollow/Unhollow, 4xPoint -> face
			for (int i :new int[]{1,2,4}) {
				recipes.add(new MBRecipeWrapperHollow(jh, mat, new MicroBlockShape(Shape.FACE,i)));
				recipes.add(new MBRecipeWrapperUnHollow(jh, mat, new MicroBlockShape(Shape.HOLLOW,i)));
				recipes.add(new MBRecipeWrapperMerge(jh, mat, new MicroBlockShape(Shape.POINT,i),4));
			}
			//4,6,8xCover ->
			recipes.add(new MBRecipeWrapperMerge(jh, mat, new MicroBlockShape(Shape.FACE,1),4));
			recipes.add(new MBRecipeWrapperMerge(jh, mat, new MicroBlockShape(Shape.FACE,1),6));
			recipes.add(new MBRecipeWrapperMerge(jh, mat, new MicroBlockShape(Shape.FACE,1),8));
			recipes.add(new MBRecipeWrapperMerge(jh, mat, new MicroBlockShape(Shape.FACE,2),4));

			recipes.add(new MBRecipeWrapperMerge(jh, mat, new MicroBlockShape(Shape.HOLLOW,1),4));
			recipes.add(new MBRecipeWrapperMerge(jh, mat, new MicroBlockShape(Shape.HOLLOW,1),6));
			recipes.add(new MBRecipeWrapperMerge(jh, mat, new MicroBlockShape(Shape.HOLLOW,1),8));
			recipes.add(new MBRecipeWrapperMerge(jh, mat, new MicroBlockShape(Shape.HOLLOW,2),4));
		}
		return recipes;
	}
}