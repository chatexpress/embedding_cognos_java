package com.example.springtest.flows;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.stereotype.Component;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import java.util.Map;
import java.util.HashMap;
import org.json.JSONObject; // Importación para JSONObject
import javax.ws.rs.core.Cookie; // Importación para Cookie
import javax.ws.rs.core.NewCookie; // Importación para NewCookie
import java.net.URI;
import java.util.List; // Importación para List
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.client.Invocation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Component
@Path("/helloworld")
public class HelloWebRestService {

    private static final String USER_COGNOS = "lpatino";
    private static final String PASSWORD_COGNOS = "lpatino1234";

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String helloMessage() {
    //public ResponseEntity<String> helloMessage() {
        // Crear un cliente Jersey
        Client client = ClientBuilder.newClient();

        // PASO 1 :: GENERAR REQUEST PARA OBTENER SESSION KEY
        String apiUrl = "https://688e-155-190-18-42.ngrok-free.app/api/v1/session";

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        String jsonBody = "{"
            + "\"parameters\": ["
                + "{\"name\": \"CAMNamespace\", \"value\": \"MHINT\"},"
                + "{\"name\": \"CAMUsername\", \"value\": \"" + USER_COGNOS + "\"},"
                + "{\"name\": \"CAMPassword\", \"value\": \"" + PASSWORD_COGNOS + "\"}"
            + "]"
        + "}";

        try {
            // Realizar la petición PUT
            Response response = client.target(apiUrl)
                    .request(MediaType.APPLICATION_JSON)
                    .put(Entity.json(jsonBody));

            // Verificar la respuesta
            if (response.getStatus() == 201) {
                String responseBodySessioKey = response.readEntity(String.class);
                
                // Analizar el JSON de la respuesta
                JSONObject jsonResponse = new JSONObject(responseBodySessioKey);
                // Obtener el valor de la clave "session_key"
                String sessionKey = jsonResponse.getString("session_key");
                System.out.println("Valor de session_key: " + sessionKey);
                                

                // PASO 2 :: GENERAR REQUEST PARA RECUPERAR XSRF-TOKEN
                String apiUrlReport = "https://688e-155-190-18-42.ngrok-free.app/bi/v1/disp/rds/reportData/report/iFB439BF7B062490B8763D905E839A2CB?fmt=HTML";
                String authorizationHeader = "IBM-BA-Authorization";

                // Construir la solicitud GET
                Invocation.Builder builder = client.target(apiUrlReport)
                        .request(MediaType.APPLICATION_JSON)
                        .header(authorizationHeader, sessionKey);

                // Realizar la solicitud GET
                Response csrfTokenResponse = builder.get();

                // Verificar si la respuesta tiene cookies
                Map<String, NewCookie> responseCookies = csrfTokenResponse.getCookies();
                
                // Buscar la cookie específica "XSRF-TOKEN" y guardar su valor en una variable
                String xsrfToken = null;
                if (!responseCookies.isEmpty()) {
                    for (Map.Entry<String, NewCookie> entry : responseCookies.entrySet()) {
                        String cookieName = entry.getKey();
                        if (cookieName.equals("XSRF-TOKEN")) {
                            xsrfToken = entry.getValue().getValue();
                            break; // Salir del bucle una vez que se encuentre la cookie
                        }
                    }
                }
                System.out.println("Valor de la cookie XSRF-TOKEN: " + xsrfToken);


                // PASO 3 :: CONSTRUIR COOKIES PARA EL REPORTE
                // Crear un mapa de cookies vacío
                Map<String, String> cookies = new HashMap<>();

                // Obtener las cookies de la respuesta
                Map<String, NewCookie> cookiesMap = response.getCookies();

                // Agregar las cookies al mapa de cookies
                cookies.put("cam_passport", cookiesMap.get("cam_passport").getValue());
                cookies.put("up", cookiesMap.get("up").getValue());
                cookies.put("usersessionid", cookiesMap.get("usersessionid").getValue());
                cookies.put("CRN", cookiesMap.get("CRN").getValue());
                cookies.put("cea-ssa", cookiesMap.get("cea-ssa").getValue());
                cookies.put("userCapabilities", cookiesMap.get("userCapabilities").getValue());
                cookies.put("userCapabilitiesEx", cookiesMap.get("userCapabilitiesEx").getValue());
                cookies.put("XSRF-TOKEN", xsrfToken);

                String cookiesAsString = cookies.toString();
                System.out.println("Las Cookies con formato son: " + cookiesAsString);

                
                // PASO 4 :: REQUEST AL REPORTE
                // Construir la solicitud GET
                // Construir la solicitud GET con las cookies
                Invocation.Builder builder_report = client.target(apiUrlReport)
                        .request(MediaType.APPLICATION_JSON)
                        .header(authorizationHeader, sessionKey); // Asegúrate de que sessionKey esté definido
                for (Map.Entry<String, String> cookieEntry : cookies.entrySet()) {
                    builder_report.cookie(new Cookie(cookieEntry.getKey(), cookieEntry.getValue()));
                }

                // Realizar la solicitud GET
                Response reportResponse = builder_report.get();

                // obtener la respuesta como HTML
                String htmlContent = reportResponse.readEntity(String.class);

                // Imprimir o procesar el contenido HTML como desees
                // ...

                // Cierra el cliente Jersey
                client.close();
                
                // Devuelve una respuesta ResponseEntity con el contenido HTML                
                /*
                return ResponseEntity.status(HttpStatus.OK)
                    .header("Content-Type", "text/html")
                    .body(htmlContent);
                */
                return htmlContent;
            } else {
                System.err.println("Error en la solicitud. Código de respuesta: " + response.getStatus());
                String errorMessage = "Error en la solicitud. Código de respuesta: " + response.getStatus();
                return errorMessage;
            }
        } finally {
            // Cerrar el cliente Jersey
            client.close();
        }
    }
}
