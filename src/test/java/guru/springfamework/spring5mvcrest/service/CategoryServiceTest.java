package guru.springfamework.spring5mvcrest.service;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.service.CategoryService;
import guru.springfamework.service.CategoryServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CategoryServiceTest {

    private static final Long CATEGORY_ID = 1L;
    private static final String CATEGORY_NAME = "category";

    private CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryService categoryService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryServiceImpl(categoryRepository, categoryMapper);
    }

    @Test
    public void getAll() {
        List<Category> categories = new ArrayList<>();
        Category category = new Category();
        category.setName(CATEGORY_NAME);
        category.setId(CATEGORY_ID);
        categories.add(category);

        Mockito.when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryDTO> res = categoryService.getAll();
        assertEquals(categories.size(), res.size());
    }

    @Test
    public void getByName() {
        Category category = new Category();
        category.setId(CATEGORY_ID);
        category.setName(CATEGORY_NAME);

        Mockito.when(categoryRepository.findByName(ArgumentMatchers.anyString())).thenReturn(category);

        CategoryDTO categoryDTO = categoryService.getByName(CATEGORY_NAME);
        assertEquals(category.getId(), categoryDTO.getId());
        assertEquals(category.getName(), categoryDTO.getName());
    }
}
