package mainFolder.VideoGamesShop_P1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class VideoGamesShop {

    static final String FILE_NAME = "tienda_videojuegos.json";
    static Scanner entry = new Scanner(System.in);
    static List<VideoGame> videoGamesList = new ArrayList<>();

    public static void main(String[] args) {

        Gson json = new GsonBuilder().setPrettyPrinting().create();

        //A
        for (int i = 0; i < 3; i++) {//Creamos 3 videojuegos

            System.out.println("Introduce los datos del videojuego " + (i + 1) + ":");
            videoGamesList.add(createGame());

        }

        //B
        Serialize(videoGamesList,json);

        //C
        List<VideoGame> videoGamesToRead = DesSerialize(json);//Desserializamos para poder ver el contenido del json.
        System.out.println("\nVideojuegos leídos desde JSON:");
        System.out.println(json.toJson(videoGamesToRead));

        //E
        System.out.println("\nIntroduce un nuevo videojuego:");
        videoGamesToRead.add(createGame());//Creamos un nuevo juego

        //F
        System.out.println("\nVideojuegos con precio menor a 30€:");
        videoGamesToRead.stream()
                .filter(v -> v.getPrice() < 30.00)
                .forEach(System.out::println);//Filtramos con stream, para recorrer el json filtrando por los objetos que tengan precio inferior a 30.

        Serialize(videoGamesToRead,json);//Serializamos de nuevo.

    }

    public static VideoGame createGame(){//Pedimos todos los atributos del objeto VideoGame para crearlo y agregarlo al Json.

        System.out.print("Nombre del juego: ");
        String name = entry.nextLine();
        System.out.print("Plataforma: ");
        String platform = entry.nextLine();
        System.out.print("Precio: ");
        double price = Double.parseDouble(entry.nextLine());
        System.out.print("Disponible? (true/false): ");
        boolean available = Boolean.parseBoolean(entry.nextLine());
        System.out.print("Géneros (separados por coma): ");
        List<String> genders = Arrays.asList(entry.nextLine().split(",\\s*"));
        return new VideoGame(name, platform, price, available, genders);

    }

    private static void Serialize(List<VideoGame> list, Gson gson) {//Creamos el json (si existe se sobreescribe).

        try (FileWriter writer = new FileWriter(FILE_NAME)) {

            gson.toJson(list, writer);
            System.out.println("\nLista guardada en " + FILE_NAME);

        } catch (IOException e) {

            System.out.println("Error al guardar JSON.");
            e.printStackTrace();

        }
    }

    private static List<VideoGame> DesSerialize(Gson gson) {//Leemos el json , teniendo en cuenta que puede tener varios objetos,
        // lo iba a hacer como lo hicimos en clase pero me saltaba todo el rato que habia otra forma mas optima que es esta y me da toc, pero entiendo
        //  que lo que se hace es indicarle la estructura que tiene la lista, despues creamos una instancia anonima para poder conservar el tipo List<VideoGames>
        //  y por ultimo lo leemos con fromJson.

        try (FileReader reader = new FileReader(FILE_NAME)) {

            Type listType = new TypeToken <List <VideoGame> >() {}.getType();
            return gson.fromJson(reader, listType);

        } catch (IOException e) {

            System.out.println("Error al leer JSON. Devolviendo lista vacía.");
            return new ArrayList<>();

        }
    }
}
