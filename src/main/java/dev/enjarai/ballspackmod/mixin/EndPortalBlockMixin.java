package dev.enjarai.ballspackmod.mixin;

import dev.enjarai.ballspackmod.BallspackMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndPortalBlock.class)
public class EndPortalBlockMixin {
    @Inject(
            method = "onEntityCollision",
            at = @At("HEAD"),
            cancellable = true
    )
    public void preventEndering(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci){
        if (!world.isClient() && world.getGameRules().getBoolean(BallspackMod.PREVENT_END_GAMERULE)) {
            if (entity instanceof PlayerEntity player) {
                player.sendMessage(Text.of("The End is currently closed."), true);
            }

            ci.cancel();
        }
    }
}
