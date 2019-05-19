import { Injectable } from '@angular/core';
import {Router} from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Http, Response, Headers, RequestOptions } from '@angular/http';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  readonly base_url:string = "http://localhost:8080";
  
  constructor(private router:Router,private _http:Http, private _cookie:CookieService) { }

  public saveAccessToken(registerForm)
  {
    let params = new URLSearchParams();
    params.append('username',registerForm.controls['username'].value);
    params.append('password',registerForm.controls['password'].value);    
    params.append('grant_type','password');

    let headers = new Headers({'Content-type': 'application/x-www-form-urlencoded; charset=utf-8',
      'Authorization': 'Basic '+btoa("myclientapp:9999")});
    let options = new RequestOptions({ headers: headers });
     
    this._http.post('http://localhost:8080/oauth/token', 
      params.toString(), options)
      .subscribe(
        data => this.saveToken(data),
        err => alert('Invalid Credentials')); 
  }

  public login(loginData)
  { 
    let params = new URLSearchParams();
    params.append('username',loginData.username);
    params.append('password',loginData.password);    
    params.append('grant_type','password');

    let headers = new Headers({'Content-type': 'application/x-www-form-urlencoded; charset=utf-8',
      'Authorization': 'Basic '+btoa("myclientapp:9999")});
    let options = new RequestOptions({ headers: headers });

    return this._http.post(this.base_url+"/oauth/token",params.toString(), options);
  }

  saveToken(token){
    let parsedToken = JSON.parse(token._body);
    var expireDate = new Date().getTime() + (1000*parsedToken.expires_in);
    //this._cookie.set("access_token", parsedToken.access_token, expireDate);
    document.cookie="access_token="+ parsedToken.access_token +"; expires="+ new Date(expireDate).toString();
  }

}
