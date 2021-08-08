package edu.summer.spring.elibrary.service;

import edu.summer.spring.elibrary.dto.mapper.ReaderMapper;
import edu.summer.spring.elibrary.dto.model.ReaderDto;
import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.exception.NotUniqueDataException;
import edu.summer.spring.elibrary.model.Reader;
import edu.summer.spring.elibrary.model.Role;
import edu.summer.spring.elibrary.model.Subscription;
import edu.summer.spring.elibrary.model.User;
import edu.summer.spring.elibrary.repository.ReaderRepository;
import edu.summer.spring.elibrary.repository.SubscriptionRepository;
import edu.summer.spring.elibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
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

    @Override
    public ReaderDto addReader(ReaderDto readerDto) throws NotUniqueDataException {
        if (userRepository.findByUsername(readerDto.getUsername()).isPresent()) {
            throw new NotUniqueDataException("Not unique username", readerDto.getUsername());
        }
        User user = new User(readerDto.getUsername(), encoder.encode(readerDto.getPassword()),
                            readerDto.getFirstName(), readerDto.getLastName(),
                            readerDto.getEmail());
        user.setActive(true);
        user.setRole(Role.READER);
        userRepository.save(user);
        Subscription subscription = new Subscription(user, UUID.randomUUID().toString());
        Reader reader = new Reader(user, subscription);
        subscriptionRepository.save(subscription);
        readerRepository.save(reader);
        return ReaderMapper.toReaderDto(reader);
    }

    @Override
    public ReaderDto findByUsername(String username) throws FoundNoInstanceException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new FoundNoInstanceException("No user with specified username was found."));
        return new ReaderDto().setUsername(user.getUsername())
                            .setPassword(user.getPassword())
                            .setFirstName(user.getFirstName())
                            .setLastName(user.getLastName())
                            .setEmail(user.getEmail());
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
