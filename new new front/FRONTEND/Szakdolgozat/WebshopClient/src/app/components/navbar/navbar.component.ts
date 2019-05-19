import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private _userService:UserService,private router:Router) { }

  ngOnInit() {
  }

  public logOut()
  {
    this._userService.logOut();
    this.router.navigate(['/index']);
  }

}
