import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute,Router } from '@angular/router';
import { ProductCategoryInterface } from 'src/app/enums/ProductCategoryInterface';
import { ProductTypeInterface } from 'src/app/enums/productTypeInterface';
import { FilterOperationInterface } from 'src/app/enums/FilterOperationInterface';
import { FilterAttributeNameInterface } from 'src/app/enums/FilterAttributeNameInterface';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  type=0;
  attrName=0;
  operation=0;

  products:any;
  errMsg:string;
  searchForm: FormGroup;
  productName:string;
  attrValue:string;

  constructor(
    private _service:ProductService,
    private route:ActivatedRoute,private router:Router) {}

    categories:ProductCategoryInterface[];
    types:ProductTypeInterface[];
    attrNames:FilterAttributeNameInterface[];
    operations:FilterOperationInterface[];

  ngOnInit() {

    this.operations = [
      {value:0,viewValue:"<"},
      {value:0,viewValue:">"},
      {value:0,viewValue:"="},
      {value:0,viewValue:"<="},
      {value:0,viewValue:">="}
    ];

    this.attrNames = [
      {value:0,viewValue:"Color"},
      {value:0,viewValue:"Height"},
      {value:0,viewValue:"Width"},
      {value:0,viewValue:"Weight"}
    ];

    this.searchForm = new FormGroup({
      type: new FormControl(0, [Validators.required]),
      categories: new FormControl(this.categories),
      price:new FormControl(),
      operation:new FormControl(),
      attrValue: new FormControl(this.attrValue),
      attrNames: new FormControl(this.attrNames),
      attrName: new FormControl(this.attrName),
      productName: new FormControl(this.productName),
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
