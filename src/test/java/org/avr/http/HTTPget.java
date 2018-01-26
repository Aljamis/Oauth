package org.avr.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Just a simple class to call a URL
 * @author axviareque
 *
 */
public class HTTPget {

	public static void main(String[] args) {
		CloseableHttpClient httpClient = HttpClients.custom()
				.setDefaultCredentialsProvider( createProxyCredentials() ).build();
		
		try {
			HttpHost proxy = new HttpHost("proxy.aetna.com", 9119);
			RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
			
			HttpGet get = new HttpGet("/zen");
			get.setConfig(config);
			
			HttpHost target = new HttpHost("api.github.com", -1, "https");
			
			CloseableHttpResponse resp = httpClient.execute(target , get );
			System.out.println( resp.getStatusLine() );
			
			BufferedReader reader = new BufferedReader( new InputStreamReader( resp.getEntity().getContent()));
			String line="";
			while((line=reader.readLine()) != null ) {
				System.out.println( line );
			}
			
		} catch ( IOException cpEx) {
			cpEx.printStackTrace();
		}
	}
	
	
	
	private static CredentialsProvider createProxyCredentials() {
		CredentialsProvider creds = new BasicCredentialsProvider();
		creds.setCredentials(
				new AuthScope("proxy.aetna.com" , 9119)
				, new UsernamePasswordCredentials("aeth\\", ""));
		return creds;
	}

}
