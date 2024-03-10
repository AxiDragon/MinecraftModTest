package com.axidreamdragon.diamondspawner.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ItemEntityStackPreventer {
    public static List<ItemEntity> trackedEntities = new ArrayList<>();

    public static void preventStacking(ItemEntity entity) {
        UUID uuid = UUID.randomUUID();

        // set an uuid to prevent stacking
        entity.getItem().setHoverName(new TextComponent(uuid.toString()));

        trackedEntities.add(entity);
    }

    @SubscribeEvent
    public static void onItemPickup(EntityItemPickupEvent event) {
        ItemEntity itemEntity = event.getItem();

        if (!trackedEntities.contains(itemEntity)) {
            return;
        }

        removeStackPrevention(itemEntity);

        trackedEntities.remove(itemEntity);
    }

    private static void removeStackPrevention(ItemEntity itemEntity) {
        ItemStack itemStack = itemEntity.getItem();

        CompoundTag tag = itemStack.getTag();

        itemStack.setHoverName(null);

        if (tag != null) {
            itemStack.removeTagKey("display");
        }
    }

    @SubscribeEvent
    public static void onWorldUnload(WorldEvent.Unload event) {
        for (ItemEntity itemEntity : trackedEntities) {
            removeStackPrevention(itemEntity);
        }
        trackedEntities.clear();
    }
}
