import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  data:string;

  constructor(private _service:ProductService,private router:ActivatedRoute) { }

  ngOnInit() {
    this.data = this.router.snapshot.paramMap.get("username");
  }

}
