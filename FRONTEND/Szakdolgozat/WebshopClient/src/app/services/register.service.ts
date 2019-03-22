import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import { Http, Response, Headers, RequestOptions } from '@angular/http';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private router:Router,private _http:Http) { }

  public saveAccessToken(registerData)
  {
    let params = new URLSearchParams();
    params.append('username',registerData.username);
    params.append('password',registerData.password);    
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
}
