package mainFolder.VideoGamesShop_P1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class VideoGame {

    private String name;
    private String platform;
    private double price;
    private boolean available;
    private List<String> genders;

}
