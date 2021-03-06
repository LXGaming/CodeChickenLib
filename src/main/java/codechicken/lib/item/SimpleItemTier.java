package codechicken.lib.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

/**
 * Created by covers1624 on 14/12/20.
 */
public class SimpleItemTier implements IItemTier {

    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int harvestLevel;
    private final int enchantability;
    private final LazyValue<Ingredient> repairMaterial;

    public SimpleItemTier(int maxUses, float efficiency, float attackDamage, int harvestLevel, int enchantability, Supplier<Ingredient> repairMaterial) {
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.harvestLevel = harvestLevel;
        this.enchantability = enchantability;
        this.repairMaterial = new LazyValue<>(repairMaterial);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(IItemTier other) {
        return builder().from(other);
    }

    @Override
    public int getMaxUses() {
        return maxUses;
    }

    @Override
    public float getEfficiency() {
        return efficiency;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }

    @Override
    public int getHarvestLevel() {
        return harvestLevel;
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return repairMaterial.getValue();
    }

    public static class Builder {

        private int maxUses;
        private float efficiency;
        private float attackDamage;
        private int harvestLevel;
        private int enchantability;
        private Supplier<Ingredient> repairMaterial;

        private Builder() {
        }

        public Builder from(IItemTier other) {
            maxUses(other.getMaxUses());
            efficiency(other.getEfficiency());
            attackDamage(other.getAttackDamage());
            harvestLevel(other.getHarvestLevel());
            enchantability(other.getEnchantability());
            repairMaterial(other::getRepairMaterial);
            return this;
        }

        public Builder maxUses(int maxUses) {
            this.maxUses = maxUses;
            return this;
        }

        public Builder efficiency(float efficiency) {
            this.efficiency = efficiency;
            return this;
        }

        public Builder attackDamage(float attackDamage) {
            this.attackDamage = attackDamage;
            return this;
        }

        public Builder harvestLevel(int harvestLevel) {
            this.harvestLevel = harvestLevel;
            return this;
        }

        public Builder enchantability(int enchantability) {
            this.enchantability = enchantability;
            return this;
        }

        public Builder repairMaterial(Supplier<Ingredient> repairMaterial) {
            this.repairMaterial = repairMaterial;
            return this;
        }

        public SimpleItemTier build() {
            return new SimpleItemTier(maxUses, efficiency, attackDamage, harvestLevel, enchantability, repairMaterial);
        }
    }
}
