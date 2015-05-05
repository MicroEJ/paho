/*******************************************************************************
 * Copyright (c) 2015 IS2T.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution. 
 *
 * The Eclipse Public License is available at 
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 *   http://www.eclipse.org/org/documents/edl-v10.php.
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
