package com.tweak.witheraway.mixin;

import com.tweak.witheraway.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WitherBoss.class)
public class WitherBossMixin {

    @Shadow
    private ServerBossEvent bossEvent;

    @Inject(
        method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;)V",
        at = @At("TAIL")
    )
    private void witheraway$onInit(EntityType<?> entityType, Level level, CallbackInfo ci) {
        if (Config.DISABLE_WITHER_DARKENING.getAsBoolean()) {
            this.bossEvent.setDarkenScreen(false);
        }
    }

    @Redirect(
        method = "startSeenByPlayer",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerBossEvent;addPlayer(Lnet/minecraft/server/level/ServerPlayer;)V")
    )
    private void witheraway$redirectAddPlayer(ServerBossEvent bossEvent, ServerPlayer player) {
        if (!Config.DISABLE_WITHER_BOSSBAR.getAsBoolean()) {
            bossEvent.addPlayer(player);
        }
    }

    @Redirect(
        method = "stopSeenByPlayer",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerBossEvent;removePlayer(Lnet/minecraft/server/level/ServerPlayer;)V")
    )
    private void witheraway$redirectRemovePlayer(ServerBossEvent bossEvent, ServerPlayer player) {
        if (!Config.DISABLE_WITHER_BOSSBAR.getAsBoolean()) {
            bossEvent.removePlayer(player);
        }
    }

    @Redirect(
        method = "customServerAiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;globalLevelEvent(ILnet/minecraft/core/BlockPos;I)V")
    )
    private void witheraway$redirectSpawnSound(Level level, int type, BlockPos pos, int data) {
        if (!Config.MUTE_WITHER_SPAWN_SOUND.getAsBoolean()) {
            level.globalLevelEvent(type, pos, data);
        }
    }

    @Redirect(
        method = "customServerAiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;DDDFZLnet/minecraft/world/level/Level$ExplosionInteraction;)V")
    )
    private void witheraway$redirectSpawnExplosion(Level level, net.minecraft.world.entity.Entity entity, double x, double y, double z, float radius, boolean fire, ExplosionInteraction interaction) {
        if (Config.MUTE_ALL_OTHER_WITHER_SOUNDS.getAsBoolean()) {
            LevelMixin.witheraway$setMuting(true);
            try {
                level.explode(entity, x, y, z, radius, fire, interaction);
            } finally {
                LevelMixin.witheraway$setMuting(false);
            }
        } else {
            level.explode(entity, x, y, z, radius, fire, interaction);
        }
    }

    @Redirect(
        method = "performRangedAttack(IDDDZ)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;levelEvent(Lnet/minecraft/world/entity/player/Player;ILnet/minecraft/core/BlockPos;I)V")
    )
    private void witheraway$redirectSkullLaunchSound(Level level, net.minecraft.world.entity.player.Player player, int type, BlockPos pos, int data) {
        if (!Config.MUTE_ALL_OTHER_WITHER_SOUNDS.getAsBoolean()) {
            level.levelEvent(player, type, pos, data);
        }
    }

    @Inject(method = "getAmbientSound", at = @At("HEAD"), cancellable = true)
    private void witheraway$onGetAmbientSound(CallbackInfoReturnable<SoundEvent> cir) {
        if (Config.MUTE_ALL_OTHER_WITHER_SOUNDS.getAsBoolean()) {
            cir.setReturnValue(null);
        }
    }

    @Inject(method = "getHurtSound", at = @At("HEAD"), cancellable = true)
    private void witheraway$onGetHurtSound(DamageSource source, CallbackInfoReturnable<SoundEvent> cir) {
        if (Config.MUTE_ALL_OTHER_WITHER_SOUNDS.getAsBoolean()) {
            cir.setReturnValue(null);
        }
    }

    @Inject(method = "getDeathSound", at = @At("HEAD"), cancellable = true)
    private void witheraway$onGetDeathSound(CallbackInfoReturnable<SoundEvent> cir) {
        if (Config.MUTE_ALL_OTHER_WITHER_SOUNDS.getAsBoolean()) {
            cir.setReturnValue(null);
        }
    }
}
