import com.google.gson.Gson;

import java.io.IOException;

public class PostsStream implements Runnable{
    private Connection connection;
    Post[] posts;

    public PostsStream() {
    }

    public PostsStream(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        String streamDataString = null;
        try {
            streamDataString = this.connection.getStreamDataString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        this.posts = gson.fromJson(streamDataString, Post[].class);
    }
}
