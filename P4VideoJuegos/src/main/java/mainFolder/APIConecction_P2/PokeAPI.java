package mainFolder.APIConecction_P2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class PokeAPI {

    private static final String BASE_URL = "https://pokeapi.co/api/v2/pokemon";//Lista de los pokemons.
    static Scanner entry = new Scanner(System.in);

    public static void main(String[] args) {

        HttpClient client = HttpClient.newHttpClient();//Conexion con HTTP
        Gson json = new GsonBuilder().setPrettyPrinting().create();

        try {
            HttpRequest listRequest = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "?limit=20"))
                    .build();//Ponemos un limite de 20 pokemons.

            HttpResponse<String> listResponse = client.send(listRequest, HttpResponse.BodyHandlers.ofString());//Cogemos los datos de la API
            JsonObject jsonList = JsonParser.parseString(listResponse.body()).getAsJsonObject();//Contruimos un json, para luego filtrar por parametros.

            System.out.println("Pokémons disponibles:");

            jsonList.getAsJsonArray("results").forEach(p -> {
                JsonObject pokeObj = p.getAsJsonObject();
                System.out.print(" - " + pokeObj.get("name").getAsString());
            });//Hacemos un foreach, para recorrer el json y coger el nombre de los pokemons.

            System.out.println("\n");

            System.out.print("Introduce el nombre del Pokémon que quieres buscar: ");
            String pokemonName = entry.nextLine().toLowerCase();//El usuario filtra que pokemon quiere ver.

            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL + "/" + pokemonName))
                        .build();//Contruimos otra conexion con la API pero apartir de nuestro pokemon.

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {//Respuesta 200 es que todo fue bien. Por ende existe el pokemon buscado.
                    Pokemon pokemon = json.fromJson(response.body(), Pokemon.class);

                    System.out.println("\n--- Información del Pokémon ---");
                    System.out.println("Nombre: " + pokemon.name);
                    System.out.println("Altura: " + pokemon.height);
                    System.out.println("Peso: " + pokemon.weight);
                    System.out.println("Tipos:");
                    for (Pokemon.TypeSlot ts : pokemon.types) {
                        System.out.println(" - " + ts.type.name);

                    }

                } else {

                    System.out.println("No se encontró el Pokémon: " + pokemonName);

                }

            } catch (IOException | InterruptedException e) {

                System.out.println("Algo salió mal al buscar el Pokémon.");

            }

        } catch (IOException | InterruptedException e) {

            System.out.println("No se pudo obtener la lista de Pokémon.");
            e.printStackTrace();

        }
    }
}