/*******************************************************************************
 * Copyright (c) 2009, 2014 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution. 
 *
 * The Eclipse Public License is available at 
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 *   http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *    Dave Locke - initial API and implementation and/or initial documentation
 *    
 *    
 * This file has been modified by IS2T.
 */
package org.eclipse.paho.client.mqttv3.internal;

import org.eclipse.paho.client.mqttv3.internal.dependencyinjection.DependencyInjectionHelper;

/**
 * Catalog of human readable error messages.
 */
public abstract class MessageCatalog {
	private static MessageCatalog INSTANCE = null;

	public static final String getMessage(int id) {
		if (INSTANCE == null) {
			INSTANCE = (MessageCatalog) DependencyInjectionHelper.getImplementation(MessageCatalog.class);
		}
		return INSTANCE.getLocalizedMessage(id);
	}
	
	protected abstract String getLocalizedMessage(int id);
}
