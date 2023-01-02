import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NewProductComponent } from './components/product/new-product/new-product.component';
import { NewCategoryComponent } from './components/category/new-category/new-category.component';
import { ProductComponent } from './components/product/product/product.component';
import { CategoryComponent } from './components/category/category/category.component';
import { HomeComponent } from './components/shared/home/home.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'admin/product/new', component: NewProductComponent },  
  { path: 'admin/product', component: ProductComponent },  
  { path: 'admin/category/new', component: NewCategoryComponent },  
  { path: 'admin/category', component: CategoryComponent },    
  { path: '', redirectTo: 'login ', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],

  exports: [RouterModule],
})
export class AppRoutingModule {}
