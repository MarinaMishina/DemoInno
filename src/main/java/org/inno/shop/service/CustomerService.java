package org.inno.shop.service;

import org.inno.shop.dto.CustomerDto;
import org.inno.shop.dto.Utils;
import org.inno.shop.entity.Customer;
import org.inno.shop.exceptions.ResourceNotFoundException;
import org.inno.shop.repository.CustomerRepository;
import org.inno.shop.ui.response.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository repo;

    @Autowired
    Utils util;

    public List<CustomerDto> getAllCustomers() {

        List<CustomerDto> returnDto = new ArrayList<>();
        List<Customer> customers = repo.findAll();
        if (customers.isEmpty()) throw new ResourceNotFoundException(ErrorMessages.NO_RECORDS_FOUND.getErrorMessage());

        for (Customer entity : customers) {
            CustomerDto customerDto = new CustomerDto();
            BeanUtils.copyProperties(entity, customerDto);
            returnDto.add(customerDto);
        }
        return returnDto;
    }

    public CustomerDto getCustomerByEmail(String email) {
        Customer customerEntity = repo.findByEmail(email);
        if (customerEntity == null) {
            throw new ResourceNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        CustomerDto returnDto = new CustomerDto();
        BeanUtils.copyProperties(customerEntity, returnDto);

        return returnDto;
    }

    public CustomerDto getCustomerByStringId(String strId) {
        Customer customerEntity = repo.findByCustomerStrId(strId);
        if (customerEntity == null) {
            throw new ResourceNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        CustomerDto returnDto = new CustomerDto();
        BeanUtils.copyProperties(customerEntity, returnDto);

        return returnDto;
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        if (repo.findByEmail(customerDto.getEmail()) != null)
            throw new ResourceNotFoundException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());

        Customer customerEntity = new Customer();
        BeanUtils.copyProperties(customerDto, customerEntity);

        String publicCustomerStrId = util.generateCustomerStrId(30);
        customerEntity.setCustomerStrId(publicCustomerStrId);

        // TODO : использовать security.crypto.bcrypt.BCryptPasswordEncoder
        customerEntity.setEncryptedpassword("encryptedPassword");

        Customer savedCustomer = repo.save(customerEntity);
        CustomerDto returnDto = new CustomerDto();
        BeanUtils.copyProperties(savedCustomer, returnDto);
        return returnDto;
    }

    public CustomerDto updateCustomer(String strId, CustomerDto customerDto) {

        CustomerDto returnDto = new CustomerDto();
        Customer customerEntity = repo.findByCustomerStrId(strId);
        if (customerEntity == null) {
            throw new ResourceNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        customerEntity.setName(customerDto.getName());
        customerEntity.setPhone(customerDto.getPhone());

        Customer updatedCustomer = repo.save(customerEntity);
        BeanUtils.copyProperties(customerEntity, returnDto);
        return returnDto;
    }

    public void deleteCustomer(String userId) {
        Customer customerEntity = repo.findByCustomerStrId(userId);
        if (customerEntity == null) {
            throw new ResourceNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        repo.delete(customerEntity);
    }
}
