package com.nezihaus.fabric_ai_animation;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class FabricAiAnimationMod implements ModInitializer {
    public static final String MOD_ID = "fabric-ai-animation";
    public static final Identifier RECORDING_TOGGLE = Identifier.of(MOD_ID, "toggle_recording");

    @Override
    public void onInitialize() {
        System.out.println("[FabricAIAnimation] Initializing mod...");
    }
}
