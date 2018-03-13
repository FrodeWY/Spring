package com.controller;

import com.domain.Animal;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("rest_client")
public class RestClientController {
    private final RestTemplate restTemplate;

    public RestClientController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(value = "/users")
    @ApiOperation(value = "查询video的所有user")
    public String users() {
        HttpHeaders headers=new HttpHeaders();
        MediaType mediaType=MediaType.APPLICATION_JSON;
        headers.setContentType(mediaType);
        headers.add("Accept",mediaType.toString());
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("username","admin");
        jsonObject.put("password","admin");
        jsonObject.put("rememberme",true);
        HttpEntity<String> httpEntity=new HttpEntity<String>(jsonObject.toString(),headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:8081/api/authenticate", httpEntity, String.class);
        String body = responseEntity.getBody();
        String id_token = new JSONObject(body).getString("id_token");
        headers.add("Authorization","Bearer "+id_token);
        HttpEntity<String> entity=new HttpEntity<String>(headers);
        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:8081/api/users", HttpMethod.GET, entity, String.class);
        return exchange.getBody();
    }
    /** getForObject()只返回资源（通过HTTP 信息转换器将其转化为Java对象）*/
    @GetMapping(value = "rest_animals/{id}",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "通过restTemplate.getForObject()查询某个animal")
    public Animal animalList(@PathVariable Long id){

        /*使用可变参数传参*/
        Animal forObject = restTemplate.getForObject("http://localhost:8080/api/animal/{id}", Animal.class, id);
        Map <String,String> map=new HashMap<String, String>();
        /*使用map传参*/
        map.put("id",id.toString());
        Animal animal=restTemplate.getForObject("http://localhost:8080/api/animal/{id}", Animal.class, map);
        return forObject;
    }
    /** getForEntity()会在ResponseEntity中返回相同对象，而且ResponseEntity还带有响应的额外信息，如http状态码和响应头*/
    @GetMapping(value = "rest_last_modified/{id}",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "通过restTemplate.getForEntity()查询某个资源最后修改时间")
    public String animalList2(@PathVariable Long id){
        ResponseEntity<Animal> forEntity = restTemplate.getForEntity("http://localhost:8080/api/animal/{id}", Animal.class, id);
        Animal animal=forEntity.getBody();
        HttpHeaders headers=forEntity.getHeaders();
        Date date=new Date(headers.getLastModified());
        HttpStatus statusCode = forEntity.getStatusCode();
        if(statusCode== HttpStatus.INTERNAL_SERVER_ERROR){
            return HttpStatus.INTERNAL_SERVER_ERROR.toString();
        }
        List<String> lastModified = headers.get("Date");
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("lastModified",date);
        return  jsonObject.toString();
    }

    @PutMapping(value = "rest_animal",consumes = "application/json;charset=UTF-8")
    public void updateAnimal(@RequestBody Animal animal){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> httpEntity=new HttpEntity<String>(headers);
        JSONObject jsonObject=new JSONObject(animal);
        restTemplate.put("http://localhost:8080/api/animal",animal);
    }
    /**在post请求后获取资源位置*/
    @PostMapping(value = "rest_animal",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public URI createAnimal(@RequestBody Animal animal){
        JSONObject jsonObject=new JSONObject(animal);
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON.toString());
        HttpEntity<Animal> httpEntity=new HttpEntity<Animal>(animal,headers);
        URI requestURL = URI.create("http://localhost:8080/api/animal");
        ResponseEntity<Animal> animalResponseEntity = restTemplate.postForEntity(requestURL, httpEntity, Animal.class);
        URI location = animalResponseEntity.getHeaders().getLocation();
        URI uri = restTemplate.postForLocation("http://localhost:8080/api/animal", httpEntity);
        System.out.println("location:"+location+" uri:"+uri);
        return uri;
    }
}
