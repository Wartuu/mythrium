package xyz.mythrium.backend.utils;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.mythrium.backend.dto.OpenGraphDTO;

public class OpenGraphUtils {

    private static final Logger logger = LoggerFactory.getLogger(OpenGraphUtils.class);


    public static OpenGraphDTO fetchOpenGraphData(String url) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        String body;

        try {
            Response response = client.newCall(request).execute();

            body = response.body().string();

            System.out.println(body);

        } catch (Exception e) {
            logger.debug("failed to fetch open graph data, error: " + e.getMessage());
        }

        return null; //TODO: parsing


    }
}
