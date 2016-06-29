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
 */
package org.eclipse.paho.client.mqttv3.internal;

import java.io.IOException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/**
 * A network module for connecting over SSL.
 */
public class MicroEJSSLNetworkModule extends TCPNetworkModule {
	private static final String CLASS_NAME = MicroEJSSLNetworkModule.class.getName();
	private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT,CLASS_NAME);

	private int handshakeTimeoutSecs;

	/**
	 * Constructs a new SSLNetworkModule using the specified host and
	 * port.  The supplied SSLSocketFactory is used to supply the network
	 * socket.
	 */
	public MicroEJSSLNetworkModule(SSLSocketFactory factory, String host, int port, String resourceContext) {
		super(factory, host, port, resourceContext);
		log.setResourceName(resourceContext);
	}

	public void setSSLhandshakeTimeout(int timeout) {
		super.setConnectTimeout(timeout);
		this.handshakeTimeoutSecs = timeout;
	}

	@Override
	public void start() throws IOException, MqttException {
		super.start();
		int soTimeout = socket.getSoTimeout();
		if ( soTimeout == 0 ) {
			// RTC 765: Set a timeout to avoid the SSL handshake being blocked indefinitely
			// remove timeout as the time is not set by default.
			socket.setSoTimeout(this.handshakeTimeoutSecs * 1000);
		}
		((SSLSocket)socket).startHandshake();
		// reset timeout to default value
		socket.setSoTimeout(soTimeout);
	}
}

