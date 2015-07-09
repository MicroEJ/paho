# Overview
Paho MicroEJ client for MQTT:
- This is a fork of Paho: https://eclipse.org/paho/clients/java/
- Implements MQTT 3.1.1


Some useful tutorials:
- http://www.infoq.com/articles/practical-mqtt-with-paho
- https://developer.motorolasolutions.com/docs/DOC-2315 android oriented

	
## Setup
In addition of adding the library in your buildpath, you have to configure your launch.

### Dependency injection:
The library requires some implementations. It comes with default implementations but you can precise yours. 
To precise your own implementation you have to create a system property with the required interface as the key and the corresponding implementation as the value.
To define a system property see the section 6.1 of the MicroEJ Platform Architecture Reference Manual.
	
The library requires an implementation for the following interfaces:
- org.eclipse.paho.client.mqttv3.MqttClientPersistence
- org.eclipse.paho.client.mqttv3.internal.NetworkModuleFactory
- org.eclipse.paho.client.mqttv3.internal.MessageCatalog
- org.eclipse.paho.client.mqttv3.logging.InternalLoggerFactory
- org.eclipse.paho.client.mqttv3.util.SystemProperties

For information about this interfaces see the corresponding javadoc.

example: org.eclipse.paho.client.mqttv3.MqttClientPersistence=org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

The default implementations are defined in the `dependencyinjection.properties` file in the package `org.eclipse.paho.client.mqttv3.internal.dependencyinjection` of `mqttv3-microej-version.jar`.
	
In your launch, in the `Main` tab:
- add the chosen implementations in the `Required types` part.
- add the file containing the default implementations in the `Resources` part.

### Threads
Paho requires 4 threads to run. You have to adapt the value `Number of threads` of your launch to take this in account. You can modify this value by going in the `Configuration` tab in the `Target > Memory` section.

### UTF-8
Paho requires UTF-8 encoding to run. In the `Configuration` tab in the `EDC` section check `Embed UTF-8 encoding`.