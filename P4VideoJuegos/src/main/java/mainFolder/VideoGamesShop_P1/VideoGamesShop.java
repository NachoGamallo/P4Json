package mainFolder.VideoGamesShop_P1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class VideoGamesShop {

    static final String FILE_NAME = "tienda_videojuegos.json";
    static Scanner entry = new Scanner(System.in);
    static List<VideoGame> videoGamesList = new ArrayList<>();

    public static void main(String[] args) {



    }

    public static VideoGame createGame(){

        System.out.print("Nombre del juego: ");
        String name = entry.nextLine();
        System.out.print("Plataforma: ");
        String platform = entry.nextLine();
        System.out.print("Precio: ");
        double price = entry.nextDouble();
        System.out.print("¿Disponible? (true/false): ");
        boolean available = entry.hasNext();
        System.out.print("Géneros (separados por coma): ");
        List<String> genders = Arrays.asList(entry.nextLine().split(",\\s*"));
        return new VideoGame(name, platform, price, available, genders);

    }
}
