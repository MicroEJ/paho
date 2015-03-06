/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.eclipse.paho.client.mqttv3.logging;


public interface InternalLoggerFactory {

	/**
	 * Find or create a logger for a named package/class. 
	 * If a logger has already been created with the given name 
	 * it is returned. Otherwise a new logger is created.
	 * 
	 * @param messageCatalogName the resource bundle containing the logging messages.
	 * @param loggerID  unique name to identify this logger.
	 * @param overrideloggerClassName the class name of the logger to use.
	 * @return a suitable Logger.
	 * @throws Exception
	 */
	Logger getLogger(String messageCatalogName, String loggerID, String overrideloggerClassName);
	
	/**
	 * When run in JSR47, this allows access to the properties in the logging.properties
	 * file.
	 * If not run in JSR47, or the property isn't set, returns null.
	 * @param name the property to return
	 * @return the property value, or null if it isn't set or JSR47 isn't being used
	 */
	String getLoggingProperty(String name);
}
