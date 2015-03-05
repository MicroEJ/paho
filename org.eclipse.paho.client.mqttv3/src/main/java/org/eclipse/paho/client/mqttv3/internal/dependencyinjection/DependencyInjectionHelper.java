/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.eclipse.paho.client.mqttv3.internal.dependencyinjection;

import java.io.InputStream;
import java.util.Properties;

/**
 * Helper to retrieve instances of implementor of contract using system
 * properties to retrieve the expected implementation to be used.
 */
public class DependencyInjectionHelper {

	private static final String DEPENDENCY_INJECTION_FILE = "dependencyinjection.properties";
	private static Properties contractToImplementation;

	private DependencyInjectionHelper() {
	}

	/**
	 * Returns an object assignable to the given contract. The policy is to
	 * retrieve a system property whose name is the fully qualified name of the
	 * given contract and the value the fully qualified name of the
	 * implementation to be used. If this property is not defined, we try to
	 * retrieve the value from the properties file.
	 * 
	 * @param contract
	 *            the class to search for an implementation.
	 * @return an object assignable to the given contract
	 */
	public static Object getImplementation(Class contract) {
		String contractName = contract.getName();
		String implementationName = System.getProperty(contractName);

		if (implementationName == null) {
			if(contractToImplementation == null){
				loadPropertiesFile();
			}
			
			implementationName = contractToImplementation
					.getProperty(contractName);
		}

		Object implementation;

		try {
			implementation = Class.forName(implementationName).newInstance();
		} catch (Throwable t) {
			// NullPointerException
			// InstantiationException
			// IllegalAccessException
			// ClassNotFoundException

			throw new RuntimeException("Can't find implementation for "
					+ contractName, t);
		}

		if (!contract.isInstance(implementation)) {
			throw new RuntimeException("Can't find implementation for "
					+ contractName, new ClassCastException());
		}

		return implementation;
	}
	
	private static void loadPropertiesFile(){
		InputStream stream = DependencyInjectionHelper.class
				.getResourceAsStream(DEPENDENCY_INJECTION_FILE);
		contractToImplementation = new Properties();
		try {
			contractToImplementation.load(stream);
		} catch (Throwable t) {
			throw new RuntimeException("Error loading "
					+ DEPENDENCY_INJECTION_FILE);
		}
	}
}
