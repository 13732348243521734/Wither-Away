package com.tweak.witheraway.mixin;

import com.tweak.witheraway.Config;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Level.class)
public class LevelMixin {
    private static final ThreadLocal<Boolean> witheraway$muting = ThreadLocal.withInitial(() -> false);

    public static void witheraway$setMuting(boolean muting) {
        witheraway$muting.set(muting);
    }

    @Inject(method = "playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V", at = @At("HEAD"), cancellable = true)
    private void witheraway$onPlaySound(Player player, double x, double y, double z, SoundEvent sound, SoundSource source, float volume, float pitch, CallbackInfo ci) {
        if (witheraway$muting.get()) {
            ci.cancel();
        }
    }
}
