package io.bluebeaker.jemicroblocks.plugin.microblock;

import java.util.ArrayList;
import java.util.List;

import codechicken.microblock.IMicroMaterial;
import codechicken.microblock.MicroMaterialRegistry;
import io.bluebeaker.jemicroblocks.utils.MicroBlockShape;
import io.bluebeaker.jemicroblocks.utils.Shape;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IRecipeWrapper;
import scala.Tuple2;

public class MBRecipeMaker {

	private MBRecipeMaker() {
	}
	public static List<IRecipeWrapper> getRecipes(IJeiHelpers jeiHelpers) {
		List<IRecipeWrapper> recipes = new ArrayList<>();

		Tuple2<String, IMicroMaterial>[] idMap = MicroMaterialRegistry.getIdMap();
		for(Tuple2<String, IMicroMaterial> item: idMap){
			recipes.add(new MBRecipeWrapperSizeDown(jeiHelpers, item._2(), new MicroBlockShape(Shape.FACE,8)));

			for(Shape shape : Shape.values()){

				recipes.add(new MBRecipeWrapperSizeDown(jeiHelpers, item._2(), new MicroBlockShape(shape,4)));
				recipes.add(new MBRecipeWrapperSizeDown(jeiHelpers, item._2(), new MicroBlockShape(shape,2)));
				if(shape.canShapeDown()){

					recipes.add(new MBRecipeWrapperShapeDown(jeiHelpers, item._2(), new MicroBlockShape(shape,4)));
					recipes.add(new MBRecipeWrapperShapeDown(jeiHelpers, item._2(), new MicroBlockShape(shape,2)));
					recipes.add(new MBRecipeWrapperShapeDown(jeiHelpers, item._2(), new MicroBlockShape(shape,1)));
				}

				recipes.add(new MBRecipeWrapperMerge(jeiHelpers, item._2(), new MicroBlockShape(shape,4)));
				recipes.add(new MBRecipeWrapperMerge(jeiHelpers, item._2(), new MicroBlockShape(shape,2)));
				recipes.add(new MBRecipeWrapperMerge(jeiHelpers, item._2(), new MicroBlockShape(shape,1)));
			}
		}
		return recipes;
	}
}