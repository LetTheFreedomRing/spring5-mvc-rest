package guru.springfamework.service;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryDTO> getAll() {
        List<CategoryDTO> res = new ArrayList<>();
        categoryRepository.findAll().forEach(category -> res.add(categoryMapper.categoryToCategoryDTO(category)));
        return res;
    }

    @Override
    public CategoryDTO getByName(String name) {
        try {
            Category foundCategory = categoryRepository.findByName(name);
            return categoryMapper.categoryToCategoryDTO(foundCategory);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Customer " + name + " not found");
        }
    }
}
