package com.tweak.witheraway.mixin;

import com.tweak.witheraway.Config;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WitherSkull.class)
public class WitherSkullMixin {
    @Redirect(
        method = "onHit",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;DDDFZLnet/minecraft/world/level/Level$ExplosionInteraction;)Lnet/minecraft/world/level/Explosion;")
    )
    private net.minecraft.world.level.Explosion witheraway$redirectSkullExplosion(Level level, net.minecraft.world.entity.Entity entity, double x, double y, double z, float radius, boolean fire, ExplosionInteraction interaction) {
        if (Config.MUTE_ALL_OTHER_WITHER_SOUNDS.getAsBoolean()) {
            if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                return serverLevel.explode(entity, null, null, x, y, z, radius, fire, interaction, false, net.minecraft.core.particles.ParticleTypes.EXPLOSION, net.minecraft.core.particles.ParticleTypes.EXPLOSION_EMITTER, net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE);
            }
        }
        return level.explode(entity, x, y, z, radius, fire, interaction);
    }
}
