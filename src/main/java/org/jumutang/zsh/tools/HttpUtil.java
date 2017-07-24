package org.jumutang.zsh.tools;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * httpClient请求工具类
 * 
 * @author 鲁雨
 * @since 20161223
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
public class HttpUtil {

	Logger logger = Logger.getLogger(HttpUtil.class);

	public static String sendPost(String url, String encode, String xmlParams) {

		CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();//设置请求和传输超时时�?
		post.setConfig(requestConfig);
		post.setHeader("Content-Type", "text/html;charset=UTF-8");
		try {
			if (xmlParams != null) {
				StringEntity stringEntity = new StringEntity(xmlParams, encode);
				System.out.println("sb=" + xmlParams);
				post.setEntity(stringEntity);
			}
			post.addHeader("Content-Type", "text/xml");
			HttpResponse httpResponse = closeableHttpClient.execute(post);
			HttpEntity responceEntity = httpResponse.getEntity();
			System.out.println("status:" + httpResponse.getStatusLine());
			return EntityUtils.toString(responceEntity, encode);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				closeableHttpClient.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
