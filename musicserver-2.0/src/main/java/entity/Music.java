package entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Music {
    private int id;
    private String title;
    private String singer;
    private String url;
    private int userId;
    private String postTime;

}
