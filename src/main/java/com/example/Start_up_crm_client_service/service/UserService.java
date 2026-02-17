package com.example.Start_up_crm_client_service.service;

import com.example.Start_up_crm_client_service.dto.UserDto;
import com.example.Start_up_crm_client_service.dto.UsersValidationResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    UserDto getUserById(Long id);
    void deleteUserById(Long id);
    UserServiceImpl.UserCounts getUserCounts();
    byte[] getProfileImage(Long userId);
    void requestPasswordReset(String email);
    void resetPassword(String token, String newPassword);
    void uploadProfileImage(Long userId, MultipartFile file) throws IOException;
    List<UserDto> getAllUsers();
    UserDto updateUser(UserDto userDto);


    // for RestTemplate  for consuming
    boolean isUserExistById(Long userId);
    UsersValidationResponse isUsersExistByIds(List<Long> userIds);
}
