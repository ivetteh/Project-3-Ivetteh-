import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class TriviaQuiz {

    
    private static final String BASE_URL = "https://opentdb.com/api.php";


    public static void main(String[] args) throws Exception {
        // For now these are typed in. Later the user will choose them.
        int numQuestions = 5;
        String difficulty = "easy";

        // Make the link, then ask the API for questions
        String url = buildUrl(numQuestions, difficulty);
        System.out.println("Requesting: " + url);

        String responseText = fetchQuestions(url);

        // Print the response if we got one
        if (responseText != null) {
            System.out.println(responseText);
        } else {
            System.out.println("Could not retrieve trivia questions.");
        }
    }

    // Puts the number of questions and difficulty into the link
    public static String buildUrl(int amount, String difficulty) {
        String url = BASE_URL;
        url = url + "?amount=" + amount;
        url = url + "&difficulty=" + difficulty;
        url = url + "&type=multiple";
        return url;
    }

    // Sends the request and returns the response text
    // Returns null if the status code is not 200
    public static String fetchQuestions(String url) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        // 200 means the request worked
        if (response.statusCode() == 200) {
            return response.body();
        } else {
            System.out.println("Request failed with status code: "
                    + response.statusCode());
            return null;
        }
    }
}
