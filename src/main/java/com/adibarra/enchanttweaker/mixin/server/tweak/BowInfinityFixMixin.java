package com.adibarra.enchanttweaker.mixin.server.tweak;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.item.BowItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @description Lets bows with Infinity enchant shoot without arrows.
 * @environment Server
 */
@Mixin(value=BowItem.class, priority=1543)
public abstract class BowInfinityFixMixin {

    @ModifyExpressionValue(
        method="use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;",
        at=@At(
            value="INVOKE",
            target="Lnet/minecraft/item/ItemStack;isEmpty()Z"))
    private boolean bowInfinityFix(boolean orig) {
        return false;
    }
}