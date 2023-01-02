import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Category } from 'src/app/models/category';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-new-category',
  templateUrl: './new-category.component.html',
  styleUrls: ['./new-category.component.css'],
})
export class NewCategoryComponent implements OnInit {
  myForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.reactiveForm();
  }

  reactiveForm() {
    this.myForm = this.fb.group({
      id: [''],
      name: ['', [Validators.required, Validators.maxLength(30)]],
      description: ['', [Validators.required, Validators.maxLength(255)]],
    });
  }

  saveCategory(): void {
    const category: Category = {
      id: 0,
      name: this.myForm.get('name')!.value,
      description: this.myForm.get('description')!.value,
    };
    this.categoryService.saveCategorie(category).subscribe({
      next: (data) => {
        this.snackBar.open('La categoría fue registrado con exito!', '', {
          duration: 3000,
        });
        this.router.navigate(['/admin/category']);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
}
