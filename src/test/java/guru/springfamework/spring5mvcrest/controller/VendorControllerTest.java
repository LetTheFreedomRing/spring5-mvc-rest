package guru.springfamework.spring5mvcrest.controller;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controller.RestResponseEntityExceptionHandler;
import guru.springfamework.controller.VendorController;
import guru.springfamework.service.ResourceNotFoundException;
import guru.springfamework.service.VendorService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static guru.springfamework.service.VendorServiceImpl.VENDOR_URL_HEADER;
import static guru.springfamework.spring5mvcrest.controller.AbstractRestControllerTest.asJsonString;

public class VendorControllerTest {

    private static final Long VENDOR_ID = 1L;
    private static final String VENDOR_NAME = "Vendor";

    @Mock
    private VendorService vendorService;

    @InjectMocks
    private VendorController vendorController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vendorController).setControllerAdvice(RestResponseEntityExceptionHandler.class).build();
    }

    @Test
    public void listVendors() throws Exception {

        List<VendorDTO> vendors = Arrays.asList(new VendorDTO(), new VendorDTO());

        Mockito.when(vendorService.listAll()).thenReturn(vendors);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vendors/")
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.vendors", Matchers.hasSize(vendors.size())));
    }

    @Test
    public void getVendor() throws Exception {

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(VENDOR_ID);
        vendorDTO.setName(VENDOR_NAME);
        vendorDTO.setVendorUrl(VENDOR_URL_HEADER + VENDOR_ID);

        Mockito.when(vendorService.getById(ArgumentMatchers.anyLong())).thenReturn(vendorDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vendors/" + VENDOR_ID + "/")
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo(VENDOR_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vendor_url", Matchers.equalTo(VENDOR_URL_HEADER + VENDOR_ID)));
    }

    @Test
    public void createVendor() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(VENDOR_ID);
        vendorDTO.setName(VENDOR_NAME);

        VendorDTO savedVendorDTO = new VendorDTO();
        savedVendorDTO.setId(VENDOR_ID);
        savedVendorDTO.setName(VENDOR_NAME);
        savedVendorDTO.setVendorUrl(VENDOR_URL_HEADER + VENDOR_ID);

        Mockito.when(vendorService.create(ArgumentMatchers.any(VendorDTO.class))).thenReturn(savedVendorDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/vendors/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo(VENDOR_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vendor_url", Matchers.equalTo(VENDOR_URL_HEADER + VENDOR_ID)));

        Mockito.verify(vendorService, Mockito.times(1)).create(ArgumentMatchers.any());
    }

    @Test
    public void deleteVendor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/vendors/" + VENDOR_ID + "/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(vendorService, Mockito.times(1)).deleteById(ArgumentMatchers.any());
    }

    @Test
    public void deleteVendorNotFound() throws Exception {
        Mockito.doAnswer(invocationOnMock -> {
            throw new ResourceNotFoundException();
        }).when(vendorService).deleteById(ArgumentMatchers.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/vendors/" + VENDOR_ID + "/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        Mockito.verify(vendorService, Mockito.times(1)).deleteById(ArgumentMatchers.any());
    }

    @Test
    public void updateVendor() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(VENDOR_NAME);

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setId(VENDOR_ID);
        returnDTO.setName(VENDOR_NAME);
        returnDTO.setVendorUrl(VENDOR_URL_HEADER + VENDOR_ID);

        Mockito.when(vendorService.saveByDTO(ArgumentMatchers.anyLong(), ArgumentMatchers.any(VendorDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/vendors/" + VENDOR_ID + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo(VENDOR_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vendor_url", Matchers.equalTo(VENDOR_URL_HEADER + VENDOR_ID)));
    }

    @Test
    public void patchVendor() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("new name");

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setId(VENDOR_ID);
        returnDTO.setName(vendorDTO.getName());
        returnDTO.setVendorUrl(VENDOR_URL_HEADER + VENDOR_ID);

        Mockito.when(vendorService.patch(ArgumentMatchers.anyLong(), ArgumentMatchers.any(VendorDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/vendors/" + VENDOR_ID + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo(vendorDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vendor_url", Matchers.equalTo(VENDOR_URL_HEADER + VENDOR_ID)));
    }

    @Test
    public void patchVendorThrowsException() throws Exception {
        Mockito.when(vendorService.patch(ArgumentMatchers.anyLong(), ArgumentMatchers.any(VendorDTO.class))).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/customers/" + VENDOR_ID + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new VendorDTO())))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
