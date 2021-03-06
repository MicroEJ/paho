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

public class MicroEJInternalLoggerFactory implements InternalLoggerFactory {

	@Override
	public Logger getLogger(String messageCatalogName, String loggerID,
			String overrideloggerClassName) {
		return new Logger() {
			
			@Override
			public void warning(String sourceClass, String sourceMethod, String msg,
					Object[] inserts, Throwable thrown) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void warning(String sourceClass, String sourceMethod, String msg,
					Object[] inserts) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void warning(String sourceClass, String sourceMethod, String msg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void trace(int level, String sourceClass, String sourceMethod,
					String msg, Object[] inserts, Throwable ex) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void severe(String sourceClass, String sourceMethod, String msg,
					Object[] inserts, Throwable thrown) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void severe(String sourceClass, String sourceMethod, String msg,
					Object[] inserts) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void severe(String sourceClass, String sourceMethod, String msg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setResourceName(String logContext) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void log(int level, String sourceClass, String sourceMethod,
					String msg, Object[] inserts, Throwable thrown) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isLoggable(int level) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void info(String sourceClass, String sourceMethod, String msg,
					Object[] inserts, Throwable thrown) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void info(String sourceClass, String sourceMethod, String msg,
					Object[] inserts) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void info(String sourceClass, String sourceMethod, String msg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public String formatMessage(String msg, Object[] inserts) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void finest(String sourceClass, String sourceMethod, String msg,
					Object[] inserts, Throwable ex) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void finest(String sourceClass, String sourceMethod, String msg,
					Object[] inserts) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void finest(String sourceClass, String sourceMethod, String msg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void finer(String sourceClass, String sourceMethod, String msg,
					Object[] inserts, Throwable ex) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void finer(String sourceClass, String sourceMethod, String msg,
					Object[] inserts) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void finer(String sourceClass, String sourceMethod, String msg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void fine(String sourceClass, String sourceMethod, String msg,
					Object[] inserts, Throwable ex) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void fine(String sourceClass, String sourceMethod, String msg,
					Object[] inserts) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void fine(String sourceClass, String sourceMethod, String msg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void dumpTrace() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void config(String sourceClass, String sourceMethod, String msg,
					Object[] inserts, Throwable thrown) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void config(String sourceClass, String sourceMethod, String msg,
					Object[] inserts) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void config(String sourceClass, String sourceMethod, String msg) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	@Override
	public String getLoggingProperty(String name) {
		return null;
	}

}
