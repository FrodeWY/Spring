package com.controller;

import com.domain.Animal;
import com.domain.Error;
import com.exception.AnimalNotFoundException;
import com.repository.AnimalRepository;

import com.repository.ErrorRepository;
import com.service.AnimalService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
/**
 * @param @RestController 为控制器默认设置消息转换，spring 将会为该控制器所有处理方法应用消息转换功能*/
@RestController
@RequestMapping("/api")
public class RestfulController {
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private ErrorRepository errorRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AnimalService animalService;

   /* @ExceptionHandler(value = AnimalNotFoundException.class)
    public ResponseEntity<Error>AnimalNotFound(AnimalNotFoundException e){
        Integer code=e.getCode();
        Long id =e.getId();
        Error error = errorRepository.findByCode(code);
        error.setMessage("Animal ["+id+"]"+error.getMessage());
        return new ResponseEntity<Error>(error,HttpStatus.NOT_FOUND);
    }*/
   /** 在明确AnimalNotFoundException http状态码一定为404时，就没有必要使用ResponseEntity了，因为使用了@RestController，@ResponseBody都没有必要使用，使代码更简洁*/
   @ExceptionHandler(value = AnimalNotFoundException.class)
   @ResponseStatus(value = HttpStatus.NOT_FOUND)
   public Error AnimalNotFound(AnimalNotFoundException e){
       Integer code=e.getCode();
       Long id =e.getId();
       Error error = errorRepository.findByCode(code);
       error.setMessage("Animal ["+id+"]"+error.getMessage());
       return error;
   }
    @GetMapping("rest_token")
    @ApiOperation(value = "获取jwt token，并携带token发送请求", notes = "获取token，并请求")
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
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:8081/api/authenticate", httpEntity, String.class);
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
    @GetMapping(produces = "application/xml;charset=UTF-8",value = "animals")
    @ApiOperation(value = "查询所有动物，只处理Accept头部信息包含“application/xml”的请求")
    public @ResponseBody
    List<Animal> animals(){
        List<Animal> all = animalRepository.findAll();
        return all;
    }
    @GetMapping(produces = "application/json;charset=UTF-8",value = "animals")
    @ApiOperation(value = "查询所有动物,只处理Accept头部信息包含“application/json”的请求", notes = "将java对象转为json格式返回")
    public @ResponseBody
    List<Animal> animals2(){
        List<Animal> all = animalRepository.findAll();
        return all;
    }
    /**
     * 在响应中设置头部信息：
     * saveAnimalJson()方法中，我们在处理post的过程中创建了一个新的Animal资源，但是按照一般的写法return save，我们返回的HTTP状态码200（ok），
     * 这并不准确，我们应该告诉客户端创建了新的资源，HTTP201不仅能表明请求成功完成，还能描述创建了新的资源。
     * 另一方面，我们应该告诉客户端新的资源在哪，也就是给客户端一个新资源对应的URL，将资源的URL放在响应的Location头部信息中，并反给客户端是
     * 比较好的方式*/
    @PostMapping(value = "animal",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "创建一个Animal，content-type='application/json'")
    public @ResponseBody ResponseEntity<Animal> saveAnimalJson(@RequestBody Animal animal, UriComponentsBuilder ucb) throws URISyntaxException {
        Animal save = animalRepository.save(animal);
        HttpHeaders headers=new HttpHeaders();
        /*URI uri=new URI("http:localhost:8080/api/animal/"+save.getId());//这种创建方式，通过硬编码，一旦不是在本地运行的话，就不试用了*/
        /*UriComponentsBuilder 会预先配置已知的信息如host、port以及servlet内容，它会从处理器方法所对应的请求中获取这些基础信息*/
        URI uri=ucb.path("/api/")
                .path("animal/")
                .path(String.valueOf(save.getId()))
                .build()
                .toUri();
        headers.setLocation(uri);
        return  new ResponseEntity<Animal>(save,headers,HttpStatus.CREATED);
    }
    /**
     * @param @ResponseBody:告诉spring再把数据发送给客户端时，要使用某一个消息器（http信息转换器）
     * @param @RequestBody:告诉spring查找某个消息转换器，将来自客户端的资源表述转为对象*/
   /* @PostMapping(value = "animal",consumes = "application/xml;charset=UTF-8",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "创建一个Animal，content-type='application/xml'")
    public @ResponseBody Animal saveAnimalXml(@RequestBody Animal animal){
        Animal save = animalRepository.save(animal);
        return save;
    }*/

    /**
     * 一个好的REST API 不仅要能够在服务器和客户端之间传递资源，
     * 还能够给客户端提供额外的元数据，帮客户端理解资源或者在请求中出现了什么情况
     * @return  responseEntity:该对象能够包含更多相应相关的元数据，responseEntity还包含了responseBody的语义，
     * 因此负载部分将会渲染到响应体中，所以就没有必要加@ResponseBody*/
    @GetMapping(value = "animal/{id}",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据Id查找Animal")
    public Animal findAnimalById(@ApiParam("Animal Id")@PathVariable Long id){
        Animal one = animalService.findOne(id);
        /*HttpStatus status=one==null?HttpStatus.NOT_FOUND:HttpStatus.OK;
        return new ResponseEntity<Animal>(one,status);*/
        if(one==null){
           throw new AnimalNotFoundException(404,id);
        }else {
            return one;
        }

    }

    @PutMapping(value = "animal"/*,consumes = "application/json",produces = "application/json"*/)
    @ApiOperation(value = "更新某个animal")
    public Animal updateAnimal(@RequestBody Animal animal){
        Long animalId = animal.getId();
        Animal one = animalRepository.findOne(animalId);
        if(one==null){
            throw new  AnimalNotFoundException(404,animalId);
        }
        Animal saveAndFlush = animalRepository.saveAndFlush(animal);
        System.out.println(saveAndFlush.getName());
        return saveAndFlush;
    }

}
