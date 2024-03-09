package com.axidreamdragon.diamondspawner.items;

import javax.annotation.Nullable;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

public class FuelItem extends Item {
    private final int burnTicks;

    public FuelItem(Properties properties, int burnTicks) {
        super(properties);
        this.burnTicks = burnTicks;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return this.burnTicks;
    }
}
