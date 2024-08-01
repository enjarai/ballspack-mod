package dev.enjarai.ballspackmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BallspackMod implements ModInitializer, ClientModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "ballspack_mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

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