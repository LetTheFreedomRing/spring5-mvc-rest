package guru.springfamework.service;

import guru.springfamework.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> getAll();

    CustomerDTO getById(Long id);

    CustomerDTO create(CustomerDTO customer);

    CustomerDTO saveByDTO(Long id, CustomerDTO customerDTO);

    CustomerDTO patch(Long id, CustomerDTO customerDTO);

    void deleteById(Long id);
}
