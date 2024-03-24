package Api;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import Model.Address;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class CepApiConsumer {
    public Address getAddress(String cep) throws IOException, InterruptedException {
        //Configura a Url da API
        var url =
                "http://viacep.com.br/ws/".concat(cep).concat("/xml/");
        //Cria o objeto responsável pela resquest
        var resquest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url));
        //Cria o client para a resquest
        var httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(8))
                .build();
        //Cria o objeto response da resquest
        var response = httpClient.send(resquest.build(), HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        //Biblioteca que convert o formato Json em objeto Java
//        var gson = new Gson();
//        //Conversão do formato Json para o objeto Address
//        var address = gson.fromJson(response.body(), Address.class);
        //Biblioteca que converte o formato Xml em objeto Java
        XmlMapper mapper = new XmlMapper();
        //Conversão do formato Json para o objeto Address
        var address = mapper.readValue(response.body(), Address.class);

        return address;
    }
}
