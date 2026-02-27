package com.example.Start_up_crm_client_service.service;

import com.example.Start_up_crm_client_service.entity.ClientHr;
import com.example.Start_up_crm_client_service.entity.User;
import com.example.Start_up_crm_client_service.repository.ClientHrRepository;
import com.example.Start_up_crm_client_service.repository.UserRepository;
import com.example.Start_up_crm_client_service.security.CustomUserDetails;
import com.example.Start_up_crm_client_service.util.MessageConstant;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final ClientHrRepository clientHrRepository;

    /**
     * Loads a user's details based on their unique identifier, such as email, username, or mobile number.
     * Searches in the database using the repository methods and throws an exception if not found.
     *
     * @param identifier the identifier of the user (email, username, or mobile number)
     * @return UserDetails object encapsulating user's information
     * @throws UsernameNotFoundException if no user is found with the specified identifier
     */
    @Override
    public UserDetails loadUserByUsername(String identifier)
            throws UsernameNotFoundException {

        // 1️⃣ Normal users
        Optional<User> user = userRepository.findByEmail(identifier)
                .or(() -> userRepository.findByUsername(identifier))
                .or(() -> userRepository.findByMobileNo(identifier));

        if (user.isPresent()) {
            return new CustomUserDetails(user.get());
        }

        // 2️⃣ HR users
        Optional<ClientHr> hr = clientHrRepository.findByEmail(identifier);

        if (hr.isPresent()) {
            return new CustomUserDetails(hr.get());
        }

        throw new UsernameNotFoundException(
                MessageConstant.USER_NOT_FOUND_WITH_IDENTIFIER + identifier
        );
    }
}