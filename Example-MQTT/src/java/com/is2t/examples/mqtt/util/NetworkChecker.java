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
 * 
 */
public class NetworkChecker {

	private static final int POLLING_PERIOD = 100; // in ms
	private static final int MAX_TRY_COUNT = 20_000 / POLLING_PERIOD;

	// Prevents initialization.
	private NetworkChecker() {
	}

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
