package dev.enjarai.ballspackmod.mixin;

import dev.enjarai.ballspackmod.BallspackMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnderEyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderEyeItem.class)
public class EnderEyeItemMixin {
    @Inject(
            method = "useOnBlock",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cancelFrameInsertion(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (!context.getWorld().isClient() && context.getWorld().getGameRules().getBoolean(BallspackMod.PREVENT_END_GAMERULE)) {
            var player = context.getPlayer();
            if (player != null) {
                player.sendMessage(Text.of("The End is currently closed."), true);
            }

            cir.setReturnValue(ActionResult.FAIL);
        }
    }

    @Inject(
            method = "use",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cancelEnderEyeThrowing(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (!world.isClient() && world.getGameRules().getBoolean(BallspackMod.PREVENT_END_GAMERULE)) {
            user.sendMessage(Text.of("The End is currently closed."), true);

            cir.setReturnValue(TypedActionResult.fail(user.getStackInHand(hand)));
        }
    }
}
