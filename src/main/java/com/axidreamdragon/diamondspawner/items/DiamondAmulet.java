package com.axidreamdragon.diamondspawner.items;

import java.util.Random;

import com.axidreamdragon.diamondspawner.entity.ItemEntityProjectile;
import com.axidreamdragon.diamondspawner.init.EntityInit;
import com.axidreamdragon.diamondspawner.tier.ModArmorMaterial;
import com.axidreamdragon.diamondspawner.util.IDamageHandlingArmor;
import com.axidreamdragon.diamondspawner.util.ItemEntityStackPreventer;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class DiamondAmulet extends ArmorItem implements IDamageHandlingArmor {
    private float soundPitch = 2.0F;
    private final float velocityRadius = 0.5F;
    private final float explosionVelocityRadius = 2F;
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
        ItemEntity diamondEntity = createDiamondEntity(world, player);

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

    private ItemEntity createDiamondEntity(Level world, Player player) {
        ItemStack diamond = new ItemStack(Items.DIAMOND);

        ItemEntity diamondEntity = new ItemEntity(world, player.getX(), player.getY() + 0.5, player.getZ(), diamond);

        ItemEntityStackPreventer.preventStacking(diamondEntity);

        diamondEntity.setDeltaMovement(getRandomVelocity());

        diamondEntity.setPickUpDelay(20);

        return diamondEntity;
    }

    private Vec3 getRandomVelocity() {
        Random random = new Random();

        double angle = random.nextDouble() * Math.PI * 2;
        double distance = velocityRadius * Math.sqrt(random.nextDouble());

        double x = Math.cos(angle) * distance;
        double z = Math.sin(angle) * distance;

        return new Vec3(x, upwardVelocity, z);
    }

    @Override
    public float onDamaged(LivingEntity entity, EquipmentSlot slot, DamageSource source, float damage) {
        explodeDiamonds(entity);
        return damage;
    }

    private void explodeDiamonds(LivingEntity entity) {
        Player player = (Player) entity;

        if (player == null)
            return;

        Level world = player.getLevel();

        int diamonds = absorbDiamonds(player);

        for (int i = 0; i < diamonds; i++) {
            createExplosionDiamond(world, player);
        }

        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    private ItemEntity createExplosionDiamond(Level world, Player player) {
        ItemStack diamond = new ItemStack(Items.DIAMOND);

        ItemEntity diamondEntity = new ItemEntity(world, player.getX(), player.getY() + 1, player.getZ(),
                diamond);

        ItemEntityStackPreventer.preventStacking(diamondEntity);

        Vec3 velocity = getRandomExplosionVelocity();

        diamondEntity.setDeltaMovement(velocity);

        diamondEntity.setPickUpDelay(20);

        world.addFreshEntity(diamondEntity);

        if (!world.isClientSide()) {
            ItemEntityProjectile itemEntity = new ItemEntityProjectile(EntityInit.ITEM_ENTITY_PROJECTILE.get(), player,
                    world);

            Vec3 playerPos = new Vec3(player.getX(), player.getY() + 1, player.getZ());
            itemEntity.setPos(playerPos.add(velocity.scale(0.5F)));
            itemEntity.setDeltaMovement(velocity);

            world.addFreshEntity(itemEntity);
        }

        return diamondEntity;
    }

    private Vec3 getRandomExplosionVelocity() {
        Random random = new Random();

        double x = random.nextGaussian();
        double y = random.nextDouble();
        double z = random.nextGaussian();

        Vec3 velocity = new Vec3(x, 0F, z);
        velocity = velocity.normalize();
        velocity = velocity.scale(explosionVelocityRadius);

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
