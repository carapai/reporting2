package org.openmrs.module.reporting2.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class HTTP {
	
	private static final Charset CHARSET = StandardCharsets.UTF_8;
	
	public static URI buildURI(String host, String path, List<NameValuePair> nameValuePairs) throws URISyntaxException {
		return new URIBuilder().setScheme("https").setHost(host).setPath(path).setParameters(nameValuePairs).build();
	}
	
	public static URI buildURI(String host, String path) throws URISyntaxException {
		return new URIBuilder().setScheme("https").setHost(host).setPath(path).build();
	}
	
	public static HttpResponse get(URI uri, String username, String password) throws IOException {
		HttpGet request = new HttpGet(uri);
		String auth = username + ":" + password;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
		String authHeader = "Basic " + new String(encodedAuth);
		request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
		
		request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		
		HttpClient client = HttpClientBuilder.create().build();
		return client.execute(request);
		
	}
	
	public static HttpResponse download(URI uri, String username, String password) throws IOException {
		HttpGet request = new HttpGet(uri);
		String auth = username + ":" + password;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
		String authHeader = "Basic " + new String(encodedAuth);
		request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
		
		HttpClient client = HttpClientBuilder.create().build();
		return client.execute(request);
		
	}
	
	public static HttpResponse post(URI uri, String username, String password, String json) throws IOException {
		
		HttpPost request = new HttpPost(uri);
		
		StringEntity entity = new StringEntity(json);
		request.setEntity(entity);
		request.setHeader(HttpHeaders.ACCEPT, "application/json");
		request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		
		String auth = username + ":" + password;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
		String authHeader = "Basic " + new String(encodedAuth);
		request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
		
		HttpClient client = HttpClientBuilder.create().build();
		return client.execute(request);
		
	}
	
	public static void write(String fileName, String data) throws IOException {
		Path path = Paths.get(fileName);
		byte[] strToBytes = data.getBytes();
		Files.write(path, strToBytes);
	}
	
	public static void write(HttpResponse response, File target) throws IOException {
		InputStream source = response.getEntity().getContent();
		FileUtils.copyInputStreamToFile(source, target);
	}
	
	public static String convert(HttpResponse response) throws IOException {
		return IOUtils.toString(response.getEntity().getContent(), CHARSET);
		
	}
	
	public static JSONObject convert(String jsonString) {
		return new JSONObject(jsonString);
	}
}
