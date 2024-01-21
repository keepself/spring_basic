package com.encore.basic.servletjsp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello-servlet-jsp-post")
public class HelloServletJspPOST extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name =req.getParameter("test1");
        String email =req.getParameter("test2");
        String password =req.getParameter("test3");

//        콘솔에 출력
        System.out.println(name);
        System.out.println(email);
        System.out.println(password);
//        응답header
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");
    //        응답body
            PrintWriter out = resp.getWriter();
            out.print("ok");


//          버퍼를 통해 조립이 이루어지므로, 버퍼를 비우는 과정.
        out.flush();


    }
}