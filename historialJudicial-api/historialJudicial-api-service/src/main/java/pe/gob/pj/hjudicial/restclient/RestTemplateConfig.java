package pe.gob.pj.hjudicial.restclient;

import java.io.IOException;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
	
	@Bean
    public RestTemplate restTemplate() throws IOException {
        return new RestTemplate(clientHttpRequestFactory());
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() throws IOException {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        //factory.setConnectTimeout(Integer.parseInt(ConfiguracionPropiedades.getInstance().getProperty(Propiedades.TIMEOUT_API_CONECTION))*1000);
        //factory.setReadTimeout(Integer.parseInt(ConfiguracionPropiedades.getInstance().getProperty(Propiedades.TIMEOUT_API_READ))*1000);
        return factory;
    }
}
