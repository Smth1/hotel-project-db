package com.roma.db.service;

import com.roma.db.model.HotelClient;
import com.roma.db.model.Room;
import com.roma.db.repository.HotelClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collections;
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

    public UserDetails currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // getUsername() - Returns the username used to authenticate the user.
        System.out.println("User name: " + userDetails.getUsername());

        // getAuthorities() - Returns the authorities granted to the user.
        System.out.println("User has authorities: " + userDetails.getAuthorities());

        return userDetails;
    }

    public Page<HotelClient> findPaginated(PageRequest pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<HotelClient> list;
        List<HotelClient> hotelClients = this.hotelClientRepository.findAll();

        if (hotelClients.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, hotelClients.size());
            list = hotelClients.subList(startItem, toIndex);
        }

        Page<HotelClient> clientPage
                = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), hotelClients.size());

        return clientPage;
    }

    public void deleteClient(int id) {
        Optional<HotelClient> byId = hotelClientRepository.findById(id);
        byId.ifPresent(hotelClientRepository::delete);
    }
}
