/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.eclipse.paho.client.mqttv3.logging;

import java.util.ResourceBundle;

public interface ResourceBundleLogger extends Logger {

	public void initialise(ResourceBundle messageCatalog, String loggerID, String resourceName);
}
