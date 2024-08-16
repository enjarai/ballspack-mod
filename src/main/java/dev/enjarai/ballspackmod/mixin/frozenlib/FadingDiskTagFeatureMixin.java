package dev.enjarai.ballspackmod.mixin.frozenlib;

import net.frozenblock.lib.shadow.org.jetbrains.annotations.NotNull;
import net.frozenblock.lib.worldgen.feature.api.features.FadingDiskTagFeature;
import net.frozenblock.lib.worldgen.feature.api.features.config.FadingDiskTagFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FadingDiskTagFeature.class)
public abstract class FadingDiskTagFeatureMixin {
    @Inject(
            method = "generate",
            at = @At("HEAD"),
            cancellable = true
    )
    private void skip(@NotNull FeatureContext<FadingDiskTagFeatureConfig> context, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}
