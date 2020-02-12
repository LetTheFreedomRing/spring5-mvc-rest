package guru.springframework.api.v1.mapper;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryMapperTest {

    private static final Long CATEGORY_ID = 1L;
    private static final String CATEGORY_NAME = "CATEGORY";

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    public void categoryToCategoryDTO() {
        Category category = new Category();
        category.setId(CATEGORY_ID);
        category.setName(CATEGORY_NAME);

        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        assertEquals(category.getId(), categoryDTO.getId());
        assertEquals(category.getName(), categoryDTO.getName());
    }
}
