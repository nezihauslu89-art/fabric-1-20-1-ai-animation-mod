# Fabric AI Animation — hızlı kullanım

Repo: https://github.com/nezihauslu89-art/fabric-1-20-1-ai-animation-mod

## Derleme

Windows: `build-windows.bat`  
Linux/macOS: `bash build-linux-mac.sh`

Gereksinim: JDK 17, Gradle 8.7+, Fabric 1.20.1 ve MP4 için FFmpeg.

## Mod içinde

- `R`: kayıt başlat/durdur
- Yakındaki oyuncuların hareketleri ve kamera her tick kaydedilir.
- JSON replay verisi yazılır.
- Ekran kareleri FFmpeg ile H.264 MP4'e dönüştürülür.
- Replay verisi gelecekteki bone/shader/offline renderer için kamera, actor ve `RenderProfile` alanlarını içerir.

Çıktılar `.minecraft/config/fabric-ai-animation/` altındadır.

Bu sürüm gerçek Minecraft görüntüsünü MP4'e çevirir ve replay verisini çıkarır; Minecraft dışında sahne yeniden render eden bağımsız bir renderer değildir.
