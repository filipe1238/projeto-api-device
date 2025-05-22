package com.app.projetoapi;

import com.app.projetoapi.utils.ParentEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class MainTestClass<T> {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

    public MainTestClass() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    public String getBaseUrl() {
        return "http://localhost:" + port + "/api/v1";
    }

    public HttpEntity<T> createRequestEntity(T requestBody) {
        return new HttpEntity<>(requestBody, headers);
    }


    // <=================== MÉTODOS ABSTRATOS ===================>
    public abstract String getEndpoint();
    public abstract T returnNewObject();
    public abstract void setProperty(T object, String property);


    // <=================== MÉTODOS DE TESTE ===================>
    private String returnList(){
        String url = getBaseUrl() + getEndpoint();
        return this.restTemplate.getForObject(url, String.class);
    }

    private T createObject(String nomeComum){
        T objeto = returnNewObject();
        this.setProperty(objeto, nomeComum);
        String url = getBaseUrl() + getEndpoint();
        return this.restTemplate.postForObject(url, createRequestEntity(objeto), (Class<T>) objeto.getClass());
    }

    private void deleteObject(Integer id){
        String url = getBaseUrl() + getEndpoint() + "/" + id;
        this.restTemplate.delete(url);
    }

    private T returnObjectById(Integer id){
        String url = getBaseUrl() + getEndpoint() + "/" + id;
        return (T) this.restTemplate.getForObject(url, returnNewObject().getClass());
    }

    private T updateObject(T objetoComId){
        ParentEntity objetoComIdCast = (ParentEntity) objetoComId;
        String url = getBaseUrl() + getEndpoint() + "/" + objetoComIdCast.getId();

        this.setProperty(objetoComId, "teste atualizado");
        this.restTemplate.put(url, createRequestEntity(objetoComId));
        return returnObjectById(objetoComIdCast.getId());
    }

    // <=================== TESTES ===================>
    @Test
    public void testList() {
        String artistas = returnList();
        assertThat(artistas).isNotEmpty();
    }

    @Test
    public void testPost() {
        ParentEntity objetoComID = (ParentEntity) createObject("teste");
        assertThat(objetoComID).isNotNull();
        assertThat(objetoComID.getId()).isNotNull();
        assertThat(objetoComID.toString()).contains("teste");

        deleteObject(objetoComID.getId());
    }

    @Test
    public void testGet() {
        ParentEntity objetoComID = (ParentEntity) createObject("teste");
        assertThat(objetoComID).isNotNull();
        assertThat(objetoComID.getId()).isNotNull();

        ParentEntity objetoRetornado = (ParentEntity) returnObjectById(objetoComID.getId());
        assertThat(objetoRetornado.toString()).contains("teste");
        assertThat(objetoRetornado).isNotNull();
        assertThat(objetoRetornado.getId()).isEqualTo(objetoComID.getId());

        deleteObject(objetoRetornado.getId());
    }


    @Test
    public void testPut() {
        ParentEntity objetoComID = (ParentEntity) createObject("teste");
        assertThat(objetoComID).isNotNull();
        assertThat(objetoComID.getId()).isNotNull();

        ParentEntity objetoAtualizado = (ParentEntity) updateObject((T) objetoComID);
        assertThat(objetoAtualizado.toString()).contains("teste atualizado");
        assertThat(objetoAtualizado).isNotNull();
        assertThat(objetoAtualizado.getId()).isEqualTo(objetoComID.getId());

        deleteObject(objetoAtualizado.getId());
    }

    @Test
    public void testDelete() {
        ParentEntity objetoComID = (ParentEntity) createObject("teste");
        assertThat(objetoComID).isNotNull();
        assertThat(objetoComID.getId()).isNotNull();

        deleteObject(objetoComID.getId());
        T objectNull = returnObjectById(objetoComID.getId());
        assertThat(objectNull).isNull();
    }
}
