package mex.edu.utez.siresp.siresp.controller.activityhistory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mex.edu.utez.siresp.siresp.model.activityhistory.ActivityHistory;
import mex.edu.utez.siresp.siresp.model.user.User;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActivityHistoryDto {
    private Long id;
    private String action;
    private String description;
    private User user;
    public ActivityHistory casToCast(){
        return  new ActivityHistory(
                getId(),
                getAction(),
                getDescription(),
                getUser()
        );
    }

}
