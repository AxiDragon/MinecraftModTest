package com.axidreamdragon.diamondspawner.init;

import com.axidreamdragon.diamondspawner.DiamondSpawnerMain;
import com.axidreamdragon.diamondspawner.items.FuelItem;
import com.axidreamdragon.diamondspawner.items.ImplodingDiamond;
import com.axidreamdragon.diamondspawner.items.TeleportDiamond;
import com.axidreamdragon.diamondspawner.tier.ModArmorMaterial;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
                        DiamondSpawnerMain.MOD_ID);

        public static final RegistryObject<Item> DIAMOND_AMULET = ITEMS.register("diamond_amulet",
                        () -> new ArmorItem(ModArmorMaterial.AMULET, EquipmentSlot.CHEST,
                                        new Item.Properties().tab(ModCreativeTab.instance)));

        public static final RegistryObject<Item> YUMMY_DIAMOND = ITEMS.register("yummy_diamond",
                        () -> new Item(new Item.Properties().tab(ModCreativeTab.instance)
                                        .food(new FoodProperties.Builder().nutrition(2).saturationMod(10).alwaysEat()
                                                        .effect(() -> new MobEffectInstance(
                                                                        MobEffects.DAMAGE_RESISTANCE, 200, 0), 0.75F)
                                                        .build())));

        public static final RegistryObject<Item> COOL_COAL = ITEMS.register("cool_coal",
                        () -> new FuelItem(new Item.Properties().tab(ModCreativeTab.instance), 6400));

        public static final RegistryObject<Item> TELEPORT_DIAMOND = ITEMS.register("teleport_diamond",
                        () -> new TeleportDiamond(new Item.Properties().tab(ModCreativeTab.instance).durability(50)));

        public static final RegistryObject<Item> IMPLODING_DIAMOND = ITEMS.register("imploding_diamond",
                        () -> new ImplodingDiamond(new Item.Properties().tab(ModCreativeTab.instance).durability(1)));

        public static class ModCreativeTab extends CreativeModeTab {
                public static final ModCreativeTab instance = new ModCreativeTab(CreativeModeTab.TABS.length,
                                "diamondspawner");

                private ModCreativeTab(int index, String label) {
                        super(index, label);
                }

                @Override
                public ItemStack makeIcon() {
                        return new ItemStack(DIAMOND_AMULET.get());
                }
        }
}
