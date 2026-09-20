package com.nezihaus.fabric_ai_animation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public final class VideoExporter {
    private VideoExporter() { }

    public static String encodeMp4(Path frames, Path runDirectory) {
        Path output = frames.resolveSibling(frames.getFileName() + ".mp4");
        ProcessBuilder process = new ProcessBuilder(
                "ffmpeg", "-y", "-framerate", "20", "-i",
                frames.resolve("frame_%06d.png").toString(),
                "-c:v", "libx264", "-pix_fmt", "yuv420p", output.toString()
        );
        process.redirectErrorStream(true);

        try {
            Process running = process.start();
            int exitCode = running.waitFor();
            if (exitCode != 0 || !Files.exists(output)) {
                return "not created (install FFmpeg and add it to PATH)";
            }
            deleteFrames(frames);
            return output.toString();
        } catch (IOException exception) {
            return "not created (FFmpeg is missing; install it and add ffmpeg to PATH)";
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            return "not created (encoding interrupted)";
        }
    }

    private static void deleteFrames(Path frames) throws IOException {
        try (var paths = Files.walk(frames)) {
            paths.sorted(Comparator.reverseOrder()).forEach(path -> {
                try { Files.deleteIfExists(path); }
                catch (IOException ignored) { }
            });
        }
    }
}
