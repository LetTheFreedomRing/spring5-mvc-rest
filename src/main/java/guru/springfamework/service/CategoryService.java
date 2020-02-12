package guru.springfamework.service;

import guru.springfamework.api.v1.model.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAll();

    CategoryDTO getByName(String name);
}
