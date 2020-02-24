package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        Customer greg = new Customer();
        greg.setFirstName("Greg");
        greg.setLastName("House");

        Customer tom = new Customer();
        tom.setFirstName("Tom");
        tom.setLastName("Brown");

        Vendor vendor1 = new Vendor();
        vendor1.setName("Vendor1");

        Vendor vendor2 = new Vendor();
        vendor2.setName("Vendor2");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        customerRepository.save(greg);
        customerRepository.save(tom);

        vendorRepository.save(vendor1);
        vendorRepository.save(vendor2);

        System.out.println("Data loaded");
        System.out.println("Categories : " + categoryRepository.count());
        System.out.println("Customers : " + customerRepository.count());
        System.out.println("Vendors : " + vendorRepository.count());
    }
}
