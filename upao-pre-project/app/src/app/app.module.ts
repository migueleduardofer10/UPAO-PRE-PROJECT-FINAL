import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NewProductComponent } from './components/product/new-product/new-product.component';
import { AngularMaterialModule } from './components/shared/angular-material/angular-material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NewCategoryComponent } from './components/category/new-category/new-category.component';
import { HomeComponent } from './components/shared/home/home.component';
import { CategoryComponent } from './components/category/category/category.component';
import { ProductComponent } from './components/product/product/product.component';
import { NavbarComponent } from './components/shared/navbar/navbar.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { RouterModule } from '@angular/router';


@NgModule({
  declarations: [
    AppComponent,
    NewProductComponent,
    NewCategoryComponent,
    HomeComponent,
    CategoryComponent,
    ProductComponent,
    NavbarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    AngularMaterialModule,
    ReactiveFormsModule,
    FormsModule, //Two Way Binding,
    HttpClientModule,
    FlexLayoutModule,
    RouterModule
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
