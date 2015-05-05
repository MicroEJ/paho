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


package org.eclipse.paho.client.mqttv3.util;

import java.util.Properties;

public interface SystemProperties {

	/**
	 * Gets the system properties.
	 * 
	 * @return the system properties.
	 */
	Properties get();
}
