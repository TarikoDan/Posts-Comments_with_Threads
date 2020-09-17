import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    @NonNull
    int id;
    int userId;
    String title;
    String body;

}
