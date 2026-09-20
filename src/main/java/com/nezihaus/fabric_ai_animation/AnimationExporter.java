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

    public static String buildJson(List<AnimationRecorder.FrameSnapshot> frames) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"format\": \"mineimator-scaffold\",\n");
        sb.append("  \"mod\": \"fabric-ai-animation\",\n");
        sb.append("  \"frameCount\": ").append(frames.size()).append(",\n");
        sb.append("  \"frames\": [\n");

        for (int i = 0; i < frames.size(); i++) {
            AnimationRecorder.FrameSnapshot frame = frames.get(i);
            sb.append("    {\n");
            sb.append("      \"tick\": ").append(frame.tick()).append(",\n");
            sb.append("      \"players\": [");

            List<AnimationRecorder.PlayerSnapshot> players = frame.players();
            for (int j = 0; j < players.size(); j++) {
                AnimationRecorder.PlayerSnapshot player = players.get(j);
                sb.append("{\n");
                sb.append("        \"name\": \"").append(escape(player.name())).append("\",\n");
                sb.append("        \"x\": ").append(formatDouble(player.x())).append(",\n");
                sb.append("        \"y\": ").append(formatDouble(player.y())).append(",\n");
                sb.append("        \"z\": ").append(formatDouble(player.z())).append(",\n");
                sb.append("        \"yaw\": ").append(formatDouble(player.yaw())).append(",\n");
                sb.append("        \"pitch\": ").append(formatDouble(player.pitch())).append(",\n");
                sb.append("        \"action\": \"").append(player.action()).append("\"\n");
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
            String fileName = "capture_" + LocalDateTime.now().format(formatter) + ".miproject.json";
            Path out = dir.resolve(fileName);
            Files.writeString(out, json, StandardCharsets.UTF_8);
            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write export", e);
        }
    }

    private static String formatDouble(double value) {
        return String.format(java.util.Locale.US, "%.4f", value);
    }

    private static String escape(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}
