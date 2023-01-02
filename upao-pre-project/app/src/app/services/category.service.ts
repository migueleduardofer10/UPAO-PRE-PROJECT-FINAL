import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Category } from '../models/category';

const base_url = environment.base_url;

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  constructor(private http: HttpClient) {}

  getCategories() {
    const endpoint = `${base_url}/categories`;
    return this.http.get<Category[]>(endpoint);
  }

  saveCategorie(body: Category) {
    const endpoint = `${base_url}/categories`;
    return this.http.post<Category>(endpoint, body);
  }

  updateCategorie(body: Category, id: number) {
    const endpoint = `${base_url}/categories/ ${id}`;
    return this.http.put<Category>(endpoint, body);
  }

  deleteCategorie(id: number) {
    const endpoint = `${base_url}/categories/ ${id}`;
    return this.http.delete<Category>(endpoint);
  }

  getCategorieById(id: number) {
    const endpoint = `${base_url}/categories/ ${id}`;
    return this.http.get<Category>(endpoint);
  }

  exportCategories() {
    const endpoint = `${base_url}/categories/export/excel`;
    return this.http.get(endpoint, {
      responseType: 'blob',
    });
  }
}
