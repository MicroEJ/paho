/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.is2t.examples.mqtt;

import static com.is2t.examples.mqtt.MqttHelloWorldConstants.BROKER;
import static com.is2t.examples.mqtt.MqttHelloWorldConstants.TOPIC;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.is2t.examples.mqtt.logger.MqttHelloWorldLogger;
import com.is2t.examples.mqtt.logger.MqttHelloWorldSysoutLogger;
import com.is2t.examples.mqtt.util.NetworkChecker;

/**
 * This example connects to a MQTT broker, publishes the "Hello World!" message to the topic "MqttHelloWorld" and
 * disconnects from the MQTT broker.
 */
public final class MqttHelloWorldPublisher {

	/**
	 * Application logger.
	 */
	private static final MqttHelloWorldLogger LOGGER = new MqttHelloWorldSysoutLogger();

	// Prevents initialization.
	private MqttHelloWorldPublisher() {
	}

	public static void main(String[] args) {
		LOGGER.logInfo("Wait for network up...");

		if (NetworkChecker.waitForUp()) {
			MqttClient client = null;
			try {
				client = new MqttClient(MqttHelloWorldConstants.BROKER, MqttHelloWorldConstants.PUBLISHER_ID);
				LOGGER.logInfo("Try to connect to " + MqttHelloWorldConstants.BROKER);
				client.connect();
				LOGGER.logInfo("Client connected");
				MqttMessage message = new MqttMessage();
				message.setPayload(MqttHelloWorldConstants.HELLO_WORLD_MESSAGE.getBytes());
				client.publish(MqttHelloWorldConstants.TOPIC, message);
				LOGGER.logInfo(message + " published to " + MqttHelloWorldConstants.TOPIC);
			} catch (MqttException e) {
				LOGGER.logInfo("Unable to connect to " + BROKER + " and publish to topic " + TOPIC);
			} finally {
				if (client != null) {
					try {
						client.disconnect();
						LOGGER.logInfo("Client disconnected");
					} catch (MqttException e) {
						// Ignored.
					}
				}
			}
		} else {
			LOGGER.logInfo("Network unavailable");
		}
	}
}
