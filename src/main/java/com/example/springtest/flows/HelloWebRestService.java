package com.example.springtest.flows;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.stereotype.Component;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.Map;
import java.util.HashMap;
import javax.ws.rs.core.Cookie; // Importación para Cookie
import javax.ws.rs.core.NewCookie; // Importación para NewCookie
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.UriBuilder;

@Component
@Path("/helloworld")
public class HelloWebRestService {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String helloMessage() {

        // PASO 1 AUNTENTICAR
        // URL del servicio
        String apiUrl = "https://sigtest.mh.gob.sv/ibmcognos/bi/v1/disp/rds/auth/logon";

        // Parámetros en formato XML
        String xmlData = "<auth:credentials xmlns:auth='http://developer.cognos.com/schemas/ccs/auth/types/1'>" +
                "   <auth:credentialElements><auth:name>CAMNamespace</auth:name>" +
                "   <auth:value><auth:actualValue>AD-Test</auth:actualValue></auth:value></auth:credentialElements>" +
                "   <auth:credentialElements><auth:name>CAMUsername</auth:name>" +
                "   <auth:value><auth:actualValue>walther.figueroa</auth:actualValue></auth:value></auth:credentialElements>" +
                "   <auth:credentialElements><auth:name>CAMPassword</auth:name>" +
                "   <auth:value><auth:actualValue>Administrador@123</auth:actualValue></auth:value></auth:credentialElements>" +
                "   </auth:credentials>";

        // Crear un cliente Jersey
        Client client = ClientBuilder.newClient();

        // Construir la solicitud GET con los parámetros
        Invocation.Builder builder = client.target(apiUrl)
                .queryParam("xmlData", xmlData)
                .request(MediaType.APPLICATION_JSON);
                
        try {
            // Realizar la solicitud GET
            Response response = builder.get();

            // Procesar la respuesta
            if (response.getStatus() == 200) {
                String responseBody = response.readEntity(String.class);
                System.out.println("Respuesta LOGON exitosa: " + responseBody);


                // PASO 2 CONSTRUIR COOKIES PARA EL REPORTE
                // Crear un mapa de cookies vacío
                Map<String, String> cookies = new HashMap<>();

                // Obtener las cookies de la respuesta
                Map<String, NewCookie> cookiesMap = response.getCookies();

                // Agregar las cookies al mapa de cookies
                cookies.put("XSRF-TOKEN", cookiesMap.get("XSRF-TOKEN").getValue());
                cookies.put("cam_passport", cookiesMap.get("cam_passport").getValue());
                cookies.put("usersessionid", cookiesMap.get("usersessionid").getValue());
                cookies.put("CRN", cookiesMap.get("CRN").getValue());
                cookies.put("cea-ssa", cookiesMap.get("cea-ssa").getValue());
                cookies.put("userCapabilities", cookiesMap.get("userCapabilities").getValue());
                cookies.put("userCapabilitiesEx", cookiesMap.get("userCapabilitiesEx").getValue());
                //cookies.put("up", cookiesMap.get("up").getValue());
                
                String cookiesAsString = cookies.toString();
                System.out.println("Las Cookies con formato son: " + cookiesAsString);


                // PASO 3 :: REQUEST AL REPORTE
                // URL del servicio con los nuevos parámetros
                String apiUrlReport = "https://sigtest.mh.gob.sv/ibmcognos/bi/v1/disp/rds/reportData/report/i7A96DE90EB364963BA27A68CAB0338B9";
                
                // Agregar los parámetros en la consulta
                String version = "3";
                String format = "HTML";
                String p_UsuaroCon = "aa017";

                // Construir la solicitud GET con las cookies
                UriBuilder uriBuilder = UriBuilder.fromUri(apiUrlReport)
                .queryParam("v", version)
                .queryParam("fmt", format)
                .queryParam("p_UsuaroCon", p_UsuaroCon);

                String fullUrl = uriBuilder.toTemplate();
                System.out.println("URL completa del request REPORT: " + fullUrl);

                // Construir la solicitud GET y agregar la cookie de sesión
                Invocation.Builder builder2 = client.target(fullUrl).request(MediaType.APPLICATION_JSON);
                for (Map.Entry<String, String> cookieEntry : cookies.entrySet()) {
                    builder2.cookie(new Cookie(cookieEntry.getKey(), cookieEntry.getValue()));
                }

                // Realizar la solicitud GET
                Response response2 = builder2.get();

                                            

                // Procesar la respuesta (puedes manejarla según tus necesidades)
                if (response2.getStatus() == 200) {
                    System.out.println("Respuesta exitosa Reporte");
                    // obtener la respuesta como HTML
                    String htmlContent = response2.readEntity(String.class);
                    return htmlContent;
                } else {
                    String errorMessageReport = "La solicitud GET REPORT no fue exitosa. Código de estado: " + response.getStatus();
                    System.out.println(errorMessageReport);
                    return errorMessageReport;
                }



            } else {                
                String errorMessage = "La solicitud GET LOGON no fue exitosa. Código de estado: " + response.getStatus();
                System.out.println(errorMessage);
                return errorMessage;
            }


    } finally {
        // Cerrar el cliente Jersey
        client.close();
    }
        
}

}
