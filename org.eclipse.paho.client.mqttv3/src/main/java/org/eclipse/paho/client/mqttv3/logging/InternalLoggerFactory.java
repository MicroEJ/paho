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
