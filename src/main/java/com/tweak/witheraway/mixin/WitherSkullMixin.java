package com.tweak.witheraway.mixin;

import com.tweak.witheraway.Config;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.world.entity.projectile.WitherSkull")
public class WitherSkullMixin {
    @Redirect(
        method = "onHit",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;DDDFZLnet/minecraft/world/level/Level$ExplosionInteraction;)V")
    )
    private void witheraway$redirectSkullExplosion(Level level, net.minecraft.world.entity.Entity entity, double x, double y, double z, float radius, boolean fire, ExplosionInteraction interaction) {
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
}
