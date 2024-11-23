package uz.pdp.food_recipe_app.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.food_recipe_app.entity.Attachment;
import uz.pdp.food_recipe_app.enums.ErrorTypeEnum;
import uz.pdp.food_recipe_app.exceptions.RestException;
import uz.pdp.food_recipe_app.repository.AttachmentRepository;
import uz.pdp.food_recipe_app.util.BaseURI;

import java.io.*;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.VIDEO)
public class VideoController {
    private final AttachmentRepository attachmentRepository;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/play/{videoId}")
    public ResponseEntity<Resource> playVideo(
            @PathVariable UUID videoId,
            HttpServletRequest request) throws IOException {

        Attachment video = attachmentRepository.findById(videoId)
                .orElseThrow(RestException.thew(ErrorTypeEnum.VIDEO_NOT_FOUND));

        File file = new File(video.getFilePath());
        if (!file.exists()) {
            throw RestException.restThrow(ErrorTypeEnum.FILE_NOT_FOUND);
        }

        long fileSize = file.length();

        String rangeHeader = request.getHeader(HttpHeaders.RANGE);
        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
            long start = 0;
            long end = fileSize - 1;

            String[] ranges = rangeHeader.substring("bytes=".length()).split("-");
            if (ranges.length > 0) {
                start = Long.parseLong(ranges[0]);
            }
            if (ranges.length > 1 && !ranges[1].isEmpty()) {
                end = Long.parseLong(ranges[1]);
            }

            if (start > end || start >= fileSize) {
                return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE).build();
            }

            end = Math.min(end, fileSize - 1);

            try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
                raf.seek(start);
                byte[] bytes = new byte[(int) (end - start + 1)];
                raf.read(bytes);

                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                        .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                        .header(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileSize)
                        .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(end - start + 1))
                        .body(new ByteArrayResource(bytes));
            }
        }

        try (InputStream inputStream = new FileInputStream(file)) {
            byte[] bytes = inputStream.readAllBytes();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileSize))
                    .body(new ByteArrayResource(bytes));
        }
    }

}
