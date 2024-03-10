package com.axidreamdragon.diamondspawner.entity;

import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;

public class ItemEntityProjectile extends AbstractArrow {
    public ItemEntityProjectile(EntityType<ItemEntityProjectile> entityType, Level world) {
        super(entityType, world);
    }

    public ItemEntityProjectile(EntityType<ItemEntityProjectile> entityType, double x, double y, double z,
            Level world) {
        super(entityType, x, y, z, world);
    }

    public ItemEntityProjectile(EntityType<ItemEntityProjectile> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world);
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        this.level.explode(this, this.getX(), this.getY(), this.getZ(), 2.0f, false, Explosion.BlockInteraction.NONE);
    }

    @Override
    public void setPierceLevel(byte pierceLevel) {
        super.setPierceLevel(Byte.MAX_VALUE);
    }

    @Override
    public void setBaseDamage(double damage) {
        damage = 14.0;
        super.setBaseDamage(damage);
    }

    @Override
    public void setSoundEvent(SoundEvent soundEvent) {
        soundEvent = SoundEvents.EXPERIENCE_ORB_PICKUP;
        super.setSoundEvent(soundEvent);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
