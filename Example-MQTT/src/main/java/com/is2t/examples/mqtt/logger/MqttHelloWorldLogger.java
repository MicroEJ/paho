/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.is2t.examples.mqtt.logger;

/**
 * Message logger interface. 
 */
public interface MqttHelloWorldLogger {

	/**
	 * Logs an info message.
	 * @param msg info message
	 */
	void logInfo(String msg);
	
	/**
	 * Logs an error message.
	 * @param msg error message
	 */
	void logError(String msg);
	
}
