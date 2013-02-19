package com.saltship.utils;

import java.util.ArrayList;
import java.util.List;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class HttpLogger extends Logger
{
	private HttpClient httpClient;

	private String applicationName;
	private String endpoint;

	public HttpLogger (String applicationIdentifier, String endpointUrl)
	{
		super();

		httpClient = new DefaultHttpClient();
		endpoint = endpointUrl;
		applicationName = applicationIdentifier;
	}

	public void log (Level level, String message)
	{
		super.log (level, message);

		HttpPost httpPost = new HttpPost (endpoint);
		
		List<NameValuePair> values = new ArrayList<>();
		values.add (new BasicNameValuePair ("application", applicationName));
		values.add (new BasicNameValuePair ("level", level.toString()));
		values.add (new BasicNameValuePair ("message", message));
		values.add (new BasicNameValuePair ("timestamp", Long.toString (System.currentTimeMillis())));
		
		try
		{
			httpPost.setEntity (new UrlEncodedFormEntity (values, HTTP.UTF_8));
		
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
		}
		catch (UnsupportedEncodingException e)
		{
		}
		catch (IOException e)
		{
			System.err.println ( e.getMessage() );
		}
	}

	protected void finalize ()
	{
		httpClient.getConnectionManager().shutdown();
	}
}
