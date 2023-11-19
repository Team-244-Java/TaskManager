package edu.cs244.taskpulse.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class ChatGPTHttpClient {

	private static final String API_URL = "https://api.openai.com/v1/chat/completions";
	private static final String model = "gpt-3.5-turbo";

    
//    public String generateResponse(String prompt) {
//    	 try {
//             HttpRequest request = HttpRequest.newBuilder()
//                     .uri(URI.create(API_URL))
//                     .header("Content-Type", "application/json")
//                     .header("Authorization", "Bearer " + apiKey)
//                     .POST(HttpRequest.BodyPublishers.ofString("{\"prompt\":\"" + prompt + "\"}"))
//                     .build();
//
//             HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//             if (response.statusCode() == 200) {
//                 return response.body();
//             } else {
//                 // Handle non-200 status codes
//                 System.err.println("Error: " + response.statusCode());
//                 return null;
//             }
//         } catch (Exception e) {
//             // Handle exceptions
//             e.printStackTrace();
//             return null;
//         }
//    }
    
    public String chatGPT(String prompt) {
    	Properties properties = new Properties();
		try (InputStream input = getClass().getResourceAsStream("/config.properties")) {
		    properties.load(input);
		} catch (IOException e) {
		    e.printStackTrace(); // Handle the exception appropriately
		}

		// Retrieve the API key from the properties
		String apiKey = properties.getProperty("api.key");
		
        try {
            URL obj = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // The request body
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Response from ChatGPT
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            StringBuffer response = new StringBuffer();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // calls the method to extract the message.
            return extractMessageFromJSONResponse(response.toString());

        } catch (IOException e) {
        	e.printStackTrace();
            return null;
        }
    }
    
    public static String extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content")+ 11;

        int end = response.indexOf("\"", start);

        return response.substring(start, end);

    }
}
