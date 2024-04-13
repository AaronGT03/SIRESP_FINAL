package mex.edu.utez.siresp.siresp.model.email;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Email {
    private String transmitter;
    private String receptor;
    private String title;
    private  String text;
    private  String body;
}
