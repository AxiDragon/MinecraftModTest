package com.axidreamdragon.diamondspawner.init;

import com.axidreamdragon.diamondspawner.DiamondSpawnerMain;
import com.axidreamdragon.diamondspawner.entity.ItemEntityProjectile;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInit {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES,
            DiamondSpawnerMain.MOD_ID);

    public static final RegistryObject<EntityType<ItemEntityProjectile>> ITEM_ENTITY_PROJECTILE = ENTITY_TYPES.register(
            "item_entity_arrow",
            () -> EntityType.Builder
                    .of((EntityType.EntityFactory<ItemEntityProjectile>) ItemEntityProjectile::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F).build("item_entity_arrow"));
}
