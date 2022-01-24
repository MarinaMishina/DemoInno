package org.inno.shop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.inno.shop.dto.CustomerDto;
import org.inno.shop.dto.Utils;
import org.inno.shop.entity.Customer;
import org.inno.shop.repository.CustomerRepository;
import org.inno.shop.service.CustomerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



public class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    Utils utils;

    Customer customer;

    String customerStrId = "i30gfYTLJ3xESow8E5udFcjWI08Mkk";

    @BeforeEach
    void setUp() {


        MockitoAnnotations.openMocks(this);

        customer = new Customer();
        customer.setId(1L);
        customer.setName("Marina");
        customer.setPhone("+79991234321");
        customer.setEmail("test1@test.com");
        customer.setCustomerStrId(customerStrId);
        customer.setEncryptedpassword("encryptedPassword");
    }

    @Test
    final void testGetCustomer() {

        when(customerRepository.findByEmail(anyString())).thenReturn(customer);
        CustomerDto customerDto = customerService.getCustomerByEmail("test1@test.com");

        assertNotNull(customerDto);
        assertEquals("Marina", customerDto.getName());
    }

    @Test
    final void testCreateCustomer()
    {
        when(customerRepository.findByEmail(anyString())).thenReturn(null);

        when(utils.generateCustomerStrId(anyInt())).thenReturn(customerStrId);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("Marina");
        customerDto.setPhone("+766626262");
        customerDto.setPassword("pass");
        customerDto.setEmail("test1@test.com");

        CustomerDto storedCustomer = customerService.createCustomer(customerDto);
        assertNotNull(storedCustomer);
        assertEquals(customer.getName(), storedCustomer.getName());
        assertNotNull(storedCustomer.getCustomerStrId());
        verify(customerRepository,times(1)).save(any(Customer.class));
    }
}
