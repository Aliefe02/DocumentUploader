package com.springRest.DocumentUploader.controllers;

import com.springRest.DocumentUploader.entity.User;
import com.springRest.DocumentUploader.models.NotificationDTO;
import com.springRest.DocumentUploader.services.NotificationService;
import com.springRest.DocumentUploader.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.springRest.DocumentUploader.controllers.UserController.getUserFromToken;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification/")
public class NotificationController {

    private NotificationService notificationService;
    private UserService userService;

    @GetMapping("all")
    public Page<NotificationDTO> getNotifications(@RequestParam(required = false) Integer pageNumber,
                                                  @RequestParam(required = false) Integer pageSize){

        User user = getUserFromToken(userService);

        return notificationService.listNotifications(user, pageNumber, pageSize);

    }


}
