package com.springframework.csscapstone.controller.moderator;

import com.springframework.csscapstone.services.ModeratorHomeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.Moderator.V4_COLLABORATOR_COUNT;
import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.Moderator.V4_ENTERPRISE_COUNT;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Home (Moderator)")
@RequiredArgsConstructor
@RestController
public class ModeratorHomeController {

    private final ModeratorHomeService service;

    @GetMapping(V4_ENTERPRISE_COUNT)
    public ResponseEntity<?> getCountEnterprise() {
        long result = this.service.countEnterprise();
        return ok(result);
    }

    @GetMapping(V4_COLLABORATOR_COUNT)
    public ResponseEntity<?> getCountCollaborator() {
        long result = this.service.countCollaborator();
        return ok(result);
    }

}
