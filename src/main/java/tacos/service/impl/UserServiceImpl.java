package tacos.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tacos.dto.UserDto;
import tacos.mapper.UserMapper;
import tacos.repository.UserRepository;
import tacos.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto createUser(UserDto dto) {
        return Optional.of(dto)
                .map(userMapper::toEntity)
                .map(user -> {user.setPassword(passwordEncoder.encode(dto.password()));
                    return user;
                })
                .map(userRepository::save)
                .map(userMapper::toDto)
                .orElseThrow();
    }
}

