import { Component, OnInit } from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ProductService } from 'src/app/services/product.service';
import { ProductTypeInterface } from 'src/app/enums/productTypeInterface';
import { ProductCategoryInterface } from 'src/app/enums/ProductCategoryInterface';
import { NgbCarouselConfig } from '@ng-bootstrap/ng-bootstrap';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
  providers:[NgbCarouselConfig]
})
export class ProductComponent implements OnInit {

  type=0;

  constructor(private router: Router,
     private _service: ProductService,
      private route:ActivatedRoute,
      private _userService:UserService,
      config: NgbCarouselConfig) {
    config.interval = 5000;
    config.wrap = true;
    config.keyboard = false;
   }

  Id:number;
  productForm: FormGroup;
  productData: any;
  productType:any;
  errorMsg: string;
  existingProduct:any;
  showPrice:boolean;
  images;// = [1, 2, 3].map(() => `https://picsum.photos/900/500?random&t=${Math.random()}`);

  types:ProductTypeInterface[];
  categories:ProductCategoryInterface[];

  ngOnInit() {
    this.Id = parseInt(this.route.snapshot.paramMap.get("id"));
    this.showPrice=true;

    this.images = [
      "http://localhost:8080/szak/image/1","http://localhost:8080/szak/image/2","http://localhost:8080/szak/image/3"
    ];

    if(this.Id > 0)
    {
      this._service.getProduct(this.Id).subscribe(
        data => {
            let parsedData:any = data;
            parsedData = JSON.parse(parsedData._body);
  
           this.existingProduct = parsedData;
           
           this.productForm.controls['name'].setValue(this.existingProduct.name);
           this.productForm.controls['description'].setValue(this.existingProduct.description);
           this.productForm.controls['type'].setValue(this.existingProduct.type == 'FixedPrice' ? 0: 1);
           this.productForm.controls['endDate'].setValue(this.existingProduct.end);
           this.productForm.controls['price'].setValue(this.existingProduct.price);
           this.productForm.controls['categories'].setValue(this.existingProduct.categories);

           if(this.existingProduct.type == 'Bidding')
           {
             this.showPrice = false;
           }
        },
        () => this.router.navigate(['/index']));
    }

    this.types =[
      { value:0, viewValue:"FixedPrice"},
      { value:1, viewValue:"Bidding"}
    ];

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
      error => this.handleError(error));

    this.productForm = new FormGroup({
      name: new FormControl("", [Validators.required, Validators.minLength(3)]),
      description: new FormControl("", [Validators.required, Validators.minLength(3)]),
      type: new FormControl(0, [Validators.required]),
      categories: new FormControl(this.categories),
      endDate:new FormControl("",[Validators.required]),
      price:new FormControl()
    });
  }

  public onSubmit() {
    if (this.productForm.valid) {

      this.productData = {
        name: this.productForm.controls['name'].value,
        description: this.productForm.controls['description'].value,
        type: this.productForm.controls['type'].value,
        categories: this.productForm.controls['categories'].value,
        end: this.productForm.controls['endDate'].value,
        price: this.productForm.controls['price'].value,
      };
    
      if(this.Id == 0)
      {
        this._service.addProduct(this.productData).subscribe(
          () => {
            this.router.navigate(['/products']);
          },
          error => this.handleError(error));
      }
      else{
        this._service.updateProduct(this.productData,this.Id).subscribe(
          () => {
            this.router.navigate(['/products']);
          },
          error => this.handleError(error));
      }


    }
  }

  handleError(error) {
    console.log(error);
    const errorObject = JSON.parse(error._body);
    
    if(errorObject.error == "invalid_token")
    {
      this._userService.logOut();
      this.router.navigate(['/login']);
    }

    this.errorMsg = errorObject.message;
  }
}

