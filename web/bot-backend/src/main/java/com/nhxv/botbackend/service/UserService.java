package com.nhxv.botbackend.service;

import com.nhxv.botbackend.dto.LocalUser;
import com.nhxv.botbackend.dto.SignUpRequest;
import com.nhxv.botbackend.exception.UserAlreadyExistAuthenticationException;
import com.nhxv.botbackend.model.User;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.util.Map;
import java.util.Optional;

/**
 * @author Chinna
 * @since 26/3/18
 */
public interface UserService {

	User registerNewUser(SignUpRequest signUpRequest, String image) throws UserAlreadyExistAuthenticationException;

	User findUserByEmail(String email);

	Optional<User> findUserById(Long id);

	User updateGuilds();

	LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);
}
