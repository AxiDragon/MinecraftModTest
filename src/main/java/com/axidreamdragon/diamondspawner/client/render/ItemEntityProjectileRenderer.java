package com.axidreamdragon.diamondspawner.client.render;

import com.axidreamdragon.diamondspawner.entity.ItemEntityProjectile;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ItemEntityProjectileRenderer extends ArrowRenderer<ItemEntityProjectile> {

    public static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/item_entity_projectile.png");

    public ItemEntityProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public ResourceLocation getTextureLocation(ItemEntityProjectile entity) {
        return TEXTURE;
    }
}
