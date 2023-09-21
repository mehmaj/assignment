package ir.snapppay.assignment.scrapper.track.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddUrlDTO {
    @NotBlank(message = "url is required!")
    @Size(max = 500,message = "Url length must be less than 500 characters!")
    private String url;
}
