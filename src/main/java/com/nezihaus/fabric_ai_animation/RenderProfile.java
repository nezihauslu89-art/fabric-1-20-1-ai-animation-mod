package com.nezihaus.fabric_ai_animation;

import java.util.Locale;

/** Render settings exported with every replay for an external or future offline renderer. */
public record RenderProfile(int width, int height, int fps, boolean shaders, boolean bones, String codec) {
    public static final RenderProfile DEFAULT = new RenderProfile(1920, 1080, 20, true, true, "h264");

    public RenderProfile {
        if (width < 320 || height < 240) throw new IllegalArgumentException("Render resolution is too small");
        if (fps < 1 || fps > 240) throw new IllegalArgumentException("Invalid FPS");
        codec = codec == null ? "h264" : codec.toLowerCase(Locale.ROOT);
    }
}
