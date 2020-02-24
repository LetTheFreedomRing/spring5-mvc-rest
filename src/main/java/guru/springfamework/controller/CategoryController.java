package guru.springfamework.controller;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.CategoryListDTO;
import guru.springfamework.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories/")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public CategoryListDTO listCategories() {
        return new CategoryListDTO(categoryService.getAll());
    }

    @GetMapping("{categoryName}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO getByName(@PathVariable(name = "categoryName") String categoryName) {
        return categoryService.getByName(categoryName);
    }
}
