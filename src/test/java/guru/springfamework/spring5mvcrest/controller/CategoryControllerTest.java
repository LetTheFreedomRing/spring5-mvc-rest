package guru.springfamework.spring5mvcrest.controller;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.controller.CategoryController;
import guru.springfamework.service.CategoryService;
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

public class CategoryControllerTest {

    private static final int API_VERSION = 1;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    MockMvc mockMvc;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void listCategories() throws Exception {
        CategoryDTO categoryDTO1 = new CategoryDTO();
        categoryDTO1.setId(1L);
        categoryDTO1.setName("cat1");

        CategoryDTO categoryDTO2 = new CategoryDTO();
        categoryDTO2.setId(2L);
        categoryDTO2.setName("cat2");

        List<CategoryDTO> categories = new ArrayList<>(Arrays.asList(categoryDTO1, categoryDTO2));

        Mockito.when(categoryService.getAll()).thenReturn(categories);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v" + API_VERSION + "/categories/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories", Matchers.hasSize(categories.size())));
    }

    @Test
    public void getByName() throws Exception{
        String categoryName = "cat";

        CategoryDTO categoryDTO1 = new CategoryDTO();
        categoryDTO1.setId(1L);
        categoryDTO1.setName(categoryName);

        Mockito.when(categoryService.getByName(ArgumentMatchers.anyString())).thenReturn(categoryDTO1);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v" + API_VERSION + "/categories/" + categoryName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo(categoryName)));
    }
}
