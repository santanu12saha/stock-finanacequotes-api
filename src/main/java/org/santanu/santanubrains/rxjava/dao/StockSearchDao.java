package org.santanu.santanubrains.rxjava.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.santanu.santanubrains.rxjava.log4j.Log4jUtil;

public class StockSearchDao {

	private static final Logger logger = Log4jUtil.getLogger(StockSearchDao.class);
	private static final String URL = "https://s.yimg.com/aq/autoc";
	private static StringBuffer result = null;

	public static String search(String searchParam) throws IOException {

		CloseableHttpClient httpclient = HttpClients.createDefault();

		try {
			HttpPost post = new HttpPost(URL);
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("query", searchParam));
			urlParameters.add(new BasicNameValuePair("region", "CA"));
			urlParameters.add(new BasicNameValuePair("lang", "en-CA"));

			post.setEntity(new UrlEncodedFormEntity(urlParameters));

			CloseableHttpResponse response = httpclient.execute(post);
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
		return result.toString();

	}
}
