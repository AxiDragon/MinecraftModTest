package com.axidreamdragon.diamondspawner.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class DiamondAmulet extends Item {
    public DiamondAmulet(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack diamond = new ItemStack(Items.DIAMOND);

        player.getCooldowns().addCooldown(this, 2);

        player.getInventory().add(diamond);

        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 3.0F);

        ItemStack stack = player.getItemInHand(hand);
        stack.setDamageValue(stack.getDamageValue() + 1);

        if (stack.getDamageValue() >= stack.getMaxDamage()) {
            stack.shrink(1);
        }

        return super.use(world, player, hand);

    }
}
