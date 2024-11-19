package uz.pdp.food_recipe_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.food_recipe_app.payload.base.ApiResult;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.payload.file.FileRes;
import uz.pdp.food_recipe_app.service.AttachmentService;
import uz.pdp.food_recipe_app.util.BaseURI;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.FILE)
public class AttachmentController {
    private final AttachmentService service;

    //@PreAuthorize("isAuthenticated()")
    @PostMapping(
            value = "/upload",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ApiResult<FileRes> upload(@RequestPart("file") MultipartFile file) {
        return ApiResult.successResponse(service.upload(file));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/download/{id}")
    public ResponseEntity<?> download(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "inline") String view) {
        return service.download(id, view);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping( "/delete/{id}")
    public ApiResult<ResBaseMsg> deleteFile(@PathVariable UUID id){
        return ApiResult.successResponse(service.delete(id));
    }

}
