package com.bigbig;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.bigbig.util.StringSignaturHelper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpMethod;

import com.microsoft.azure.storage.StorageCredentialsAccountAndKey;
import com.microsoft.azure.storage.core.StorageCredentialsHelper;

public class StorageConnect {

	public static void main(String[] args) throws InvalidKeyException, IOException {
		String date = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'"));
		System.out.println(date);
		String file = "podn000122256";
		StringSignaturHelper stringSignatureHelper = new StringSignaturHelper();
		stringSignatureHelper.setHttpMethod(HttpMethod.GET.toString());
		stringSignatureHelper.addCannonicalHeader("x-ms-date", date);
		stringSignatureHelper.addCannonicalHeader("x-ms-version", "2015-07-08");
		stringSignatureHelper.addCannoicalResource("/stgcluboperationsreceivi/podatadownload/" + file);
		// stringSignatureHelper.addCannoicalResource("/sandboxclubopssa/sampleshare\ncomp:metadata\nresType:directory");

		String endPoint = "https://npclubopsapim.azure-api.net/poFileDownload/v1";
		StorageCredentialsAccountAndKey storageCredentialsAccountAndKey = new StorageCredentialsAccountAndKey("stgcluboperationsreceivi", "OykhHqQ38rXsB7KtwPCMoCcOpc7uQ0ypBSOul7nDdKKWt3UrnxvPUVYOj3dCw2EDtp6KbrMa/Ai2cx7Wv9a+vQ==");
		String sharedKey = StorageCredentialsHelper.computeHmac256(storageCredentialsAccountAndKey, stringSignatureHelper.buildStringToSign());
		System.out.println(sharedKey);
		Map<String, String> headers = createHeader(sharedKey, date);
		HttpGet getRequest = new HttpGet(endPoint + "/" + file);
//         HttpGet getRequest = new HttpGet(endPoint +
//         "?restype=directory&comp=list");
		addHeaders(getRequest, headers);
		HttpResponse response = null;
		HttpClient client = HttpClientBuilder.create().build();
		response = client.execute(getRequest);
		System.out.println(response.getStatusLine());
		System.out.println(response.getEntity().getContent());
		System.out.println("finished ");

	}

	public static Map<String, String> createHeader(String key, String date) {
		Map<String, String> httpHeaders = new HashMap<String, String>();

		httpHeaders.put("sa-name", "stgcluboperationsrece");
		httpHeaders.put("file-server", "podatadownload");
		httpHeaders.put("Ocp-Apim-Subscription-Key", "8d29b49364d8469f9f6077a0fbfb943d");
		httpHeaders.put("sa-sharedkeyauthsig", key);
		httpHeaders.put("x-ms-date", date);
		httpHeaders.put("x-ms-version", "2015-07-08");

		return httpHeaders;

	}

	private static void addHeaders(HttpRequestBase object, Map<String, String> httpHeaders) {
		for (String key : httpHeaders.keySet()) {
			object.addHeader(key, httpHeaders.get(key));
		}
	}

}
