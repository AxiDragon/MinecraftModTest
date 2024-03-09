package com.axidreamdragon.diamondspawner.items;

import java.util.Random;

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

public class DiamondAmulet extends Item {
    private float soundPitch = 2.0F;
    private float velocityRadius = 0.5F;
    private float upwardVelocity = 1F;

    public DiamondAmulet(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack diamond = new ItemStack(Items.DIAMOND);

        ItemEntity diamondEntity = new ItemEntity(world, player.getX(), player.getY() + 0.5, player.getZ(), diamond);

        diamondEntity.setDeltaMovement(getRandomVelocity());

        diamondEntity.setPickUpDelay(20);

        world.addFreshEntity(diamondEntity);

        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, soundPitch);

        soundPitch += 0.1F;

        if (soundPitch > 2.0F) {
            soundPitch = 0.5F;
        }

        ItemStack stack = player.getItemInHand(hand);
        stack.setDamageValue(stack.getDamageValue() + 1);

        if (stack.getDamageValue() >= stack.getMaxDamage()) {
            stack.shrink(1);
        }

        return super.use(world, player, hand);

    }

    private Vec3 getRandomVelocity() {
        Random random = new Random();

        double angle = random.nextDouble() * Math.PI * 2;
        double distance = velocityRadius * Math.sqrt(random.nextDouble());

        double x = Math.cos(angle) * distance;
        double z = Math.sin(angle) * distance;

        return new Vec3(x, upwardVelocity, z);
    }
}
