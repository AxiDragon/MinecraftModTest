package com.axidreamdragon.diamondspawner.items;

import java.util.Random;

import com.axidreamdragon.diamondspawner.tier.ModArmorMaterial;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class DiamondAmulet extends ArmorItem {
    private float soundPitch = 2.0F;
    private final float velocityRadius = 0.5F;
    private final float upwardVelocity = 1F;

    private final float cooldown = 0.5F;

    public DiamondAmulet(Properties properties) {
        super(ModArmorMaterial.AMULET, EquipmentSlot.CHEST, properties);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        if (!player.getCooldowns().isOnCooldown(this) && !world.isClientSide) {
            spawnDiamond(stack, world, player);
        }

        super.onArmorTick(stack, world, player);
    }

    public void spawnDiamond(ItemStack stack, Level world, Player player) {
        ItemStack diamond = new ItemStack(Items.DIAMOND);

        ItemEntity diamondEntity = new ItemEntity(world, player.getX(), player.getY() + 0.5, player.getZ(), diamond);

        diamondEntity.setDeltaMovement(getRandomVelocity());

        diamondEntity.setPickUpDelay(20);

        world.addFreshEntity(diamondEntity);

        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, soundPitch);

        soundPitch += 0.1F;

        if (soundPitch > 3.0F) {
            soundPitch = 0.5F;
        }

        player.getCooldowns().addCooldown(this, (int) (cooldown * 20 / soundPitch));

        stack.setDamageValue(stack.getDamageValue() + 1);

        if (stack.getDamageValue() >= stack.getMaxDamage()) {
            stack.shrink(1);
        }
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
