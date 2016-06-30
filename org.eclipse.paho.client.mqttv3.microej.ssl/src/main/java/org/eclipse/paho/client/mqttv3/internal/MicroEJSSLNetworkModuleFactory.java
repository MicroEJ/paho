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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.internal.security.MicroEJSSLSocketFactoryFactory;

public class MicroEJSSLNetworkModuleFactory extends AbstractNetworkModuleFactory
implements NetworkModuleFactory {

	public static final String DEFAULTLIST = "/certificates/paho.certificates.list";
	public static final String CERTIFICATESLIST = "org.eclipse.paho.certificates";

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

		case MqttConnectOptions.URI_TYPE_SSL:
			shortAddress = address.substring(6);
			host = getHostName(shortAddress);
			port = getPort(shortAddress, 8883);
			MicroEJSSLSocketFactoryFactory factoryFactory = null;
			if (factory == null) {
				// try {
				factoryFactory = new MicroEJSSLSocketFactoryFactory();
				String certificatesList = System.getProperty(CERTIFICATESLIST, DEFAULTLIST);
				if (certificatesList != null) {
					InputStream listStream = MicroEJSSLNetworkModuleFactory.class.getResourceAsStream(certificatesList);
					BufferedReader reader = new BufferedReader(new InputStreamReader(listStream));
					String certificate;
					try {
						while ((certificate = reader.readLine()) != null) {
							factoryFactory.addCertificate(certificate);
						}
					} catch (IOException e) {
						throw ExceptionHelper.createMqttException(MqttException.REASON_CODE_SSL_CONFIG_ERROR);
					}
				} else {
					throw ExceptionHelper.createMqttException(MqttException.REASON_CODE_SSL_CONFIG_ERROR);
				}
				factory = factoryFactory.createSocketFactory();
			} else if ((factory instanceof SSLSocketFactory) == false) {
				throw ExceptionHelper.createMqttException(MqttException.REASON_CODE_SOCKET_FACTORY_MISMATCH);
			}

			// Create the network module...
			netModule = new MicroEJSSLNetworkModule((SSLSocketFactory) factory, host, port, clientId);
			((MicroEJSSLNetworkModule) netModule).setSSLhandshakeTimeout(options.getConnectionTimeout());
			// // Ciphers suites need to be set, if they are available
			// if (factoryFactory != null) {
			// String[] enabledCiphers =
			// factoryFactory.getEnabledCipherSuites(null);
			// if (enabledCiphers != null) {
			// ((MicroEJSSLNetworkModule)
			// netModule).setEnabledCiphers(enabledCiphers);
			// }
			// }
			break;

		default:
			throw new IllegalArgumentException("Unsupported URI type: "
					+ address);
		}
		return netModule;
	}
}
