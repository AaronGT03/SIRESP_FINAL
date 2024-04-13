package mex.edu.utez.siresp.siresp.controller.category.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mex.edu.utez.siresp.siresp.model.category.Category;
import mex.edu.utez.siresp.siresp.model.accommodation.Accommodation;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Long id;
    private String name ;
    private String description ;
    private String icon;
    private List<Accommodation> accommodations;

    public Category castToCast(){
        return  new Category(
                getId(),
                getName(),
                getDescription(),
                getIcon(),
                getAccommodations()
        );
    }

}
