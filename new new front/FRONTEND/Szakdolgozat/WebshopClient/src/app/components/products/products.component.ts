import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute,Router } from '@angular/router';
import { ProductCategoryInterface } from 'src/app/enums/ProductCategoryInterface';
import { ProductTypeInterface } from 'src/app/enums/productTypeInterface';
import { FilterOperationInterface } from 'src/app/enums/FilterOperationInterface';
import { ColorInterface } from 'src/app/enums/ColorInterface';
import { GenericSelectList } from 'src/app/enums/GenericSelectList';
import { BooleanSelectList } from 'src/app/enums/BooleanSelectList';

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
  priceOperation=0;
  own=1;
  active=0;

  products:any;
  errMsg:string;
  searchForm: FormGroup;
  productName:string;
  attrValue:string;
  heightValue:number;
  widthValue:number;
  weightValue:number;
  numberOfProducts:number;
  price:number;

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
    priceOperations:GenericSelectList[];
    owns:BooleanSelectList[];
    actives:BooleanSelectList[];

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

    this.owns=[
      {value:0,viewValue:true},
      {value:1,viewValue:false},
    ];

    this.actives=[
      {value:0,viewValue:true},
      {value:1,viewValue:false},
    ];

    this.heightOperations = this.operations;
    this.widthOperations = this.operations;
    this.weightOperations = this.operations;
    this.priceOperations = this.operations;

    this.searchForm = new FormGroup({
      type: new FormControl(0, [Validators.required]),
      categories: new FormControl(this.categories),
      price:new FormControl(this.price),
      heightOperation:new FormControl(this.heightOperation),
      widthOperation:new FormControl(this.widthOperation),
      weightOperation: new FormControl(this.weightOperation),
      priceOperation: new FormControl(this.priceOperation),
      owns: new FormControl(this.owns),
      own: new FormControl(this.own),
      active:new FormControl(this.active),
      actives:new FormControl(this.actives),
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
        this.numberOfProducts=this.products.length;
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
        { value:0, viewValue:"Any"},
        { value:1, viewValue:"Fixed Price"},
        { value:2, viewValue:"Bidding"}
      ];

  }

  public onSubmit()
  {
    let searchModel = {};
    
    let productName=this.searchForm.controls['productName'].value;
    let categories = this.searchForm.controls['categories'].value;
    let own=this.searchForm.controls['own'].value; //???????????
    let active=this.searchForm.controls['active'].value; //???????????
    let type = this.searchForm.controls['type'].value;
    let price = this.searchForm.controls['price'].value;
    let priceOperation = this.searchForm.controls['priceOperation'].value;
    let heightOperation = this.searchForm.controls['heightOperation'].value;
    let widthOperation = this.searchForm.controls['widthOperation'].value;
    let weightOperation = this.searchForm.controls['weightOperation'].value;
    let color = this.searchForm.controls['color'].value;
    let heightValue = this.searchForm.controls['heightValue'].value;
    let widthValue = this.searchForm.controls['widthValue'].value;
    let weightValue = this.searchForm.controls['weightValue'].value;

    if(productName)
    {
      searchModel['productName']= productName;
    }

    if(categories && categories.length != 0)
    {
      searchModel['categories']= categories;
    }

    searchModel['own']= own != 1;

    searchModel['isActive']= active != 1;

    if(type && type != 0)
    {
      searchModel['productType']= parseInt(type) - 1 < 0 ? 0 : parseInt(type) - 1;
      searchModel['price']= price;
      searchModel['operation']= parseInt(priceOperation);
    }

    //filter cors
    let productFilterCores = [];

    if(color != 0)
    {
      let productFilterCore = {
        attributeCore : 1,
        attributeOperation:2,
        value:this.colors[color].viewValue.toLowerCase()
      };

      productFilterCores.push(productFilterCore);
    }

    if(heightValue != null)
    {
      let productFilterCore = {
        attributeCore : 2,
        attributeOperation:parseInt(heightOperation),
        value:heightValue
      };

      productFilterCores.push(productFilterCore);
    }

    if(widthValue != null)
    {
      let productFilterCore = {
        attributeCore : 3,
        attributeOperation:parseInt(widthOperation),
        value:widthValue
      };

      productFilterCores.push(productFilterCore);
    }

    if(weightValue != null)
    {
      let productFilterCore = {
        attributeCore : 4,
        attributeOperation:parseInt(weightOperation),
        value:weightValue
      };

      productFilterCores.push(productFilterCore);
    }


    if(productFilterCores.length != 0)
    {
      searchModel['productFilterCores']= productFilterCores;
    }

    console.log(searchModel);
    this._service.getFilteredProducts(searchModel).subscribe(
      data => {
        this.products = data;
        this.products = JSON.parse(this.products._body);
        this.numberOfProducts=this.products.length;
      },
      error => console.error(error));
  }
}
