package com.controller;

import com.domain.Animal;
import com.repository.AnimalRepository;
import io.swagger.annotations.Api;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@Api("/api")
public class RestController {
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("rest_token")
    public @ResponseBody String  rest_token(){
        HttpHeaders headers=new HttpHeaders();
        MediaType type=MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        JSONObject object=new JSONObject();
        object.put("password","admin");
        object.put("username","admin");
        object.put("rememberme","true");
        HttpEntity<String > httpEntity=new HttpEntity<String>(object.toString(),headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://192.168.16.162:8081/api/authenticate", httpEntity, String.class);
        String body=responseEntity.getBody();
        JSONObject tokenJsonObject=new JSONObject(body);
        String token=tokenJsonObject.getString("id_token");
        headers.add("Authorization","Bearer "+token);
        HttpEntity<String > httpEntity2=new HttpEntity<String>(object.toString(),headers);
        ResponseEntity<String> forEntity = restTemplate.exchange("http://127.0.0.1:8081/api/users", HttpMethod.GET,httpEntity2,String .class);
        String userString=forEntity.getBody();
        System.out.println(userString);
        return userString;
    }
    /**
     * produces:可制定返回的response的媒体类型和字符集,且只处理Accept头部信息包含“application/json”的请求，其他任何类型的请求，即使他的URl匹配制定的路径
     * 并且是Get请求，也不会被这个方法处理，这样的请求会被其他的方法进行处理（如果存在适当的方法），或者返回客户端http 406（NOT Acceptable） 响应*/
    @GetMapping(produces = "application/xml;charset=UTF-8",value = "Animals")
    public @ResponseBody
    List<Animal> animals(){
        List<Animal> all = animalRepository.findAll();
        return all;
    }
    @GetMapping(produces = "application/json;charset=UTF-8",value = "Animals")
    public @ResponseBody
    List<Animal> animals2(){
        List<Animal> all = animalRepository.findAll();
        return all;
    }

    @PostMapping(value = "save_animal",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public @ResponseBody Animal saveAnimal(@RequestBody Animal animal){
        Animal save = animalRepository.save(animal);
        return save;
    }
}
