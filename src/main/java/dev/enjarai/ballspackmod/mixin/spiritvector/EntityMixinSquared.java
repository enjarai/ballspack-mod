package dev.enjarai.ballspackmod.mixin.spiritvector;

import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Entity.class, priority = 1500)
public abstract class EntityMixinSquared {
    @Shadow public abstract boolean isTouchingWater();

    @SuppressWarnings("UnresolvedMixinReference")
    @TargetHandler(
            mixin = "symbolics.division.spirit.vector.mixin.EntityMixin",
            name = "preventSprintingWithSpiritVectorEquipped"
    )
    @WrapWithCondition(
            method = "@MixinSquared:Handler",
            at = @At(
                    value = "INVOKE",
                    target = "org/spongepowered/asm/mixin/injection/callback/CallbackInfo.cancel()V"
            )
    )
    private boolean allowInWater(CallbackInfo instance) {
        return !isTouchingWater();
    }
}
