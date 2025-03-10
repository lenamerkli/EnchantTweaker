package com.adibarra.enchanttweaker.mixin.server.capmod;

import com.adibarra.enchanttweaker.ETMixinPlugin;
import com.adibarra.utils.ADMath;
import com.adibarra.utils.CheckCallstack;
import net.minecraft.enchantment.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @description Modify enchantment level cap. (Special cases, not all enchantments override getMaxLevel())
 * @environment Server
 */
@Mixin(value={
    AquaAffinityEnchantment.class, BindingCurseEnchantment.class, ChannelingEnchantment.class,
    FlameEnchantment.class,        InfinityEnchantment.class,     MendingEnchantment.class,
    MultishotEnchantment.class,    SilkTouchEnchantment.class,    VanishingCurseEnchantment.class
})
public abstract class SpecialEnchantMixin extends Enchantment {

    @SuppressWarnings("unused")
    protected SpecialEnchantMixin(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(weight, target, slotTypes);
    }

    // VERSION CHANGES:
    // 1.16+: Registry
    // 1.19.3+: Registries
    @Override
    public int getMaxLevel() {
        int orig = super.getMaxLevel();
        if (CheckCallstack.merchantOrLoot()){
            return orig;
        }
        if (Registries.ENCHANTMENT.getKey(this).isEmpty()) return orig;
        String key = Registries.ENCHANTMENT.getKey(this).get().getValue().getPath();
        int lvlCap = ETMixinPlugin.getConfig().getOrDefault(key, orig);
        if (lvlCap < 0) return orig;
        return ADMath.clamp(lvlCap, 0, 255);
    }
}
