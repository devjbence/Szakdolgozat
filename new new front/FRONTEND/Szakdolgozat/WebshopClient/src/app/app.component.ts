import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'WebshopClient';

  constructor(private router:Router){
  }

  ngOnInit()
  {
    let url = window.location.href;

    if(url == 'http://localhost:4200/' || url == 'http://localhost:4200')
    {
      this.router.navigate(['/index']);
    }
  }
  
}
