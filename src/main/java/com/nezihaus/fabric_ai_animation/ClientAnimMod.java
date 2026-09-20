package com.nezihaus.fabric_ai_animation;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ClientAnimMod implements ClientModInitializer {
    private static KeyBinding recordingToggle;

    @Override
    public void onInitializeClient() {
        recordingToggle = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.fabric-ai-animation.toggle_recording",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.fabric-ai-animation.main"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (recordingToggle.wasPressed()) {
                AnimationRecorder.toggle();
            }

            AnimationRecorder.tick(client);
        });

        System.out.println("[FabricAIAnimation] Client initializer ready. Config dir: " + FabricLoader.getInstance().getConfigDir());
    }
}
