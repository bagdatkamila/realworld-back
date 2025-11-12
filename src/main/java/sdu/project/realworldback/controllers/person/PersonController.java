package sdu.project.realworldback.controllers.person;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sdu.project.realworldback.dto.ResponseUserRequestDto;
import sdu.project.realworldback.services.impl.PersonServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import sdu.project.realworldback.exceptions.AccessDeniedException;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class PersonController {

    private final PersonServiceImpl personService;

    @GetMapping
    public ResponseEntity<ResponseUserRequestDto> getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            throw new AccessDeniedException("Unauthorized"); // will be handled by GlobalExceptionHandler
        }
        String username = auth.getName();
        return ResponseEntity.ok(personService.getCurrentUser(username));
        // prefer service method that accepts username (not raw jwt)
    }
}
