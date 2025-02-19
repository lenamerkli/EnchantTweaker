package com.adibarra.enchanttweaker.mixin.server.tweak;

import com.adibarra.enchanttweaker.EnchantTweaker;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntity.class)
public abstract class FairExperienceCostTableMixin extends LivingEntity {
    @Shadow public int experienceLevel;
    @Shadow public float experienceProgress;
    @Shadow public int totalExperience;
    @Shadow protected int enchantmentTableSeed;

    protected FairExperienceCostTableMixin(EntityType<? extends LivingEntity> $$0, World $$1) {
        super($$0, $$1);
    }

    /**
     * @author Lena Merkli
     * @reason Make the enchantment cost more fair
     */
    @Overwrite
    public void applyEnchantmentCosts(ItemStack enchantedItem, int experienceLevels) {
        EnchantTweaker.LOGGER.info("Applying mixin FairExperienceCostMixin");
        int min = Math.min(30, this.experienceLevel);
        this.experienceLevel -= enchantTweaker$getExperienceTotal(min - experienceLevels, min);
        if (this.experienceLevel < 0) {
            this.experienceLevel = 0;
            this.experienceProgress = 0.0F;
            this.totalExperience = 0;
        }
        this.enchantmentTableSeed = this.random.nextInt();
    }

    @Unique
    private static int enchantTweaker$getExperienceTotal(int startingAt, int endingAt) {
        int total = 0;
        for (int i = startingAt; i < endingAt ; i++) {
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
