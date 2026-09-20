package com.nezihaus.fabric_ai_animation;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.ScreenshotRecorder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class AnimationRecorder {
    private static final double CAPTURE_RADIUS = 64.0D;
    private static boolean enabled = false;
    private static final List<FrameSnapshot> frames = new ArrayList<>();
    private static int tickCounter = 0;
    private static Path captureDirectory;

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
        if (!enabled || client.world == null || client.player == null) {
            return;
        }

        tickCounter++;
        List<PlayerSnapshot> players = new ArrayList<>();
        double radiusSquared = CAPTURE_RADIUS * CAPTURE_RADIUS;

        for (PlayerEntity player : client.world.getPlayers()) {
            if (player.squaredDistanceTo(client.player) > radiusSquared) {
                continue;
            }

            Vec3d pos = player.getPos();
            players.add(new PlayerSnapshot(
                    player.getName().getString(), pos.x, pos.y, pos.z,
                    player.getYaw(), player.getPitch(), player.isSwimming(),
                    player.isSprinting(), player.isSneaking(), player.isOnGround(),
                    player.getVelocity().length()
            ));
        }

        frames.add(new FrameSnapshot(tickCounter, players));
        captureScreenFrame(client, tickCounter - 1);
    }

    private static void captureScreenFrame(MinecraftClient client, int frameNumber) {
        if (captureDirectory == null) return;
        ScreenshotRecorder.saveScreenshot(
                captureDirectory.toFile(),
                String.format("frame_%06d", frameNumber),
                client.getFramebuffer(),
                message -> { }
        );
    }

    private static void startRecording() {
        frames.clear();
        tickCounter = 0;
        try {
            Path root = MinecraftClient.getInstance().runDirectory.toPath()
                    .resolve("config/fabric-ai-animation/captures");
            captureDirectory = root.resolve("capture_" + System.currentTimeMillis());
            Files.createDirectories(captureDirectory);
        } catch (IOException exception) {
            captureDirectory = null;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            String note = captureDirectory == null
                    ? "Recording started, but video frames could not be created"
                    : "Recording started (R to stop)";
            client.player.sendMessage(Text.of("[FabricAIAnimation] " + note), false);
        }
    }

    private static void stopRecording() {
        MinecraftClient client = MinecraftClient.getInstance();
        String json = AnimationExporter.buildJson(frames);
        String jsonPath = AnimationExporter.writeExport(json);
        String videoPath = captureDirectory == null ? "not created" :
                VideoExporter.encodeMp4(captureDirectory, client.runDirectory.toPath());

        if (client.player != null) {
            client.player.sendMessage(Text.of("[FabricAIAnimation] MP4: " + videoPath), false);
            client.player.sendMessage(Text.of("[FabricAIAnimation] Motion data: " + jsonPath), false);
        }
        captureDirectory = null;
    }

    public record PlayerSnapshot(String name, double x, double y, double z, float yaw,
                                 float pitch, boolean swimming, boolean sprinting,
                                 boolean sneaking, boolean grounded, double speed) {
        public String action() { return ActionClassifier.classify(this); }
    }

    public record FrameSnapshot(int tick, List<PlayerSnapshot> players) { }
}
