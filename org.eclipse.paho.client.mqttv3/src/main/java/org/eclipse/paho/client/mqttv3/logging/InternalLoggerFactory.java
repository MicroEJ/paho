/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.eclipse.paho.client.mqttv3.logging;


public interface InternalLoggerFactory {

	Logger getLogger(String messageCatalogName, String loggerID, String overrideloggerClassName);
	
	String getLoggingProperty(String name);
}
