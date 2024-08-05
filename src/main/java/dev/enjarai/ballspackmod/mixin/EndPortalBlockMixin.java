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
        if (world.getGameRules().getBoolean(BallspackMod.ENTER_END_GAMERULE)) {
            if (world instanceof ServerWorld && entity instanceof PlayerEntity) {

                world.getPlayers().forEach(playerEntity -> {
                    playerEntity.sendMessage(entity.getDisplayName().copy().append(" tried entering the end. Laugh at this user."), true);
                });
                // Play the alert to everyone in the world :clueless:
                world.playSound(null, pos, SoundEvents.ENTITY_SKELETON_HORSE_DEATH, SoundCategory.PLAYERS, 0.2f, 0.5f);
            }

            world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);

            ci.cancel();
        }
    }
}
