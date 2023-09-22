package ir.snapppay.assignment.scrapper.track.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddUrlDTO {
    @NotBlank(message = "url is required!")
    @Size(max = 500,message = "Url length must be less than 500 characters!")
    @Pattern(regexp = ".*dkp-.*",message = "URL pattern is invalid!")
    @Schema( example = "https://www.digikala.com/product/dkp-12144455/%D9%87%D8%AF%D8%B3%D8%AA-%D9%85%D8%AE%D8%B5%D9%88%D8%B5-%D8%A8%D8%A7%D8%B2%DB%8C-%D8%A8%D9%84%D9%88%D8%AA%D9%88%D8%AB%DB%8C-%D8%B3%D9%86%D9%87%D8%A7%DB%8C%D8%B2%D8%B1-%D9%85%D8%AF%D9%84-ldy-momentume-fore-bt-komani-4/")
    private String url;
}
