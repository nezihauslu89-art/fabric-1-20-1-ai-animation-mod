# Fabric AI Animation Replay Camera

This repo is now focused on a real replay-style motion capture system for Fabric 1.20.1.

What is included:
- Camera position + rotation capture
- Nearby player tracking within a radius
- Motion/action classifier (`idle`, `walk`, `run`, `jump`, `crouch`, `attack`, `turn`)
- JSON export of replay data in a render-friendly structure
- Optional MP4 generation using FFmpeg from captured screenshots

Important technical note:
A single Fabric client mod cannot create a true 3D skeletal bone renderer or a full shader-based cinematic engine by itself. A full “bone + shader + replay render” pipeline requires:
- offline render passes,
- custom animation/model export,
- shader stages,
- a dedicated renderer or compositing pipeline.

This version gives you the correct foundation for that system: it records camera motion, nearby actor motion, and high-value animation metadata in a clean JSON replay format. That is the correct base for a later offline renderer or external animation engine.

Requirements:
1. JDK 17
2. Fabric Loader + Fabric API 1.20.1
3. FFmpeg installed and on PATH if you want MP4 output
4. Gradle build tools

Run:
- gradle clean build

Output:
- built jar goes to `build/libs/`
- replay JSON is stored under `%APPDATA%/.minecraft/config/fabric-ai-animation/`
- captured screenshots and video output are under `%APPDATA%/.minecraft/config/fabric-ai-animation/captures/`

In-game controls:
- Press `R` to start/stop recording.

This repo is intentionally the replay foundation, not a fake “magic AI animation creator.” The AI layer here is a real motion classifier, while the camera + actor motion export is the data required for a later bone/renderer pipeline.
