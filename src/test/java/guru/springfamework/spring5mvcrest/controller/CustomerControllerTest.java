package guru.springfamework.spring5mvcrest.controller;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.controller.CustomerController;
import guru.springfamework.service.CustomerService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerControllerTest {

    private static final Long CUSTOMER_ID = 1L;
    private static final String CUSTOMER_FIRST_NAME = "Greg";
    private static final String CUSTOMER_LAST_NAME = "House";
    private static final String CUSTOMER_URL = "URL";

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void listCustomers() throws Exception {
        List<CustomerDTO> customers = new ArrayList<>(Arrays.asList(new CustomerDTO(), new CustomerDTO()));
        Mockito.when(customerService.getAll()).thenReturn(customers);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/")
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customers", Matchers.hasSize(2)));
    }

    @Test
    public void getCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(CUSTOMER_ID);
        customerDTO.setFirstName(CUSTOMER_FIRST_NAME);
        customerDTO.setLastName(CUSTOMER_LAST_NAME);
        customerDTO.setCustomerUrl(CUSTOMER_URL);

        Mockito.when(customerService.getById(ArgumentMatchers.anyLong())).thenReturn(customerDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/" + CUSTOMER_ID)
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.equalTo(CUSTOMER_FIRST_NAME)));
    }
}
