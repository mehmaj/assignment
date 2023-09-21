package ir.snapppay.assignment.scrapper.track.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.snapppay.assignment.scrapper.track.model.AddUrlDTO;
import ir.snapppay.assignment.scrapper.track.service.TrackService;
import ir.snapppay.assignment.scrapper.user.model.dto.ResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Track", description = "Track product price changes")
@RestController
@RequestMapping("/api/v1")
public class TrackController {
    final private TrackService trackService;

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @Operation(summary = "Add product URL", description = "An api for adding product digikala URL")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "URL saved successfully!")})
    @PostMapping("/track")
    public ResponseEntity<ResponseDTO> addURL(@Valid @RequestBody AddUrlDTO dto) {
        return ResponseEntity.ok(trackService.addURL(dto));
    }

}
