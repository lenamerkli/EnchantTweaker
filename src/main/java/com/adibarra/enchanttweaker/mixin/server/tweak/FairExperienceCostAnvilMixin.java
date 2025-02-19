package com.adibarra.enchanttweaker.mixin.server.tweak;


import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AnvilScreenHandler.class)
public abstract class FairExperienceCostAnvilMixin extends ForgingScreenHandler {

    public FairExperienceCostAnvilMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    @WrapWithCondition(method = "onTakeOutput", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addExperienceLevels(I)V"))
    private boolean enchantTweaker$fairExperienceCost(PlayerEntity instance, int levels) {
        this.player.addExperience(-enchantTweaker$getExperienceTotal(-levels));
        return false;
    }

    @Unique
    private static int enchantTweaker$getExperienceTotal(int level) {
        int total = 0;
        for (int i = 0; i < level ; i++) {
            if (i >= 30) {
                total += 112 + (i - 30) * 9;
            } else if (i >= 15) {
                total += 37 + (i - 15) * 5;
            } else {
                total += 7 + i * 2;
            }
        }
        return total;
    }
}
