import { Injectable } from '@angular/core';
import {Router} from '@angular/router';
import { Http, Response, Headers, RequestOptions } from '@angular/http';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  public saveAccessToken(registerForm)
  {
    let params = new URLSearchParams();
    params.append('username',registerForm.controls['username'].value);
    params.append('password',registerForm.controls['password'].value);    
    params.append('grant_type','password');

    let headers = new Headers({'Content-type': 'application/x-www-form-urlencoded; charset=utf-8',
      'Authorization': 'Basic '+btoa("myclientapp:9999")});
    let options = new RequestOptions({ headers: headers });
     
    this._http.post('http://localhost:8080/szak/oauth/token', 
      params.toString(), options)
      .subscribe(
        data => this.saveToken(data),
        err => alert('Invalid Credentials')); 
  }

  public saveToken(data:any)
  {
    console.log(data);
  }

  constructor(private router:Router,private _http:Http) { }
}
