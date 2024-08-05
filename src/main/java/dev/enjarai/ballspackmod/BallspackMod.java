package dev.enjarai.ballspackmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import symbolics.division.spirit.vector.SpiritVectorItems;
import symbolics.division.spirit.vector.SpiritVectorTags;

import java.util.List;
import java.util.Set;

public class BallspackMod implements ModInitializer, ClientModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final String MOD_ID = "ballspack_mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Set<Identifier> SPIRIT_VECTOR_LOOT_TABLES = Set.of(
            Identifier.ofVanilla("chests/ancient_city"),
            Identifier.ofVanilla("chests/bastion_bridge"),
            Identifier.ofVanilla("chests/bastion_other"),
            Identifier.ofVanilla("chests/bastion_treasure"),
            Identifier.ofVanilla("chests/desert_pyramid"),
            Identifier.ofVanilla("chests/end_city_treasure"),
            Identifier.ofVanilla("chests/jungle_temple"),
            Identifier.ofVanilla("chests/nether_bridge"),
            Identifier.ofVanilla("chests/shipwreck_treasure"),
            Identifier.ofVanilla("chests/simple_dungeon"),
            Identifier.ofVanilla("chests/stronghold_corridor"),
            Identifier.ofVanilla("chests/stronghold_crossing"),
            Identifier.ofVanilla("chests/woodland_mansion"),
            Identifier.ofVanilla("chests/abandoned_mineshaft"),
            Identifier.ofVanilla("chests/trial_chambers/reward_rare"),
            Identifier.ofVanilla("chests/trial_chambers/reward_ominous_rare")
    );
    public static final GameRules.Key<GameRules.BooleanRule> ENTER_END_GAMERULE =
            GameRuleRegistry.register("ballspack_mod:preventEndEntering", GameRules.Category.MISC,
                    GameRuleFactory.createBooleanRule(true));

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (SPIRIT_VECTOR_LOOT_TABLES.contains(key.getValue())) {
                tableBuilder.pool(LootPool.builder()
                        .conditionally(RandomChanceLootCondition.builder(0.6f))
                        .rolls(UniformLootNumberProvider.create(1, 3))
                        .with(List.of(
                                ItemEntry.builder(SpiritVectorItems.SPIRIT_VECTOR).weight(12).build(),
                                ItemEntry.builder(SpiritVectorItems.COSMETIC_WINGS_RUNE).weight(6).build(),
                                ItemEntry.builder(SpiritVectorItems.TAKE_BREAK_CASSETTE).weight(4).build(),
                                ItemEntry.builder(SpiritVectorItems.SHOW_DONE_SONG).weight(4).build()
                        ))
                        .with(registries.createRegistryLookup().getOrThrow(RegistryKeys.ITEM)
                                .getOrThrow(SpiritVectorTags.Items.SFX_PACK_TEMPLATES)
                                .stream()
                                .map(e -> ItemEntry.builder(e.value())
                                        .weight(2)
                                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 3)))
                                        .build()
                                )
                                .toList()
                        )
                        .with(registries.createRegistryLookup().getOrThrow(RegistryKeys.ITEM)
                                .getOrThrow(SpiritVectorTags.Items.ABILITY_UPGRADE_RUNES)
                                .stream()
                                .map(e -> ItemEntry.builder(e.value())
                                        .weight(2)
                                        .build()
                                )
                                .toList()
                        )
                        .with(registries.createRegistryLookup().getOrThrow(RegistryKeys.ITEM)
                                .getOrThrow(SpiritVectorTags.Items.SLOT_UPGRADE_RUNES)
                                .stream()
                                .map(e -> ItemEntry.builder(e.value())
                                        .weight(4)
                                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 2)))
                                        .build()
                                )
                                .toList()
                        )
                        .build());
            }
        });

        LOGGER.info("The balls are of significant size today.");
    }

    @Override
    public void onInitializeClient() {
        ResourceManagerHelper.registerBuiltinResourcePack(id("flavor"),
                FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(),
                Text.literal("Ballspack 5 Flavor Pack"), ResourcePackActivationType.DEFAULT_ENABLED);
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}