/*******************************************************************************
 * Copyright (c) 2014 IBM Corp.
 * Copyright (c) 2016 IS2T.
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

package org.eclipse.paho.client.mqttv3;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.paho.client.mqttv3.internal.ClientComms;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/**
 * Default ping sender implementation
 *
 * <p>
 * This class implements the {@link MqttPingSender} pinger interface allowing applications to send ping packet to server
 * every keep alive interval.
 * </p>
 *
 * @see MqttPingSender
 */
public class TimerPingSender implements MqttPingSender {
	private static final String CLASS_NAME = TimerPingSender.class.getName();
	private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CLASS_NAME);

	private ClientComms comms;
	private Timer timer;

	public void init(ClientComms comms) {
		if (comms == null) {
			throw new IllegalArgumentException("ClientComms cannot be null.");
		}
		this.comms = comms;
	}

	public void start() {
		final String methodName = "start";
		String clientid = this.comms.getClient().getClientId();

		// @Trace 659=start timer for client:{0}
		log.fine(CLASS_NAME, methodName, "659", new Object[] { clientid });

		this.timer = new Timer("MQTT Ping: " + clientid);
		// Check ping after first keep alive interval.
		this.timer.schedule(new PingTask(), this.comms.getKeepAlive());
	}

	public void stop() {
		final String methodName = "stop";
		// @Trace 661=stop
		log.fine(CLASS_NAME, methodName, "661", null);
		if (this.timer != null) {
			this.timer.cancel();
		}
	}

	public void schedule(long delayInMilliseconds) {
		this.timer.schedule(new PingTask(), delayInMilliseconds);
	}

	private class PingTask extends TimerTask {
		private static final String methodName = "PingTask.run";

		public void run() {
			// @Trace 660=Check schedule at {0}
			log.fine(CLASS_NAME, methodName, "660", new Object[] { new Long(System.currentTimeMillis()) });
			TimerPingSender.this.comms.checkForActivity();
		}
	}
}
