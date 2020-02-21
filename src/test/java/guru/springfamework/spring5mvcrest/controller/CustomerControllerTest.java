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

import static guru.springfamework.spring5mvcrest.controller.AbstractRestControllerTest.asJsonString;

public class CustomerControllerTest {

    private static final Long CUSTOMER_ID = 1L;
    private static final String CUSTOMER_FIRST_NAME = "Greg";
    private static final String CUSTOMER_LAST_NAME = "House";
    private static final int API_VERSION = 1;
    private static final String CUSTOMER_URL = "/api/v" + API_VERSION + "/customers/" + CUSTOMER_ID;

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

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v" + API_VERSION + "/customers/")
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

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v" + API_VERSION + "/customers/" + CUSTOMER_ID)
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", Matchers.equalTo(CUSTOMER_FIRST_NAME)));
    }

    @Test
    public void createCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(CUSTOMER_ID);
        customerDTO.setFirstName(CUSTOMER_FIRST_NAME);
        customerDTO.setLastName(CUSTOMER_LAST_NAME);
        customerDTO.setCustomerUrl(CUSTOMER_URL);

        Mockito.when(customerService.create(ArgumentMatchers.any())).thenReturn(customerDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v" + API_VERSION + "/customers/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(customerDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", Matchers.equalTo(CUSTOMER_FIRST_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname", Matchers.equalTo(CUSTOMER_LAST_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer_url", Matchers.equalTo("/api/v" + API_VERSION + "/customers/" + CUSTOMER_ID)));
    }

    @Test
    public void updateCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(CUSTOMER_FIRST_NAME);
        customerDTO.setLastName(CUSTOMER_LAST_NAME);

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setId(CUSTOMER_ID);
        returnDTO.setFirstName(CUSTOMER_FIRST_NAME);
        returnDTO.setLastName(CUSTOMER_LAST_NAME);
        returnDTO.setCustomerUrl(CUSTOMER_URL);

        Mockito.when(customerService.saveByDTO(ArgumentMatchers.anyLong(), ArgumentMatchers.any())).thenReturn(returnDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v" + API_VERSION + "/customers/" + CUSTOMER_ID + "/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(customerDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", Matchers.equalTo(CUSTOMER_FIRST_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname", Matchers.equalTo(CUSTOMER_LAST_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer_url", Matchers.equalTo(CUSTOMER_URL)));
    }
}
