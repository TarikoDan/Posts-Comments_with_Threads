import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

public class Connection {
    private final String url;

    public Connection(String url) {
        this.url = url;
    }

    String getStreamDataString () throws IOException {
        URL url = new URL(this.url);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        StringBuilder data = new StringBuilder();
        try {
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = urlConnection.getInputStream();
                int i = 0;
                while ((i = inputStream.read()) != -1) {
                    data.append((char) i);
                }
            }
            else{
                    System.out.println("bad Connection");
                }
        }
        catch (UnknownHostException e) {
            System.out.println(e);
        }
        return data.toString();
    }
}
