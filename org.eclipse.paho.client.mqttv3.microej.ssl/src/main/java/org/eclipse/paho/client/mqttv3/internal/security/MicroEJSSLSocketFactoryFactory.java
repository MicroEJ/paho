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
 */
package org.eclipse.paho.client.mqttv3.internal.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.eclipse.paho.client.mqttv3.MqttSecurityException;

/**
 * An SSLSocketFactoryFactory provides a socket factory and a server socket
 * factory that then can be used to create SSL client sockets or SSL server
 * sockets.
 * <p>
 * The SSLSocketFactoryFactory is configured using IBM SSL properties, i.e.
 * properties of the format "com.ibm.ssl.propertyName", e.g.
 * "com.ibm.ssl.keyStore". The class supports multiple configurations, each
 * configuration is identified using a name or configuration ID. The
 * configuration ID with "null" is used as a default configuration. When a
 * socket factory is being created for a given configuration, properties of that
 * configuration are first picked. If a property is not defined there, then that
 * property is looked up in the default configuration. Finally, if a property
 * element is still not found, then the corresponding system property is
 * inspected, i.e. javax.net.ssl.keyStore. If the system property is not set
 * either, then the system's default value is used (if available) or an
 * exception is thrown.
 * <p>
 * The SSLSocketFacotryFactory can be reconfigured at any time. A
 * reconfiguration does not affect existing socket factories.
 * <p>
 * All properties share the same key space; i.e. the configuration ID is not
 * part of the property keys.
 * <p>
 * The methods should be called in the following order:
 * <ol>
 * <li><b>isSupportedOnJVM()</b>: to check whether this class is supported on
 * the runtime platform. Not all runtimes support SSL/TLS.</li>
 * <li><b>SSLSocketFactoryFactory()</b>: the constructor. Clients (in the same
 * JVM) may share an SSLSocketFactoryFactory, or have one each.</li>
 * <li><b>initialize(properties, configID)</b>: to initialize this object with
 * the required SSL properties for a configuration. This may be called multiple
 * times, once for each required configuration.It may be called again to change
 * the required SSL properties for a particular configuration</li>
 * <li><b>getEnabledCipherSuites(configID)</b>: to later set the enabled cipher
 * suites on the socket [see below].</li>
 * </ol>
 * <ul>
 * <li><i>For an MQTT server:</i></li>
 * <ol>
 * <li><b>getKeyStore(configID)</b>: Optionally, to check that if there is no
 * keystore, then that all the enabled cipher suits are anonymous.</li>
 * <li><b>createServerSocketFactory(configID)</b>: to create an
 * SSLServerSocketFactory.</li>
 * <li><b>getClientAuthentication(configID)</b>: to later set on the
 * SSLServerSocket (itself created from the SSLServerSocketFactory) whether
 * client authentication is needed.</li>
 * </ol>
 * <li><i>For an MQTT client:</i></li>
 * <ol>
 * <li><b>createSocketFactory(configID)</b>: to create an SSLSocketFactory.</li>
 * </ol>
 * </ul>
 */
public class MicroEJSSLSocketFactoryFactory {

	// X509 certificate type name
	public static String CERT_TYPE = "X509";

	// TLS algorithm version 1.2
	public static String TLS_VERSION_1_2 = "TLSv1.2";

	private KeyStore store = null;

	/**
	 *
	 */
	public MicroEJSSLSocketFactoryFactory() {
		super();
		try {
			store = KeyStore.getInstance(KeyStore.getDefaultType());
			// our default KeyStore can not be loaded from an InputStream; so
			// just load as empty KeyStore with null parameters
			store.load(null, null);
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Returns an SSL socket factory for the given configuration. If no
	 * SSLProtocol is already set, uses DEFAULT_PROTOCOL. Throws
	 * IllegalArgumentException if the socket factory could not be created due
	 * to underlying configuration problems.
	 *
	 * @see org.eclipse.paho.client.mqttv3.internal.security.MicroEJSSLSocketFactoryFactory#DEFAULT_PROTOCOL
	 * @param configID
	 *            The configuration identifier for selecting a configuration.
	 * @return An SSLSocketFactory
	 * @throws MqttDirectException
	 */
	public SSLSocketFactory createSocketFactory() throws MqttSecurityException {
		final String METHOD_NAME = "createSocketFactory";
		SSLContext ctx = getSSLContext(TLS_VERSION_1_2);

		return ctx.getSocketFactory();
	}

	/**
	 * @param configID
	 * @return
	 */
	private SSLContext getSSLContext(String configID) {

		TrustManagerFactory trustManagerFactory;
		try {
			trustManagerFactory = TrustManagerFactory.getInstance(CERT_TYPE);

			trustManagerFactory.init(store);
			TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

			SSLContext sslContext = SSLContext.getInstance(TLS_VERSION_1_2);
			sslContext.init(null, trustManagers, null);
			return sslContext;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void addCertificate(String alias, InputStream certificateStream) {
		CertificateFactory certificateFactory;
		try {
			certificateFactory = CertificateFactory.getInstance(CERT_TYPE);
			Certificate myServerCert = certificateFactory.generateCertificate(certificateStream);
			store.setCertificateEntry(alias, myServerCert);
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addCertificate(String alias, String pathToCertificate) {
		InputStream certificateStream = MicroEJSSLSocketFactoryFactory.class.getResourceAsStream(pathToCertificate);
		addCertificate(alias, certificateStream);
	}

	public void addCertificate(String pathToCertificate) {
		addCertificate(pathToCertificate, pathToCertificate);
	}

}
