import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import {map} from 'rxjs/operators';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  readonly base_url:string = "http://localhost:8080/szak";

  constructor(private router:Router,private _http:Http) { 
  }

  public register(registerData)
  { 
    return this._http.post(this.base_url+"/user", registerData);
  }

}
