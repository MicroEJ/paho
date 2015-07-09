/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.is2t.examples.mqtt.util;

import java.net.NetworkInterface;
import java.net.SocketException;

/**
 * Network utilities.
 */
public final class NetworkChecker {

	private static final int POLLING_PERIOD = 100;
	private static final int TIMEOUT = 20_000;
	private static final int MAX_TRY_COUNT = TIMEOUT / POLLING_PERIOD;

	// Prevents initialization.
	private NetworkChecker() {
	}

	/**
	 * Blocks until network is up or when there is timeout.
	 * 
	 * @return true if network is up otherwise false.
	 */
	public static boolean waitForUp() {
		int tryCount = 0;

		while (!isUp()) {
			tryCount++;

			if (tryCount >= MAX_TRY_COUNT) {
				return false;
			}

			try {
				Thread.sleep(POLLING_PERIOD);
			} catch (InterruptedException e) {
				return false;
			}
		}

		return true;
	}

	private static boolean isUp() {
		try {
			return NetworkInterface.getNetworkInterfaces() != null;
		} catch (SocketException e) {
			return false;
		}
	}
}
