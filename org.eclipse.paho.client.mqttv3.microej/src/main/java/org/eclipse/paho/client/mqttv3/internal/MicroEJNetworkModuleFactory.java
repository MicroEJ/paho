/*******************************************************************************
 * Copyright (c) 2009, 2014 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution. 
 *
 * The Eclipse Public License is available at 
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 *   http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *    Dave Locke - initial API and implementation and/or initial documentation
 *    Ian Craggs - MQTT 3.1.1 support
 *    
 *    
 * This file has been modified by IS2T.
 */

package org.eclipse.paho.client.mqttv3.internal;

import javax.net.SocketFactory;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

public class MicroEJNetworkModuleFactory extends AbstractNetworkModuleFactory
		implements NetworkModuleFactory {

	@Override
	public NetworkModule createNetworkModule(String address,
			MqttConnectOptions options, String clientId) throws MqttException,
			MqttSecurityException {
		NetworkModule netModule;
		String shortAddress;
		String host;
		int port;
		SocketFactory factory = options.getSocketFactory();

		int serverURIType = MqttConnectOptions.validateURI(address);

		switch (serverURIType) {
		case MqttConnectOptions.URI_TYPE_TCP:
			shortAddress = address.substring(6);
			host = getHostName(shortAddress);
			port = getPort(shortAddress, 1883);
			if (factory == null) {
				factory = SocketFactory.getDefault();
			}

			netModule = new TCPNetworkModule(factory, host, port, clientId);
			((TCPNetworkModule) netModule).setConnectTimeout(options
					.getConnectionTimeout());
			break;

		default:
			throw new IllegalArgumentException("Unsupported URI type: "
					+ address);
		}
		return netModule;
	}
}
