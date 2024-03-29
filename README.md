# packetlistener
[![License](https://img.shields.io/badge/License-MIT-blue.svg?style=flat-square)](https://opensource.org/licenses/MIT)
[![Release](https://jitpack.io/v/eu.miopowered/packetlistener.svg?style=flat-square)](https://jitpack.io/#eu.miopowered/packetlistener)

Packet listener for modifiy readed and sented packets

## Installation

### Maven

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>eu.miopowered</groupId>
    <artifactId>packetlistener</artifactId>
    <version>2.0</version>
</dependency>
<!-- You should add joor, if you want to access joor in your ide to modify packets without direct reflection -->
<dependency>
    <groupId>org.jooq</groupId>
    <artifactId>joor-java-8</artifactId>
    <version>0.9.13</version>
</dependency>
```

### Gradle

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}
		
dependencies {
  implementation 'eu.miopowered:packetlistener:2.0'
  
  // You should add joor, if you want to access joor in your ide to modify packets without direct reflection
  implementation 'org.jooq:joor-java-8:0.9.13'
}
```

## Usage

### Simple packet listening

```java
Player player = event.getPlayer();

PacketTarget.listen(PacketListener.of(player)
        .filter(PacketState.PLAY, PacketType.IN/*, PacketFilter.name("OutBlockChange") */)
        .sent((context, packet) -> {
            System.out.println(">> " + packet.packetName());
            return true; // false means that the packet wont be sent
        })
        .receive((context, packet) -> {
            System.out.println("<< " + packet.packetName());
            return true; // false means that the packet wont be used
        }));

// Remove handler on plugin reload
        
PacketTarget.remove(event.getPlayer());
```

### Event system

Instead of listening you can check the events for sent / received packets

Register the library in your enable
```java 
EventListener.register(javaPlugin)
```

You than can use the following events for modifying packets:

``PacketReceiveEvent``,
``PacketSentEvent``

Example
```java
    @EventHandler
    public void handle(PacketSentEvent event) {
        if (event.packet().state() == PacketState.PLAY
                && event.packet().packetName().equals("PacketPlayOutTitle")) {
            event.packet().reflect().set("a", "some reflection manipulation");
        }
    }
```

As you see, the joor reflection library is added and you can modifiy the packet via reflect method.

### Player packet actions

You can perform various actions like actionbar and titles with this library

```java
PacketPlayer packetPlayer = PacketPlayer.of(player);

packetPlayer.sendFullTitle(String title, String subtitle, int fadeInTime, int showTime, int fadeOutTime)
packetPlayer.sendActionbar(String text)
```

Everything should be version independent because of reflection.

## License

MIT