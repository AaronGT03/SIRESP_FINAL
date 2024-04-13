package mex.edu.utez.siresp.siresp.utils;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Response <T>{
   T data;
   boolean error;
   int status;
   String message;
}
