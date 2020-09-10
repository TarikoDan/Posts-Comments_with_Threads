import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InterruptedException {

//        URL url = new URL("https://jsonplaceholder.typicode.com/posts");
//        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//        int responseCode = urlConnection.getResponseCode();
//        StringBuilder dataString = new StringBuilder();
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//            InputStream inputStream = urlConnection.getInputStream();
//            int i = 0;
//            while ((i=inputStream.read()) != -1) {
//                dataString.append((char) i);
//            }
//        }

        Connection postsConnection = new Connection("https://jsonplaceholder.typicode.com/posts");
        Connection commentsConnection = new Connection("https://jsonplaceholder.typicode.com/comments");

        PostsStream postsStream = new PostsStream(postsConnection);
        CommentsStream commentsStream = new CommentsStream(commentsConnection);
        Thread postsThread = new Thread(postsStream);
        Thread commentsThread = new Thread(commentsStream);
        postsThread.start();
        commentsThread.start();
        postsThread.join();
        commentsThread.join();

        Post[] posts = postsStream.posts;
        Comment[] comments = commentsStream.comments;

        for (Post post : posts) {
            System.out.println("post " + post.id);
            System.out.println("- - - -");
            Arrays.stream(comments)
                    .filter(comment -> comment.postId == post.id)
                    .forEach(comment -> System.out.println("post " + comment.postId + " --> comment " + comment.id));
            System.out.println("----------------------");
        }

//        for (Object o : postsInJSONArray) {
//            JSONObject jsonObject = new JSONObject(o.toString());
//            int id = jsonObject.getInt("id");
//            int userId = jsonObject.getInt("userId");
//            String title = jsonObject.getString("title");
//            String body = jsonObject.getString("body");
//            Post post = new Post(id, userId, title, body);
//            System.out.println(post.userId);
//        }

//        Gson gson = new Gson();
//        for (Object o : postsInJSONArray) {
//            Post post = gson.fromJson(o.toString(), Post.class);
//            System.out.println(post);
//            System.out.println(post.id);
//        }
//        System.out.println(Arrays.stream(posts).count());
    }
}
