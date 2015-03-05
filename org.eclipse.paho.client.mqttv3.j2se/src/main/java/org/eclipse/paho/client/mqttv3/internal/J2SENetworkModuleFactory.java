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
 *    This file has been modified by IS2T.
 */

package org.eclipse.paho.client.mqttv3.internal;

import java.util.Properties;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory;

public class J2SENetworkModuleFactory extends AbstractNetworkModuleFactory
		implements NetworkModuleFactory {

	public NetworkModule createNetworkModule(String address,
			MqttConnectOptions options, String clientId) throws MqttException {
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
			} else if (factory instanceof SSLSocketFactory) {
				throw ExceptionHelper
						.createMqttException(MqttException.REASON_CODE_SOCKET_FACTORY_MISMATCH);
			}
			netModule = new TCPNetworkModule(factory, host, port, clientId);
			((TCPNetworkModule) netModule).setConnectTimeout(options
					.getConnectionTimeout());
			break;
		case MqttConnectOptions.URI_TYPE_SSL:
			shortAddress = address.substring(6);
			host = getHostName(shortAddress);
			port = getPort(shortAddress, 8883);
			SSLSocketFactoryFactory factoryFactory = null;
			if (factory == null) {
				// try {
				factoryFactory = new SSLSocketFactoryFactory();
				Properties sslClientProps = options.getSSLProperties();
				if (null != sslClientProps)
					factoryFactory.initialize(sslClientProps, null);
				factory = factoryFactory.createSocketFactory(null);
				// }
				// catch (MqttDirectException ex) {
				// throw ExceptionHelper.createMqttException(ex.getCause());
				// }
			} else if ((factory instanceof SSLSocketFactory) == false) {
				throw ExceptionHelper
						.createMqttException(MqttException.REASON_CODE_SOCKET_FACTORY_MISMATCH);
			}

			// Create the network module...
			netModule = new SSLNetworkModule((SSLSocketFactory) factory, host,
					port, clientId);
			((SSLNetworkModule) netModule).setSSLhandshakeTimeout(options
					.getConnectionTimeout());
			// Ciphers suites need to be set, if they are available
			if (factoryFactory != null) {
				String[] enabledCiphers = factoryFactory
						.getEnabledCipherSuites(null);
				if (enabledCiphers != null) {
					((SSLNetworkModule) netModule)
							.setEnabledCiphers(enabledCiphers);
				}
			}
			break;
		case MqttConnectOptions.URI_TYPE_LOCAL:
			netModule = new LocalNetworkModule(address.substring(8));
			break;
		default:
			// This shouldn't happen, as long as validateURI() has been called.
			netModule = null;
		}
		return netModule;
	}
}
