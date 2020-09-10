import com.google.gson.Gson;

import java.io.IOException;

public class CommentsStream implements Runnable {
    Connection connection;
    Comment[] comments;

    public CommentsStream(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
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
        this.comments = gson.fromJson(streamDataString, Comment[].class);
    }
}
