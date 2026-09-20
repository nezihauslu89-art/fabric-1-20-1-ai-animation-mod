package com.nezihaus.fabric_ai_animation;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public final class AnimationRecorder {
    private static boolean enabled = false;
    private static final List<FrameSnapshot> frames = new ArrayList<>();
    private static int tickCounter = 0;

    private AnimationRecorder() {
    }

    public static void toggle() {
        enabled = !enabled;
        if (enabled) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    public static void tick(MinecraftClient client) {
        if (!enabled || client.world == null) {
            return;
        }

        tickCounter++;
        List<PlayerSnapshot> players = new ArrayList<>();

        for (PlayerEntity player : client.world.getPlayers()) {
            Vec3d pos = player.getPos();
            players.add(new PlayerSnapshot(
                    player.getName().getString(),
                    pos.x,
                    pos.y,
                    pos.z,
                    player.getYaw(),
                    player.getPitch(),
                    player.isSwimming(),
                    player.isSprinting(),
                    player.isSneaking(),
                    player.isOnGround(),
                    player.getVelocity().length()
            ));
        }

        frames.add(new FrameSnapshot(tickCounter, players));
    }

    private static void startRecording() {
        frames.clear();
        tickCounter = 0;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            client.player.sendMessage(Text.of("[FabricAIAnimation] Recording started"), false);
        }
    }

    private static void stopRecording() {
        MinecraftClient client = MinecraftClient.getInstance();
        String export = AnimationExporter.buildJson(frames);
        String path = AnimationExporter.writeExport(export);

        if (client.player != null) {
            client.player.sendMessage(Text.of("[FabricAIAnimation] Recording stopped. Export written to: " + path), false);
        }
    }

    public record PlayerSnapshot(
            String name,
            double x,
            double y,
            double z,
            float yaw,
            float pitch,
            boolean swimming,
            boolean sprinting,
            boolean sneaking,
            boolean grounded,
            double speed
    ) {
        public String action() {
            return ActionClassifier.classify(this);
        }
    }

    public record FrameSnapshot(int tick, List<PlayerSnapshot> players) {
    }
}
