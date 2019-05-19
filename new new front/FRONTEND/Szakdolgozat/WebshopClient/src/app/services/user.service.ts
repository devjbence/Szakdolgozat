import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private _cookie:CookieService) {
   }

   public checkToken():boolean
   {
     return this._cookie.check('access_token');
   }

   public logOut()
   {
      //TODO: token kill backendben
     this._cookie.delete('access_token');
   }

   public getToken():string
   {
    return this._cookie.get('access_token');
   }
}
