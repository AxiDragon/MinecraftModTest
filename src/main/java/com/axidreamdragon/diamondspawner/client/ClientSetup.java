package com.axidreamdragon.diamondspawner.client;

import com.axidreamdragon.diamondspawner.DiamondSpawnerMain;
import com.axidreamdragon.diamondspawner.client.render.ItemEntityProjectileRenderer;
import com.axidreamdragon.diamondspawner.init.EntityInit;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = DiamondSpawnerMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void DoSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(EntityInit.ITEM_ENTITY_PROJECTILE.get(), ItemEntityProjectileRenderer::new);
    }
}
