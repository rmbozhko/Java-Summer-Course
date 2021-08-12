package edu.summer.spring.elibrary.service;

import edu.summer.spring.elibrary.dto.mapper.ReaderMapper;
import edu.summer.spring.elibrary.dto.mapper.UserMapper;
import edu.summer.spring.elibrary.dto.model.ReaderDto;
import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.exception.NotUniqueDataException;
import edu.summer.spring.elibrary.model.Reader;
import edu.summer.spring.elibrary.model.Role;
import edu.summer.spring.elibrary.model.Subscription;
import edu.summer.spring.elibrary.model.User;
import edu.summer.spring.elibrary.repository.ReaderRepository;
import edu.summer.spring.elibrary.repository.SubscriptionRepository;
import edu.summer.spring.elibrary.repository.TransactionalEntityManager;
import edu.summer.spring.elibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ReaderServiceImpl implements ReaderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    @Qualifier("bcryptPasswordEncoder")
    private PasswordEncoder encoder;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private TransactionalEntityManager entityManager;

    @Override
    public ReaderDto addReader(ReaderDto readerDto) throws NotUniqueDataException {
        readerDto.setPassword(encoder.encode(readerDto.getPassword()));
        User user = UserMapper.toUser(readerDto);
        user.setActive(true);
        user.setRole(Role.READER);
        Subscription subscription = new Subscription(user, UUID.randomUUID().toString());
        Reader reader = new Reader(user, subscription);
        try {
            entityManager.saveEntity(userRepository, user);
            entityManager.saveEntity(subscriptionRepository, subscription);
            entityManager.saveEntity(readerRepository, reader);
        } catch (DataIntegrityViolationException e) {
            throw new NotUniqueDataException("Not unique username");
        }
        return ReaderMapper.toReaderDto(reader);
    }

    @Override
    public ReaderDto findByUsername(String username) throws FoundNoInstanceException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new FoundNoInstanceException("No user with specified username was found."));
        return ReaderDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public Map<String, User> getAllReadersData() {
        Map<String, User> readersData = new HashMap<>();
        for (Reader reader : readerRepository.findAll()) {
            readersData.put(reader.getSubscription().getToken(), reader.getUser());
        }
        return readersData;
    }
}
