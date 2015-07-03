/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.is2t.examples.mqtt;

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
public class MqttHelloWorldPublisher {

	/**
	 * Application logger.
	 */
	public static MqttHelloWorldLogger Logger = new MqttHelloWorldSysoutLogger();

	public static void main(String[] args) {
		Logger.logInfo("Wait for network up...");

		if (NetworkChecker.waitForUp()) {
			MqttClient client = null;
			try {
				client = new MqttClient(MqttHelloWorldConstants.Broker, MqttHelloWorldConstants.PublisherId);
				Logger.logInfo("Try to connect to " + MqttHelloWorldConstants.Broker);
				client.connect();
				Logger.logInfo("Client connected");
				MqttMessage message = new MqttMessage();
				message.setPayload(MqttHelloWorldConstants.HelloWorldMessage.getBytes());
				client.publish(MqttHelloWorldConstants.Topic, message);
				Logger.logInfo(message + " published to " + MqttHelloWorldConstants.Topic);
			} catch (MqttException e) {
				e.printStackTrace();
			} finally {
				if (client != null) {
					try {
						client.disconnect();
						Logger.logInfo("Client disconnected");
					} catch (MqttException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			Logger.logInfo("Network unavailable");
		}
	}
}
