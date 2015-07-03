/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.is2t.examples.mqtt.logger;

/**
 * Class that logs messages on {@link System#out}.
 */
public class MqttHelloWorldSysoutLogger implements
		MqttHelloWorldLoggerConstants, MqttHelloWorldLogger {

	@Override
	public void logInfo(String msg) {
		System.out.println(INFO_PROMPT + " " + msg);
	}

	@Override
	public void logError(String msg) {
		System.out.println(ERROR_PROMPT + " " + msg);
	}

}
