package cz.jiripinkas.jsitemapgenerator;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpClientUtil {

    private HttpClientUtil() {

    }

    /**
     * HTTP GET to URL, return status
     *
     * @param url URL
     * @return status code (for example 200)
     * @throws Exception When error
     */
    public static int get(String url) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try(Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new Exception("error sending HTTP GET to this URL: " + url);
            }
            return response.code();
        }
    }
}
