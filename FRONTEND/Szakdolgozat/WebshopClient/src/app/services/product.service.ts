import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { UserService } from './user.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  readonly base_url: string = "http://localhost:8080/szak/product";

  constructor(
    private _userService: UserService,
    private _http: Http,
    private router: Router) { }

  public getProducts() {
    return this._http.get(this.base_url + "/all");
  }

  public getProduct(Id:number) {
    return this._http.get(this.base_url + "/"+Id);
  }

  public getCategories() {
    return this._http.get(this.base_url + "/categories");
  }

  addProduct(productData: any): any {

    if (!this._userService.checkToken()) {
      this.router.navigate(['/login']);
    }

    let token = this._userService.getToken();

    let headers = {
      headers: new Headers({
        Authorization: "Bearer " + token
      })
    };

    return this._http.post(this.base_url, productData, headers);
  }

  updateProduct(productData: any, Id:number): any {
    if (!this._userService.checkToken()) {
      this.router.navigate(['/login']);
    }

    let token = this._userService.getToken();

    let headers = {
      headers: new Headers({
        Authorization: "Bearer " + token
      })
    };

    return this._http.put(this.base_url+"/"+Id, productData, headers);
  }
}
