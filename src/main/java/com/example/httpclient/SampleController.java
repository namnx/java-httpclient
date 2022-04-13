package com.example.httpclient;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.TlsVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@RestController
public class SampleController {

  @GetMapping("/get1")
  public String get1(
          @RequestParam(name = "tls", required = false) String tls,
          @RequestParam(name = "url") String url
  ) throws IOException {
    // Using OkHttp
    OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
    if (tls != null && !tls.trim().isEmpty()) {
      TlsVersion tlsVersion = TlsVersion.forJavaName(tls.trim());
      ConnectionSpec connectionSpec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
              .tlsVersions(tlsVersion)
              .build();
      httpClientBuilder.connectionSpecs(List.of(connectionSpec));
    }

    Request request = new Request.Builder()
            .url(url)
            .build();
    Response response = httpClientBuilder.build().newCall(request).execute();
    return response.body().string() + "\n";
  }

  @GetMapping("/get2")
  public String get2(@RequestParam(name = "url") String url) throws IOException, InterruptedException {
    // Using Java11 HttpClient
    HttpClient httpClient = HttpClient.newBuilder().build();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();
    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    return response.body();
  }
}
