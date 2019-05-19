import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute,Router } from '@angular/router';
import { ProductCategoryInterface } from 'src/app/enums/ProductCategoryInterface';
import { ProductTypeInterface } from 'src/app/enums/productTypeInterface';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  type=0;

  products:any;
  errMsg:string;
  searchForm: FormGroup;
  productName:string;

  constructor(
    private _service:ProductService,
    private route:ActivatedRoute,private router:Router) {}

    categories:ProductCategoryInterface[];
    types:ProductTypeInterface[];

  ngOnInit() {

    this.searchForm = new FormGroup({
      type: new FormControl(0, [Validators.required]),
      categories: new FormControl(this.categories),
      price:new FormControl()
    });

    //this.data = this.route.snapshot.paramMap.get("username");
    this._service.getProducts().subscribe(
      data => {
        this.products = data;
        this.products = JSON.parse(this.products._body);
      },
      error => this.errMsg = error);

      this.categories = [];

    this._service.getCategories().subscribe(
      data => {
          let parsedData:any = data;
          parsedData = JSON.parse(parsedData._body);

          parsedData.forEach(cat => {
            let category = { value:cat.id, viewValue:cat.name };
            this.categories.push(category);
          });
      },
      error => console.error(error));//this.handleError(error));

      this.types =[
        { value:0, viewValue:"Fixed Price"},
        { value:1, viewValue:"Bidding"}
      ];

  }

  public onSubmit()
  {

  }
}
