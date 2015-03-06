/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.eclipse.paho.client.mqttv3.internal;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public interface NetworkModuleFactory {

	/**
	 * Factory methods to create the correct network module, based on the supplied address URI.
	 * 
	 * @param address the URI of the server.
	 * @param options the connection options.
	 * @param clientId the client identifier that is unique on the server being connected to.
	 * @return a network module appropriate to the specified address.
	 * @throws MqttException
	 */
	NetworkModule createNetworkModule(String address,
			MqttConnectOptions options, String clientId) throws MqttException;
}
