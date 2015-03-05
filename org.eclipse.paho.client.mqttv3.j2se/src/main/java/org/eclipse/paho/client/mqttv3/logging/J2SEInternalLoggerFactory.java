/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.eclipse.paho.client.mqttv3.logging;

import java.lang.reflect.Method;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class J2SEInternalLoggerFactory implements InternalLoggerFactory {

	/**
	 * Default message catalog.
	 */
	private static final String CLASS_NAME = J2SEInternalLoggerFactory.class.getName();
	
	/**
	 * Default logger that uses java.util.logging. 
	 */
	private static String jsr47LoggerClassName = "org.eclipse.paho.client.mqttv3.logging.JSR47Logger"; 
	
	public Logger getLogger(String messageCatalogName, String loggerID,
			String overrideloggerClassName) {
		String loggerClassName = overrideloggerClassName;
		Logger logger = null;
		
		if (loggerClassName == null) {
			loggerClassName = jsr47LoggerClassName;
		}
//			logger = getJSR47Logger(ResourceBundle.getBundle(messageCatalogName), loggerID, null) ;
		logger = getLogger(loggerClassName, ResourceBundle.getBundle(messageCatalogName), loggerID, null) ;
//		}

		if (null == logger) {
			throw new MissingResourceException("Error locating the logging class", CLASS_NAME, loggerID);
		}

		return logger;
	}
	
	/**
	 * Return an instance of a logger
	 * 
	 * @param the class name of the load to load
	 * @param messageCatalog  the resource bundle containing messages 
	 * @param loggerID  an identifier for the logger 
	 * @param resourceName a name or context to associate with this logger instance.  
	 * @return a ready for use logger
	 */
	private ResourceBundleLogger getLogger(String loggerClassName, ResourceBundle messageCatalog, String loggerID, String resourceName) { //, FFDC ffdc) {
		ResourceBundleLogger logger  = null;
		Class logClass = null;
		
		try {
			logClass = Class.forName(loggerClassName);
		} catch (NoClassDefFoundError ncdfe) {
			return null;
		} catch (ClassNotFoundException cnfe) {
			return null;
		}
		if (null != logClass) {
			// Now instantiate the log
			try {
				logger = (ResourceBundleLogger)logClass.newInstance();
			} catch (IllegalAccessException e) {
				return null;
			} catch (InstantiationException e) {
				return null;
			} catch (ExceptionInInitializerError e) {
				return null;
			} catch (SecurityException e) {
				return null;
			}
			logger.initialise(messageCatalog, loggerID, resourceName);
		}

		return logger;
	}

	public String getLoggingProperty(String name) {
		String result = null;
		try {
			// Hide behind reflection as java.util.logging is guaranteed to be
			// available.
			Class logManagerClass = Class.forName("java.util.logging.LogManager");
			Method m1 = logManagerClass.getMethod("getLogManager", new Class[]{});
			Object logManagerInstance = m1.invoke(null, null);
			Method m2 = logManagerClass.getMethod("getProperty", new Class[]{String.class});
			result = (String)m2.invoke(logManagerInstance,new Object[]{name});
		} catch(Exception e) {
			// Any error, assume JSR47 isn't available and return null
			result = null;
		}
		return result;
	}

}
