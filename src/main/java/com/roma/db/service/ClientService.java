package com.roma.db.service;

import com.roma.db.model.HotelClient;
import com.roma.db.repository.HotelClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements UserDetailsService {
    private final HotelClientRepository hotelClientRepository;

    @Autowired
    public ClientService(HotelClientRepository hotelClientRepository) {
        this.hotelClientRepository = hotelClientRepository;
    }

    public HotelClient getClientById(Integer id) {
        Optional<HotelClient> client = hotelClientRepository.findById(id);

        return client.orElse(null);
    }

    public HotelClient getClientByLogin(String login) {
        Optional<HotelClient> client = hotelClientRepository.findByLogin(login);

        return client.orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HotelClient clientByLogin = getClientByLogin(username);

        System.out.println("clientByLogin = " + clientByLogin);

        if (clientByLogin != null) {
            return clientByLogin;
        } else {
            throw new UsernameNotFoundException(MessageFormat.format("User with username {0} cannot be found.", username));
        }
    }

    public List<HotelClient> getAllClients() {
        return hotelClientRepository.findAll();
    }
}
