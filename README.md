# Fabric AI Animation

Fabric 1.20.1 client mod that records nearby players and creates an MP4 screen capture.

## What it does

- Press **R** to start/stop recording.
- Captures the Minecraft window framebuffer at 20 FPS.
- Tracks players within 64 blocks and writes motion/action data to JSON.
- On stop, invokes **FFmpeg** to turn the PNG frames into an `.mp4`.
- The MP4 is a video of what your Minecraft client sees. It is not a separate Mine-imator 3D render.

## Install requirements

1. Java 17
2. Fabric Loader/API for Minecraft 1.20.1
3. FFmpeg installed and available as `ffmpeg` in the system PATH
4. Build with `gradle clean build` (or the included Gradle wrapper when present)

## Output

Files are written under:

`%APPDATA%/.minecraft/config/fabric-ai-animation/captures/`

The motion data JSON is written under:

`%APPDATA%/.minecraft/config/fabric-ai-animation/`

If FFmpeg is not installed, the mod still saves the motion JSON and keeps a PNG frame folder; it reports that the MP4 could not be created.

## Important limitation

A client-side Fabric mod can reliably make a screen/video capture. Producing a freely movable camera animation or a full 3D render of every player would require a replay/renderer pipeline rather than a simple MP4 encoder. This version records the current Minecraft viewpoint, while also tracking nearby-player motion for later animation processing.
