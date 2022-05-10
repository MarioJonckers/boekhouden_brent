package be.vermolen.boekhouden.service;

import be.vermolen.boekhouden.exception.ClientNotFoundException;
import be.vermolen.boekhouden.exception.UpdateException;
import be.vermolen.boekhouden.model.City;
import be.vermolen.boekhouden.model.Client;
import be.vermolen.boekhouden.model.Salutation;
import be.vermolen.boekhouden.model.dto.CreateClientDto;
import be.vermolen.boekhouden.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static be.vermolen.boekhouden.util.MyStringUtil.trim;

@Service
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final LocationService locationService;

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public Client get(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            throw new ClientNotFoundException(id);
        }
        return client;
    }

    public Client update(CreateClientDto client) {
        Client original = get(client.getId());

        return updateAndSaveClient(original, client);
    }

    public Client create(CreateClientDto client) {
        return updateAndSaveClient(new Client(), client);
    }

    private Client updateAndSaveClient(Client original, CreateClientDto client) {
        String salutation = client.getSalutation();
        try {
            original.setSalutation(Salutation.valueOf(salutation));
        } catch (IllegalArgumentException ex) {
            throw new UpdateException("klant", "Aanspreking '" + salutation + "' bestaat niet.");
        } catch (NullPointerException ex) {
            original.setSalutation(null);
        }

        original.setName(trim(client.getName(), true));
        original.setAddress(trim(client.getAddress(), true));
        original.setAddress2(trim(client.getAddress2(), true));

        City city = client.getCity();
        if (city.getId() == null || city.getId() == -1L) {
            original.setCity(locationService.createCity(city));
        } else {
            original.setCity(locationService.getById(city.getId()));
        }

        original.setTel(trim(client.getTel(), false));
        original.setMobile(trim(client.getMobile(), false));
        original.setEmail(trim(client.getEmail(), false));
        original.setBtwNumber(trim(client.getBtwNumber(), false));

        return clientRepository.save(original);
    }
}
