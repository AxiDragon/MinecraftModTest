package com.axidreamdragon.diamondspawner.items;

import java.util.Random;

import com.axidreamdragon.diamondspawner.util.ItemEntityStackPreventer;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ImplodingDiamond extends Item {
    private float velocityRadius = 0.8F;

    public ImplodingDiamond(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        int diamondCount = absorbDiamonds(player);

        if (diamondCount == 0) {
            world.playSound(player, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.5F, 0.5F);

            player.getCooldowns().addCooldown(this, 20);

            return super.use(world, player, hand);
        }

        for (int i = 0; i < diamondCount; i++) {
            ItemEntity explosionDiamond = getExplosionDiamond(world, player);

            world.addFreshEntity(explosionDiamond);
        }

        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.0F, 1.0F);

        ItemStack stack = player.getItemInHand(hand);
        stack.shrink(1);

        return super.use(world, player, hand);
    }

    private ItemEntity getExplosionDiamond(Level world, Player player) {
        ItemStack diamond = new ItemStack(Items.DIAMOND);

        ItemEntity diamondEntity = new ItemEntity(world, player.getX(), player.getY() + 1, player.getZ(),
                diamond);

        ItemEntityStackPreventer.preventStacking(diamondEntity);

        diamondEntity.setDeltaMovement(getRandomVelocity());

        diamondEntity.setPickUpDelay(20);

        return diamondEntity;
    }

    private Vec3 getRandomVelocity() {
        Random random = new Random();

        double x = random.nextGaussian();
        double y = random.nextGaussian();
        double z = random.nextGaussian();

        Vec3 velocity = new Vec3(x, y, z);
        velocity = velocity.normalize();
        velocity = velocity.scale(velocityRadius);

        return velocity;
    }

    public int absorbDiamonds(Player player) {
        int diamondCount = 0;

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);

            if (stack.getItem() == Items.DIAMOND) {
                diamondCount += stack.getCount();
                player.getInventory().removeItem(stack);
            }
        }

        return diamondCount;
    }
}