import { decrypt, encrypt } from "../config/aes";
import instance from "./axios";
const token = sessionStorage.getItem("token");
instance.interceptors.request.use(


     
    (config) => {
        const data = config.data;
        const url = config.url;
        const method=config.method
        

        if (url == "/user/" && method =="post") {
            
        }else if( url.includes("/accommodation/category/")  && method=="get"){

        }else if(url.includes("/accommodation/") && method=="get" ){
            console.log("entro a la peticion get");
            
          
        }
         else if(url.includes("/user/username/") && method=="get" ){


          
        }
         
        else if(url == "/category/" && method=="get"){

        }else{
           console.log("Entro Aqui")
            config.headers = {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json',
            };
        }
       
        if (data != null && url!="/user/upload"&&url!="/host/upload"&&method!="GET"&&method!="DELETE") {
            console.log("Entro Aqui?")
            const dataString = {data:encrypt(data)}
            const jsonData = JSON.stringify(dataString);
            config.data = jsonData
      
        }
     
        
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);


instance.interceptors.response.use(
    (response) => {
        if (response.config.method==="get") {
            const data = response.data;
            if (data) {
                response.data = decrypt(data);
            } 
        } 
        
       
        
        return response;
    },
    (error) => {
        return Promise.reject(error);
    }
);


export default {

    async doPost(url, data) {
        return await instance.post(url, data);
    },
    async doDelete(url) {
        return await instance.delete(url);
    },
    
    async doGet(url) {
        return await instance.get(url);
    },
    
    


 
};

