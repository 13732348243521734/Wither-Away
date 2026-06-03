package com.tweak.witheraway.mixin;

import com.tweak.witheraway.Config;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
}
