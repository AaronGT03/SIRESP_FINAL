package mex.edu.utez.siresp.siresp.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;

public class CustomHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{



        if(request.getRequestURL().toString().equals("http://localhost:8080/api-sirep/user/upload")
                ||request.getRequestURL().toString().equals("http://localhost:8080/api-sirep/host/upload")
                ||request.getRequestURL().toString().equals("http://localhost:8080/api-sirep/accommodation/upload")){


        }else if(request.getMethod().equals("GET")||request.getMethod().equals("DELETE")){
            return  true;
        }else{
            StringBuilder requesBody = new StringBuilder();
            request.getReader().lines().forEach(requesBody::append);
            String encryptedBody = requesBody.toString();

            JSONObject jsonObject = new JSONObject(encryptedBody);
            String data = jsonObject.getString("data");

            String decryptedBody = Encrypt.decrypt(data);


            JSONObject jsonObject2 = new JSONObject(decryptedBody);
            request.setAttribute("jsonData", jsonObject2);
        }




        return true;
    }


}
