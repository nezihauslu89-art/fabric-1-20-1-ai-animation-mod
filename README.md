# Fabric AI Animation

This repo is a starter Fabric 1.20.1 mod scaffold for recording nearby player animations and exporting a Mine-imator-friendly JSON structure.

What is included:
- Fabric 1.20.1 project files
- Client-side recorder for nearby players
- Simple action classification (idle, walk, run, jump, turn)
- Export to JSON with frame-by-frame movement data
- Keybind to start/stop recording

Important note:
- This is a solid foundation, not a magical black-box AI-to-Mine-Imator generator.
- The current AI part is a deterministic motion classifier. If you later want a real LLM-based action naming layer, you can attach an external API or a local model.
- A true .miproject file is a specific JSON/ZIP format used by Mine-imator. This scaffold builds the format structure you can adapt for export.

Quick start:
1. Install JDK 17
2. Install Gradle 8.7+ or use the Gradle wrapper after bootstrapping it locally
3. Run:
   gradle clean build
4. The built jar will appear under build/libs/

In-game controls:
- Press the configured keybind to start/stop recording
- The export is written to:
  %APPDATA%/.minecraft/config/fabric-ai-animation/

Files added so far:
- Fabric mod bootstrap
- Client animation recorder
- Simple export logic
- README with setup instructions

Next steps you can do in the same repo:
- Add a real Mine-imator .miproject exporter
- Add AI prompt-based action labeling
- Add more animation classes (sneak, attack, damage, crouch, use item)
- Add GUI or config screen
