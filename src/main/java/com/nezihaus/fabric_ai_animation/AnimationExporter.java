package com.nezihaus.fabric_ai_animation;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class AnimationExporter {
    private AnimationExporter() {
    }

    public static String buildJson(List<AnimationRecorder.ReplayFrame> frames) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"format\": \"fabric-motion-replay-v1\",\n");
        sb.append("  \"mod\": \"fabric-ai-animation\",\n");
        sb.append("  \"frameCount\": ").append(frames.size()).append(",\n");
        sb.append("  \"camera\": [\n");

        for (int i = 0; i < frames.size(); i++) {
            AnimationRecorder.ReplayFrame frame = frames.get(i);
            sb.append("    {\n");
            sb.append("      \"tick\": ").append(frame.tick()).append(",\n");
            sb.append("      \"position\": [").append(formatDouble(frame.cameraX())).append(", ")
                    .append(formatDouble(frame.cameraY())).append(", ")
                    .append(formatDouble(frame.cameraZ())).append("],\n");
            sb.append("      \"rotation\": [").append(formatDouble(frame.cameraYaw())).append(", ")
                    .append(formatDouble(frame.cameraPitch())).append("]\n");
            sb.append("    }");
            if (i < frames.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("  ],\n");

        sb.append("  \"actors\": [\n");
        for (int i = 0; i < frames.size(); i++) {
            AnimationRecorder.ReplayFrame frame = frames.get(i);
            sb.append("    {\n");
            sb.append("      \"tick\": ").append(frame.tick()).append(",\n");
            sb.append("      \"players\": [");

            List<AnimationRecorder.PlayerSnapshot> players = frame.players();
            for (int j = 0; j < players.size(); j++) {
                AnimationRecorder.PlayerSnapshot player = players.get(j);
                sb.append("{\n");
                sb.append("        \"name\": \"").append(escape(player.name())).append("\",\n");
                sb.append("        \"position\": [").append(formatDouble(player.x())).append(", ")
                        .append(formatDouble(player.y())).append(", ")
                        .append(formatDouble(player.z())).append("],\n");
                sb.append("        \"rotation\": [").append(formatDouble(player.yaw())).append(", ")
                        .append(formatDouble(player.pitch())).append("],\n");
                sb.append("        \"action\": \"").append(player.action()).append("\",\n");
                sb.append("        \"state\": {\n");
                sb.append("          \"sprinting\": ").append(player.sprinting()).append(",\n");
                sb.append("          \"sneaking\": ").append(player.sneaking()).append(",\n");
                sb.append("          \"swimming\": ").append(player.swimming()).append(",\n");
                sb.append("          \"grounded\": ").append(player.grounded()).append(",\n");
                sb.append("          \"speed\": ").append(formatDouble(player.speed())).append("\n");
                sb.append("        }\n");
                sb.append("      }");
                if (j < players.size() - 1) {
                    sb.append(",");
                }
            }

            sb.append("]\n");
            sb.append("    }");
            if (i < frames.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("  ]\n");
        sb.append("}\n");
        return sb.toString();
    }

    public static String writeExport(String json) {
        try {
            Path dir = FabricLoader.getInstance().getConfigDir().resolve("fabric-ai-animation");
            Files.createDirectories(dir);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String fileName = "replay_" + LocalDateTime.now().format(formatter) + ".json";
            Path out = dir.resolve(fileName);
            Files.writeString(out, json, StandardCharsets.UTF_8);
            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write replay export", e);
        }
    }

    private static String formatDouble(double value) {
        return String.format(java.util.Locale.US, "%.4f", value);
    }

    private static String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
