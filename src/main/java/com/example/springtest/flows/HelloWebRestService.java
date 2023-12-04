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
    public Response helloMessage() {

        // Crear un cliente Jersey
        Client client = ClientBuilder.newClient();
      
            // PASO 1 :: REQUEST AL DASHBOARD
            // URL del DASHBOARD
            String apiUrlReport = "https://sigtest.mh.gob.sv/ibmcognos/bi/?perspective=dashboard&pathRef=.public_folders%2FModulo%2Bde%2BGesti%25C3%25B3n%2BDirecci%25C3%25B3n%2BGeneral%2Bde%2BAduanas%2FModelo%2BVehiculos%2BUsados%2B%2528ARIVU%2529%2FInformes%2BArivus%2FDASHBORAD%2FDASHBOARD%2BARIVU%2BCONTEO&action=view&mode=dashboard&CAMNamespace=AD-Test&CAMUsername=walther.figueroa&CAMPassword=Administrador@123&ui_appbar=false&ui_navbar=false";
            
            // Construir la solicitud GET
            UriBuilder uriBuilder = UriBuilder.fromUri(apiUrlReport);
            String fullUrl = uriBuilder.toTemplate();
            System.out.println("URL completa del request REPORT: " + fullUrl);

            Invocation.Builder builder2 = client.target(fullUrl).request(MediaType.APPLICATION_JSON);
            
            // Realizar la solicitud GET
            Response response2 = builder2.get();

            // Procesar la respuesta (puedes manejarla según tus necesidades)
            if (response2.getStatus() == 200) {
                System.out.println("Respuesta exitosa Reporte");                
                client.close();

                //Devolver respuesta del request
                //String ResponseContent = response2.readEntity(String.class);
                //return htmlContent;

                // Crear la estructura HTML con la etiqueta iframe y el contenido de la respuesta
                String htmlContent = "<html><head><title>Mi Pagina con Iframe</title>";

                htmlContent += "<style>";
                htmlContent += "body {";
                htmlContent += "    font-family: Arial, sans-serif;";
                htmlContent += "    margin: 0;";
                htmlContent += "    padding: 0;";
                htmlContent += "    display: flex;";
                htmlContent += "    flex-direction: column;";
                htmlContent += "    align-items: center;";
                htmlContent += "    justify-content: center;";
                htmlContent += "    height: 100vh;";
                htmlContent += "    background-color: #f2f2f2;";
                htmlContent += "}";
                htmlContent += "h1 {";
                htmlContent += "    margin-bottom: 20px;";
                htmlContent += "}";
                htmlContent += "iframe {";
                htmlContent += "    width: 80%;";
                htmlContent += "    height: 60vh;";
                htmlContent += "    border: none;";
                htmlContent += "    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);";
                htmlContent += "}";
                htmlContent += "</style>";
                
                htmlContent += "</head><body>";
                htmlContent += "<h1>Mi Pagina con Iframe desde Java</h1>";

                htmlContent += "<iframe src='https://sigtest.mh.gob.sv/ibmcognos/bi/?perspective=dashboard&pathRef=.public_folders%2FModulo%2Bde%2BGesti%25C3%25B3n%2BDirecci%25C3%25B3n%2BGeneral%2Bde%2BAduanas%2FModelo%2BVehiculos%2BUsados%2B%2528ARIVU%2529%2FInformes%2BArivus%2FDASHBORAD%2FDASHBOARD%2BARIVU%2BCONTEO&action=view&mode=dashboard&CAMNamespace=AD-Test&CAMUsername=walther.figueroa&CAMPassword=Administrador@123&ui_appbar=false&ui_navbar=false' width='320%' height='200' frameborder='0' allow='encrypted-media' allowfullscreen='' sandbox='allow-same-origin allow-scripts'></iframe>";
                htmlContent += "</body></html>";

                return Response.ok(htmlContent, MediaType.TEXT_HTML).build();

            } else {
                // Crear la estructura HTML del error personalizado
                String htmlContent = "<html><head><title>Error al cargar el contenido</title></head><body>";
                htmlContent += "<h1>Error</h1>";
                htmlContent += "<p>Error.</p>";
                htmlContent += "</body></html>";

                return Response.ok(htmlContent, MediaType.TEXT_HTML).build();
            }           
        
}

}
