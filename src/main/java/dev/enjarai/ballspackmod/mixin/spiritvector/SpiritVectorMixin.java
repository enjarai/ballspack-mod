package dev.enjarai.ballspackmod.mixin.spiritvector;

import com.google.common.collect.Iterators;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import symbolics.division.spirit.vector.logic.SpiritVector;

@Mixin(SpiritVector.class)
public abstract class SpiritVectorMixin {
    @WrapOperation(method = "getEquippedItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getEquippedStack(Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/item/ItemStack;"))
    private static ItemStack ignoreTransmog(LivingEntity instance, EquipmentSlot equipmentSlot, Operation<ItemStack> original) {
        if(FabricLoader.getInstance().isModLoaded("transmog")) {
            return Iterators.toArray(instance.getArmorItems().iterator(), ItemStack.class)[equipmentSlot.getEntitySlotId()];
        }
        return original.call(instance, equipmentSlot);
    }
}
