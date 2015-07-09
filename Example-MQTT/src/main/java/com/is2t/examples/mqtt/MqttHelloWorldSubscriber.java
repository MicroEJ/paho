/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.is2t.examples.mqtt;

import static com.is2t.examples.mqtt.MqttHelloWorldConstants.BROKER;
import static com.is2t.examples.mqtt.MqttHelloWorldConstants.SUBSCRIBER_ID;
import static com.is2t.examples.mqtt.MqttHelloWorldConstants.TOPIC;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.is2t.examples.mqtt.logger.MqttHelloWorldLogger;
import com.is2t.examples.mqtt.logger.MqttHelloWorldSysoutLogger;
import com.is2t.examples.mqtt.util.NetworkChecker;

/**
 * This example connects to a MQTT broker, creates a callback and subscribes to the topic "MqttHelloWorld".
 */
public final class MqttHelloWorldSubscriber {

	/**
	 * Application logger.
	 */
	private static final MqttHelloWorldLogger LOGGER = new MqttHelloWorldSysoutLogger();

	// Prevents initialization.
	private MqttHelloWorldSubscriber() {
	}

	public static void main(String[] args) {
		LOGGER.logInfo("Wait for network up...");

		if (NetworkChecker.waitForUp()) {
			MqttClient client = null;
			try {
				client = new MqttClient(BROKER, SUBSCRIBER_ID);
				client.setCallback(new MqttCallback() {

					@Override
					public void messageArrived(String topic, MqttMessage message) throws Exception {
						LOGGER.logInfo(new String(message.getPayload()) + " received from topic " + topic);
					}

					@Override
					public void deliveryComplete(IMqttDeliveryToken token) {
						LOGGER.logInfo("delivery complete: " + token);
					}

					@Override
					public void connectionLost(Throwable cause) {
						LOGGER.logInfo("connection lost: " + cause);
					}
				});
				LOGGER.logInfo("Try to connect to " + MqttHelloWorldConstants.BROKER);
				client.connect();
				LOGGER.logInfo("Client connected");
				client.subscribe(TOPIC);
				LOGGER.logInfo("Client subscribed to " + TOPIC);
			} catch (MqttException e) {
				LOGGER.logInfo("Unable to connect to " + BROKER + " and subscribe to topic " + TOPIC);
				if (client != null) {
					try {
						client.disconnect();
						LOGGER.logInfo("Client disconnected");
					} catch (MqttException e2) {
						// Ignored.
					}
				}
			}
		} else {
			LOGGER.logInfo("Network unavailable");
		}
	}
}
