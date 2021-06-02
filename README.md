# packetlistener
[![License](https://img.shields.io/badge/License-MIT-blue.svg?style=flat-square)](https://opensource.org/licenses/MIT)
[![Release](https://jitpack.io/v/eu.miopowered/packetlistener.svg?style=flat-square)](https://jitpack.io/#eu.miopowered/packetlistener)

Packet listener for modifiy readed and sented packets

## Installation

### Maven

```xml
<dependency>
    <groupId>eu.miopowered</groupId>
    <artifactId>packetlistener</artifactId>
    <version>1.0</version>
</dependency>
```

### Gradle

```gradle
dependencies {
  implementation 'eu.miopowered:packetlistener:1.0'
}
```

## Usage

```java
Player player = event.getPlayer();

PacketHandler.listen(PacketListener.of(player)
        .filter(PacketState.PLAY, PacketType.IN/*, PacketFilter.name("OutBlockChange") */)
        .sent((context, packet) -> System.out.println(">> " + packet.packetName()))
        .receive((context, packet) -> System.out.println("<< " + packet.packetName())));

// Remove handler on plugin reload
PacketHandler.remove(event.getPlayer());
```

## License

MIT