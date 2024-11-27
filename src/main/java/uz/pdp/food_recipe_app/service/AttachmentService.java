package uz.pdp.food_recipe_app.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.food_recipe_app.entity.Attachment;
import uz.pdp.food_recipe_app.exceptions.RestException;
import uz.pdp.food_recipe_app.mapper.AttachmentMapper;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.payload.file.FileRes;
import uz.pdp.food_recipe_app.repository.AttachmentRepository;
import uz.pdp.food_recipe_app.util.GlobalVar;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import static uz.pdp.food_recipe_app.enums.ErrorTypeEnum.*;


@Service
@RequiredArgsConstructor
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;

    @Transactional
    public FileRes upload(MultipartFile file) {
        if (file == null || file.isEmpty())
            throw RestException.restThrow(FILE_NOT_FOUND);

        Attachment attachment = AttachmentMapper.fromMultipartToEntity(file);
        attachmentRepository.save(attachment); //save file in db

        try {
            Files.copy(file.getInputStream(), Paths.get(attachment.getFilePath()), StandardCopyOption.REPLACE_EXISTING); //save file in memory
        } catch (IOException e) {
            throw RestException.restThrow(ERROR_SAVING_FILE);
        }
        return AttachmentMapper.fromEntityToResDto(attachment);
    }

    public ResponseEntity<?> download(UUID id, String view) {
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow(FILE_NOT_FOUND));

        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(attachment.getContentType()))
                    .headers(httpHeaders -> {
                        httpHeaders.set(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
                        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, view + "; filename=" + attachment.getOriginalName());
                    })
                    .body(Files.readAllBytes(Path.of(attachment.getFilePath())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public ResBaseMsg delete(UUID id) {
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow(FILE_NOT_FOUND));

        try {
            Files.deleteIfExists(Path.of(attachment.getFilePath()));  // deletes file from system
            attachmentRepository.deleteById(attachment.getId()); // deletes file from db
        } catch (IOException e) {
            throw RestException.restThrow(FILE_CANNOT_DELETED);
        }

        return new ResBaseMsg("File successfully deleted");
    }

    @Transactional
    public ResBaseMsg deleteByPath(String filePath) {
        if (!Files.exists(Path.of(filePath))) {
            throw RestException.restThrow(FILE_NOT_FOUND);
        }

        String filename = StringUtils.getFilename(filePath);
        String extension = StringUtils.getFilenameExtension(filePath);
        String name = filename.replace("." + extension, "");

        Attachment attachment = attachmentRepository.findById(UUID.fromString(name))
                .orElseThrow(() -> RestException.restThrow(ATTACHMENT_NOT_FOUND));

        if (!GlobalVar.getUser().getId().equals(attachment.getCreatedById()))
            throw RestException.restThrow(FORBIDDEN);

        try {
            Files.deleteIfExists(Path.of(attachment.getFilePath()));  // deletes file from system
            attachmentRepository.delete(attachment); //deleting from db
        } catch (IOException e) {
            throw RestException.restThrow(FILE_CANNOT_DELETED);
        }

        return new ResBaseMsg("Successfully deleted");
    }
}
