package com.demo.apidemo.controller;

import com.demo.apidemo.exception.ResourceNotFoundException;
import com.demo.apidemo.exception.ValidationException;
import com.demo.apidemo.model.Category;
import com.demo.apidemo.model.Product;
import com.demo.apidemo.repository.CategoryRepository;
import com.demo.apidemo.repository.ProductRepository;
import com.demo.apidemo.util.ProductExcelExporter;
import com.demo.apidemo.util.Util;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    public ProductController(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    @Transactional (readOnly = true)
    public ResponseEntity<List<Product>> search() {
        List<Product> products=new ArrayList<>();
        List<Product> productsAux=new ArrayList<>();
        productsAux=productRepository.findAll();

        if(productsAux.size()>0){
            productsAux.forEach(p -> {
                byte[] imageDescompressed = Util.decompressZLib(p.getPicture());
                p.setPicture(imageDescompressed);
                products.add(p);
            });
        }

        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    @Transactional (readOnly = true)
    public ResponseEntity<Product> searchById(@PathVariable Long id){
        Product product=productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Not found product with id="+id));

        if(product!=null){
            byte[] imageDescompressed = Util.decompressZLib(product.getPicture());
            product.setPicture(imageDescompressed);
        }
        return new ResponseEntity<Product>(product,HttpStatus.OK);
    }


    @PostMapping("/products")
    @Transactional
    public ResponseEntity<Product> save(@RequestParam("picture") MultipartFile picture,
                                        @RequestParam("name") String name,
                                        @RequestParam("price") int price,
                                        @RequestParam("account") int account,
                                        @RequestParam("categoryId") Long categoryID)throws IOException {

        Product product = new Product();
        product.setName(name);
        product.setAccount(account);
        product.setPrice(price);
        product.setPicture(Util.compressZLib(picture.getBytes()));

        //TODO: búsqueda de categoría para establecer en el objeto del producto
         Category category = categoryRepository.findById(categoryID)
                .orElseThrow(()-> new ResourceNotFoundException("Not found category with id="+categoryID));

        if( category!=null) {
            product.setCategory(category);
        }

        existsProductByName(product);
        validateProduct(product);
        Product productSaved=productRepository.save(product);

        return new ResponseEntity<Product>(productSaved,HttpStatus.CREATED);
    }

    @PutMapping("/products/{id}")
    @Transactional
    public ResponseEntity<Product> update(@RequestParam("picture") MultipartFile picture,
                                        @RequestParam("name") String name,
                                        @RequestParam("price") int price,
                                        @RequestParam("account") int account,
                                        @RequestParam("categoryId") Long categoryID, @PathVariable Long id
                                    )throws IOException {

        Product product = new Product();
        product.setName(name);
        product.setAccount(account);
        product.setPrice(price);
        product.setPicture(Util.compressZLib(picture.getBytes()));

        //TODO: búsqueda de categoría para establecer en el objeto del producto
        Category category = categoryRepository.findById(categoryID)
                .orElseThrow(()-> new ResourceNotFoundException("Not found category with id="+categoryID));


        if( category!=null) {
            product.setCategory(category);
        }

        //TODO: búsqueda de producto para establecer los nuevos datos
        Product productSearch = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Not found product with id="+id));

       // if( productSearch!=null) {
            productSearch.setName(product.getName());
            productSearch.setPrice(product.getPrice());
            productSearch.setAccount(product.getAccount());
            productSearch.setCategory(product.getCategory());
            productSearch.setPicture(Util.compressZLib(picture.getBytes()));
            existsProductByName(productSearch);
            validateProduct(productSearch);

        //}
        Product productUpdate=productRepository.save(productSearch);
        return new ResponseEntity<Product>(productUpdate,HttpStatus.CREATED);
    }




    @GetMapping("/products/filter/{name}")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Product>> searchByName(@PathVariable String name){
        List<Product> products=new ArrayList<>();
        List<Product> productsAux=new ArrayList<>();

        productsAux=productRepository.findByNameContainingIgnoreCase(name);

        if(productsAux.size()>0){
            productsAux.forEach((p)->{
                byte[] imageDescompressed = Util.decompressZLib(p.getPicture());
                p.setPicture(imageDescompressed);
                products.add(p);
            });
        }

        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @GetMapping("/products/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=result_product";
        response.setHeader(headerKey, headerValue);

        List<Product> products = productRepository.findAll();

        ProductExcelExporter excelExporter = new ProductExcelExporter(
                products);

        excelExporter.export(response);
    }

    //Validación
    private void validateProduct(Product product) {
        if(product.getName()==null || product.getName().trim().isEmpty()){
            throw new ValidationException("El nombre del producto es obligatorio");
        }
        if(product.getName().length()>60){
            throw new ValidationException("El nombre del producto no debe exceder los 60 caracteres");
        }
        if(product.getPrice()<=0){
            throw new ValidationException("El precio del producto no debe ser negativo o 00 (CERO)");
        }

        if(product.getAccount()<=0){
            throw new ValidationException("La cantidad del producto no debe ser negativo o 00 (CERO)");
        }

    }

    //No se debe permitir el registro de un producto con el mismo nombre
    private void existsProductByName(Product product){
        if(productRepository.existsByName(product.getName())){
            throw new ValidationException("El nombre del producto ya existe");
        }
    }


}
