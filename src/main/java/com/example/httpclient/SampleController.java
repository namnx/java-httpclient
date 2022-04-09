package com.example.httpclient;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
public class SampleController {

  @GetMapping("/get1")
  public String get1(@RequestParam(name = "url") String url) throws IOException {
    // Using OkHttp
    OkHttpClient httpClient = new OkHttpClient();
    Request request = new Request.Builder()
            .url(url)
            .build();
    Response response = httpClient.newCall(request).execute();
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
