package guru.springfamework.controller;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.CategoryListDTO;
import guru.springfamework.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/categories/")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public ResponseEntity<CategoryListDTO> listCategories() {
        return new ResponseEntity<CategoryListDTO>(new CategoryListDTO(categoryService.getAll()), HttpStatus.OK);
    }

    @GetMapping("{categoryName}")
    public ResponseEntity<CategoryDTO> getByName(@PathVariable(name = "categoryName") String categoryName) {
        return new ResponseEntity<CategoryDTO>(categoryService.getByName(categoryName), HttpStatus.OK);
    }
}
