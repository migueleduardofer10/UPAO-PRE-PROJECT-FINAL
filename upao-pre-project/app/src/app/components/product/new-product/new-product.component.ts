import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/models/category';
import { CategoryService } from 'src/app/services/category.service';
import { ProductService } from 'src/app/services/product.service';
import { Product } from 'src/app/models/product';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-new-product',
  templateUrl: './new-product.component.html',
  styleUrls: ['./new-product.component.css'],
})
export class NewProductComponent implements OnInit {
  public myForm: FormGroup;
  categories: Category[] = [];
  selectedFile: any;
  nameImg: string = '';

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService,
    private productService: ProductService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.reactiveForm();
    this.getCategories();
  }

  reactiveForm() {
    this.myForm = this.fb.group({
      id: [''],
      name: ['', Validators.required],
      price: ['', Validators.required],
      account: ['', Validators.required],
      category: ['', Validators.required],
      picture: ['', Validators.required],
    });
  }

  getCategories() {
    this.categoryService.getCategories().subscribe({
      next:(data) => {
        this.categories = data;
      },
      error:(error) => {
        console.log('error al consultar categorias');
      }
    });
  }



  onFileChanged(event: any) {
    this.selectedFile = event.target.files[0];
    console.log(this.selectedFile);

    this.nameImg = event.target.files[0].name;
  }

  saveProduct() {
    const product: Product = {
      id: 0,
      name: this.myForm.get('name')?.value,
      price: this.myForm.get('price')?.value,
      account: this.myForm.get('account')?.value,
      category: this.myForm.get('category')?.value,
      picture: this.selectedFile,
    };

    const uploadImageData = new FormData();
    uploadImageData.append('picture', product.picture, product.picture.name);
    uploadImageData.append('name', product.name);
    uploadImageData.append('price', product.price.toString());
    uploadImageData.append('account', product.account.toString());
    uploadImageData.append('categoryId', product.category);

    //TODO: llamado a metodo service registro producto
    this.productService.saveProduct(uploadImageData).subscribe({
      next: (data) => {
        this.snackBar.open('La categoría fue registrado con exito!', '', {
          duration: 3000,
        });
        this.router.navigate(['/admin/product']);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
}
