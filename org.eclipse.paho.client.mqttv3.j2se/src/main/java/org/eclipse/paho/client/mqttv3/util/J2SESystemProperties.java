/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.eclipse.paho.client.mqttv3.util;

import java.util.Properties;

public class J2SESystemProperties implements SystemProperties {

	public Properties get() {
		return System.getProperties();
	}

}
