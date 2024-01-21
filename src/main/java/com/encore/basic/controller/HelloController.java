package com.encore.basic.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.encore.basic.domain.Hello;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;


//모든 요청에 ResponseBody를 붙이고 싶다면, RestController사용
@Controller
@RequestMapping("hello")
//클래스차원에서 url경로를 지정하고 싶다면 @RequestMapping을 클래스 위에 선언하면서 경로지정.
public class HelloController {
    //    @responseBody가 없고
//    responseBody가 없고, return타입이 String이면 templates밑에 html파일 리턴
//    data만을 return할때는 @ResponseBody를 붙인다.
    @RequestMapping(value = "string", method = RequestMethod.GET)


    public String helloString() {
        return "hello_string";
    }

    @GetMapping("screen")
    public String helloScreen() {
        return "screen";
    }

    @GetMapping("screen-model-param")
//   localhost:8080/hello/screen-model-param?name=hongildong의 방식으로 호출(파라미터 방식)
    public String helloScreenModelParam(@RequestParam(value = "name") String inputname, Model model) {
//        화면에 data를 넘기고싶을때는 model객체 사용
//        model에 key:value형식으로 전달
        model.addAttribute("myData", inputname);
        return "screen";
    }

    //  pathvariable 방식은 url을 통해 자원의 구조를 명확하게 표현할수 있어
//    좀더 RestFul API 디자인에 적합
    @GetMapping("screen-model-path/{id}")
    public String helloScreenModelPath(@PathVariable(value = "id") int id, Model model) {
        model.addAttribute("myData", id);
        return "member2/screen";
    }

    @GetMapping("json")
    @ResponseBody
    public Hello helloJson() {
        Hello hello = new Hello();
        hello.setEmail("keepself@naver.com");
        hello.setName("leechangseon");
        hello.setPassword("1234");
        System.out.println(hello);
        return hello;
    }


    //Form태그로 x-www 데이터 처리

    @GetMapping("form-screen")
    public String formScreen() {
        return "form";
    }

    @PostMapping("/form-post-handle")
    @ResponseBody
    public String formPostHandle(
            @RequestParam(value = "email") String inputEmail,
            @RequestParam(value = "name") String inputName,
            @RequestParam(value = "password") String inputPassword
    ) {
        System.out.println("이름 : " + inputEmail);
        System.out.println("이름 : " + inputName);
        System.out.println("이름 : " + inputPassword);
        return "정상출력";
    }

    //    form태그를 통한 body의 데이터 형태가 key1=value1&key2=value2

    //    public String formScreen(@RequestParam(value = "email") String inputname),
    //        (@RequestParam(value = "name") String inputname),
    //            ,(@RequestParam(value = "password") String inputname)
    //
    @PostMapping("/form-post-handle2")
    @ResponseBody
//    spring에서 Hello클래스의 인스턴스 자동 메핑하여 생성
//    form-data형식 즉, x www-url인코딩 형식의 경우 사용
//    이를 데이터 바인딩 이라 부른다.(Hello클래스에 setter필수
//    Requestparam 대신에 사용하는것
    public String formPostHandle2(@RequestBody Hello hello) {
        System.out.println(hello);
        return "정상출력";

    }


    //json데이터
    @GetMapping("json-screen")
    public String jsonScreen() {

        return "hello-jason-screen";
    }

    @PostMapping("/json-post-handle")
    @ResponseBody
    public String jsonPostHandle(@RequestBody Map<String, String> body) {
        System.out.println("이름 : " + body.get("name"));
        System.out.println("이메일 : " + body.get("email"));
        System.out.println("비밀번호 : " + body.get("password"));
        Hello hello = new Hello();
        hello.setName(body.get("name"));
        hello.setEmail(body.get("email"));
        hello.setPassword(body.get("password"));
        return "/hello/screen";
    }

    @PostMapping("/json-post-handle2")
    @ResponseBody
    public String jsonPostHandle2(@RequestBody JsonNode body) {
        Hello hello = new Hello();
        System.out.println("이름 : " + body.get("name").asText());
        System.out.println("이메일 : " + body.get("email").asText());
        System.out.println("비밀번호 : " + body.get("password").asText());


        return "/hello/screen";

    }

    @PostMapping("/json-post-handle3")
    @ResponseBody
    public String jsonPostHandle3(@RequestBody Hello hello) {
        System.out.println(hello);
        return "/hello/screen";
    }

    @PostMapping("httpservlet")
    @ResponseBody
    public String httpServlet(HttpServletRequest req) {
//        HttpServletRequest객체에서 header정보 추출
        System.out.println(req.getContentType());
        System.out.println(req.getMethod());
//        session : 로그인(auth) 정보에서 필요한 정보값을 추출할때 많이 사용
        System.out.println(req.getSession());
        System.out.println(req.getHeader("Accept"));

//        HttpServletRequest객체에서 body정보 추출
        System.out.println(req.getParameter("test1"));
        System.out.println(req.getParameter("test2"));
//        req.getReader()를 통해 BufferedReader 받아 직접 파싱

        return "OK";
    }

    @GetMapping("hello-servlet-jsp-get")
    public String helloServletJspGe(Model model) {
        model.addAttribute("myData", "jsp test data");
        return "hello-jsp";
    }


}