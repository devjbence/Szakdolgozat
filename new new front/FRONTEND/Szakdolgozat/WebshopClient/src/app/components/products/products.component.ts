import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute,Router } from '@angular/router';
import { ProductCategoryInterface } from 'src/app/enums/ProductCategoryInterface';
import { ProductTypeInterface } from 'src/app/enums/productTypeInterface';
import { FilterOperationInterface } from 'src/app/enums/FilterOperationInterface';
import { ColorInterface } from 'src/app/enums/ColorInterface';
import { GenericSelectList } from 'src/app/enums/GenericSelectList';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  type=0;
  color=0;
  heightOperation=0;
  widthOperation=0;
  weightOperation=0;

  products:any;
  errMsg:string;
  searchForm: FormGroup;
  productName:string;
  attrValue:string;
  heightValue:number;
  widthValue:number;
  weightValue:number;

  constructor(
    private _service:ProductService,
    private route:ActivatedRoute,private router:Router) {}

    categories:ProductCategoryInterface[];
    types:ProductTypeInterface[];
    colors:GenericSelectList[];
    operations:GenericSelectList[];
    heightOperations:GenericSelectList[];
    widthOperations:GenericSelectList[];
    weightOperations:GenericSelectList[];

  ngOnInit() {

    this.operations = [
      {value:0,viewValue:"<"},
      {value:1,viewValue:">"},
      {value:2,viewValue:"="},
      {value:3,viewValue:"<="},
      {value:4,viewValue:">="}
    ];

    this.colors = [
      {value:0,viewValue:"No color filter"},
      {value:1,viewValue:"Red"},
      {value:2,viewValue:"Green"},
      {value:3,viewValue:"Blue"},
      {value:4,viewValue:"Black"},
      {value:5,viewValue:"White"},
      {value:6,viewValue:"Yellow"}
    ];

    this.heightOperations = this.operations;
    this.widthOperations = this.operations;
    this.weightOperations = this.operations;

    this.searchForm = new FormGroup({
      type: new FormControl(0, [Validators.required]),
      categories: new FormControl(this.categories),
      price:new FormControl(),
      heightOperation:new FormControl(this.heightOperation),
      widthOperation:new FormControl(this.widthOperation),
      weightOperation: new FormControl(this.weightOperation),
      colors: new FormControl(this.colors),
      color: new FormControl(this.color),
      productName: new FormControl(this.productName),
      heightValue: new FormControl(this.heightValue),
      widthValue: new FormControl(this.widthValue),
      weightValue: new FormControl(this.weightValue)
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
