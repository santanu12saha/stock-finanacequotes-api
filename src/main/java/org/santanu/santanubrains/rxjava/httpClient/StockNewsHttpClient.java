package org.santanu.santanubrains.rxjava.httpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.json.XML;
import org.santanu.santanubrains.rxjava.log4j.Log4jUtil;

public class StockNewsHttpClient {

	private static final Logger logger = Log4jUtil.getLogger(StockNewsHttpClient.class);
	private static StringBuffer result = null;

	public static String getRemoteNews(String ticker) throws Exception {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		String URL = "https://feeds.finance.yahoo.com/rss/2.0/headline?s=" + ticker + "&region=US&lang=en-US";
		try {
			
			HttpGet get = new HttpGet(URL);
			CloseableHttpResponse response = httpclient.execute(get);
			try {

				logger.info("Response Code : " + response.getStatusLine().getStatusCode());
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					BufferedReader rd = new BufferedReader(new InputStreamReader(entity.getContent()));

					try {
						result = new StringBuffer();
						String line = "";
						try {
							while ((line = rd.readLine()) != null) {
								result.append(line);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					} finally {
						rd.close();
					}
				}
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
		return XML.toJSONObject(result.toString()).toString();
	}
}
