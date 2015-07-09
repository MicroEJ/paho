/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.is2t.examples.mqtt;

import static com.is2t.examples.mqtt.MqttHelloWorldConstants.Broker;
import static com.is2t.examples.mqtt.MqttHelloWorldConstants.SubscriberId;
import static com.is2t.examples.mqtt.MqttHelloWorldConstants.Topic;

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
public class MqttHelloWorldSubscriber {

	/**
	 * Application logger.
	 */
	public static MqttHelloWorldLogger Logger = new MqttHelloWorldSysoutLogger();

	public static void main(String[] args) {
		Logger.logInfo("Wait for network up...");

		if (NetworkChecker.waitForUp()) {
			MqttClient client = null;
			try {
				client = new MqttClient(Broker, SubscriberId);
				client.setCallback(new MqttCallback() {

					@Override
					public void messageArrived(String topic, MqttMessage message) throws Exception {
						Logger.logInfo(new String(message.getPayload()) + " received from topic " + topic);
					}

					@Override
					public void deliveryComplete(IMqttDeliveryToken token) {
						Logger.logInfo("delivery complete: " + token);
					}

					@Override
					public void connectionLost(Throwable cause) {
						Logger.logInfo("connection lost: " + cause);
					}
				});
				Logger.logInfo("Try to connect to " + MqttHelloWorldConstants.Broker);
				client.connect();
				Logger.logInfo("Client connected");
				client.subscribe(Topic);
				Logger.logInfo("Client subscribed to " + Topic);
			} catch (MqttException e) {
				e.printStackTrace();
			}
		} else {
			Logger.logInfo("Network unavailable");
		}
	}
}
