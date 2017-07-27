package com.gepardec.hogarama.pi4j.mqtt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

/**
 * see https://gist.github.com/Montecito/a60c8193f43b0b8c85f8096cea8fe3f3
 *
 * Stolen from mkapferer
 */
public class MqttClientFactory {
	private MqttClient client;
	private MqttConnectOptions connOpt;

	private static final String OPENSHIFT_HOST = "broker-amq-mqtt-ssl-57-hogarama.cloud.itandtel.at";
	private static final String BROKER_URL = "tcp://" + OPENSHIFT_HOST + ":1883";
	private static final String BROKER_URL_SSL = "ssl://" + OPENSHIFT_HOST + ":443";
	private static final String CLIENT_TRUSTSTORE_PATH = "META-INF/client.ts";
	private static final String CLIENT_TRUSTSTORE_PASSWD = "L(o?cqGPtJ}7YiHu";
	private static final String AMQ_USERNAME = "mq_habarama";
	private static final String AMQ_PASSWORD_MD5 = "mq_habarama_pass";

	private static final Boolean sslEnabled = true;
	private static Logger logger = LogManager.getLogger(MqttClientFactory.class);

	private MqttClient getClientSSLwithSNI(String clientID) throws MqttException, NoSuchAlgorithmException, KeyManagementException{

		SSLContext sc = SSLContext.getInstance("SSL");
		
		KeyStore trustStore;
		TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		try {
			trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			InputStream schemaIS = this.getClass().getClassLoader().getResourceAsStream(CLIENT_TRUSTSTORE_PATH);
			trustStore.load(schemaIS, CLIENT_TRUSTSTORE_PASSWD.toCharArray());

			trustManagerFactory.init(trustStore);
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
		
		sc.init(null, trustManagerFactory.getTrustManagers(), new java.security.SecureRandom());

		URL url = null;
		try {
			url = new URL("https://"+OPENSHIFT_HOST);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
		
		List<SNIServerName> sniServerNames = new ArrayList<SNIServerName>(1);
		sniServerNames.add(new SNIHostName(url.getHost()));
		
		SSLParameters sslParameters = new SSLParameters();
		sslParameters.setServerNames(sniServerNames);
		
		SSLSocketFactory wrappedSSLSocketFactory = new SSLSocketFactoryWrapper(sc.getSocketFactory(), sslParameters);
		
		connOpt.setSocketFactory(wrappedSSLSocketFactory);
		
		return new MqttClient(BROKER_URL_SSL, clientID);
	}

	public MqttClient getClient(MqttCallback callback) {
		String clientID = MqttClient.generateClientId();
		connOpt = new MqttConnectOptions();

		connOpt.setKeepAliveInterval(30);
		connOpt.setUserName(AMQ_USERNAME);
		connOpt.setPassword(AMQ_PASSWORD_MD5.toCharArray());

		// Connect to Broker
		try {
			if(sslEnabled){
				client = getClientSSLwithSNI(clientID);
			} else {
				client = new MqttClient(BROKER_URL, clientID);
			}
			client.setCallback(callback);
			client.connect(connOpt);
		} catch (MqttException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
		
		if(sslEnabled){
			logger.info("Connected to " + BROKER_URL_SSL);
		} else {
			logger.info("Connected to " + BROKER_URL);
		}
		

		return client;
	}

}
