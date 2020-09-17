import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Post post1 = new Post(); /*lombok*/
        Post buildedPost = Post.builder().body("aa").title("ss").build();  /*lombok*/


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

        File postsFile = new File("posts.txt");
        try (FileOutputStream postsFileOutputStream = new FileOutputStream(postsFile)) {
//            postsFileOutputStream.write(Arrays.toString(posts).getBytes());
            for (Post post : posts) {
                postsFileOutputStream.write(("-----post " + post.id + "----- \n").getBytes());
                postsFileOutputStream.write(post.toString().getBytes());
                postsFileOutputStream.write("\n".getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileInputStream postsFileInputStream = new FileInputStream("posts.txt")) {
//            char read = (char) postsFileInputStream.read();
//            System.out.println(read);
            int i;
            while ((i = postsFileInputStream.read()) != -1) {
                System.out.print((char) i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("*****************************");

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("comments.txt")))){
            for (Comment comment : comments) {
                bufferedWriter.write("-----comment" + comment.id + "-----\n");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("comments.txt"))){
            String s;
            while ((s=bufferedReader.readLine()) !=null) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
