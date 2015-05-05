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

public abstract class AbstractNetworkModuleFactory implements
		NetworkModuleFactory {
	
	protected int getPort(String uri, int defaultPort) {
		int port;
		int portIndex = uri.lastIndexOf(':');
		if (portIndex == -1) {
			port = defaultPort;
		} else {
			port = Integer.parseInt(uri.substring(portIndex + 1));
		}
		return port;
	}

	protected String getHostName(String uri) {
		int schemeIndex = uri.lastIndexOf('/');
		int portIndex = uri.lastIndexOf(':');
		if (portIndex == -1) {
			portIndex = uri.length();
		}
		return uri.substring(schemeIndex + 1, portIndex);
	}
}
