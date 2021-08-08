package edu.summer.spring.elibrary.service;

import edu.summer.spring.elibrary.dto.model.ReaderDto;
import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.model.Reader;
import edu.summer.spring.elibrary.model.User;
import edu.summer.spring.elibrary.repository.ReaderRepository;
import edu.summer.spring.elibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ReaderServiceImpl implements ReaderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Override
    public ReaderDto signup(ReaderDto readerDto) {
        return null;
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
