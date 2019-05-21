import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ProductService } from 'src/app/services/product.service';
import { ProductTypeInterface } from 'src/app/enums/productTypeInterface';
import { ProductCategoryInterface } from 'src/app/enums/ProductCategoryInterface';
import { NgbCarouselConfig } from '@ng-bootstrap/ng-bootstrap';
import { UserService } from 'src/app/services/user.service';
import { GenericSelectList } from 'src/app/enums/GenericSelectList';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
  providers: [NgbCarouselConfig]
})
export class ProductComponent implements OnInit {

  type = 0;
  color = 0;

  constructor(private router: Router,
    private _service: ProductService,
    private route: ActivatedRoute,
    private _userService: UserService,
    config: NgbCarouselConfig) {
    config.interval = 5000;
    config.wrap = true;
    config.keyboard = false;
  }

  Id: number;
  attributes: any[];
  productForm: FormGroup;
  productData: any;
  colorAttributeData: any;
  heightAttributeData: any;
  widthAttributeData:any;
  weightAttributeData: any;
  productType: any;
  errorMsg: string;
  existingProduct: any;
  showPrice: boolean;
  colors: GenericSelectList[];
  images;// = [1, 2, 3].map(() => `https://picsum.photos/900/500?random&t=${Math.random()}`);
  height:number;
  width:number;
  weight:number;
  isOwn:boolean;
  isActive:boolean;

  types: ProductTypeInterface[];
  categories: ProductCategoryInterface[];

  ngOnInit() {
    this.Id = parseInt(this.route.snapshot.paramMap.get("id"));
    this.showPrice = true;
    this.colors = [
      { value: 0, viewValue: "No color filter" },
      { value: 1, viewValue: "Red" },
      { value: 2, viewValue: "Green" },
      { value: 3, viewValue: "Blue" },
      { value: 4, viewValue: "Black" },
      { value: 5, viewValue: "White" },
      { value: 6, viewValue: "Yellow" }
    ];

    this.images = [
      "http://localhost:8080/image/1", "http://localhost:8080/image/2", "http://localhost:8080/image/3"
    ];

    this.attributes = [];

    if (this.Id > 0) {
      this._service.getProduct(this.Id).subscribe(
        data => {
          let parsedData: any = data;
          parsedData = JSON.parse(parsedData._body);
          let attributes = parsedData.attributes;

          for (let i = 0; i < attributes.length; i++) {
            this._service.getAttribute(attributes[i]).subscribe(
              data => {
                let parsedData: any = data;
                parsedData = JSON.parse(parsedData._body);
                this.attributes.push(parsedData);

                if (parsedData.attributeCore == 1)//color
                {
                  this.productForm.controls['color'].setValue(0);
                  for (let j = 0; j < this.colors.length; j++) {
                    let parsedColor = parsedData.value.charAt(0).toUpperCase() + parsedData.value.slice(1);
                    if (parsedColor == this.colors[j].viewValue) {
                      this.productForm.controls['color'].setValue(j);
                    }
                  }
                }

                if (parsedData.attributeCore == 2)//height
                {
                  this.height=parseInt(parsedData.value);
                  this.productForm.controls['height'].setValue(this.height);
                }

                if (parsedData.attributeCore == 3)//width
                {
                  this.width=parseInt(parsedData.value);
                  this.productForm.controls['width'].setValue(this.width);
                }

                if (parsedData.attributeCore == 4)//weigth
                {
                  this.weight=parseInt(parsedData.value);
                  this.productForm.controls['weight'].setValue(this.weight);
                }
              },
              error => this.handleError(error));
          }

          this.existingProduct = parsedData;
          this.isOwn=parsedData.isOwn;
          this.isActive=parsedData.active;

          this.productForm.controls['name'].setValue(this.existingProduct.name);
          this.productForm.controls['description'].setValue(this.existingProduct.description);
          this.productForm.controls['type'].setValue(this.existingProduct.type == "Bidding" ? 1 : 0);
          this.productForm.controls['endDate'].setValue(this.existingProduct.end);
          this.productForm.controls['price'].setValue(this.existingProduct.price);
          this.productForm.controls['categories'].setValue(this.existingProduct.categories);

          if (this.existingProduct.type == 'Bidding') {
            this.showPrice = false;
          }
        },
        () => this.router.navigate(['/index']));
    }

    this.types = [
      { value: 0, viewValue: "Fixed Price" },
      { value: 1, viewValue: "Bidding" }
    ];

    this.categories = [];

    this._service.getCategories().subscribe(
      data => {
        let parsedData: any = data;
        parsedData = JSON.parse(parsedData._body);

        parsedData.forEach(cat => {
          let category = { value: cat.id, viewValue: cat.name };
          this.categories.push(category);
        });
      },
      error => this.handleError(error));

    this.productForm = new FormGroup({
      name: new FormControl("", [Validators.required, Validators.minLength(3)]),
      description: new FormControl("", [Validators.required, Validators.minLength(3)]),
      type: new FormControl(0, [Validators.required]),
      categories: new FormControl(this.categories),
      endDate: new FormControl("", [Validators.required]),
      price: new FormControl(),
      color: new FormControl(this.color),
      height: new FormControl(this.height),
      width: new FormControl(this.width),
      weight: new FormControl(this.weight)
    });
  }

  public buy()
  {
    this._service.buy(this.Id).subscribe(
      data => {},
      error => this.handleError(error));

      this.router.navigate(['/products']);
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

      this.color = this.productForm.controls['color'].value;
      this.height = this.productForm.controls['height'].value;
      this.width = this.productForm.controls['width'].value;
      this.weight = this.productForm.controls['weight'].value;

      if (this.Id == 0) {
        this._service.addProduct(this.productData).subscribe(
          data => {
            let productId = JSON.parse(data._body).id;

            this.colorAttributeData = {
              attributeCore: 1,
              product: parseInt(productId),
              value: this.color == null || this.color == 0 ? "" : this.colors[this.color].viewValue.toLowerCase()
            };

            this.heightAttributeData = {
              attributeCore: 2,
              product: parseInt(productId),
              value: this.height == null ? 0 : this.height
            };

            this.widthAttributeData = {
              attributeCore: 3,
              product: parseInt(productId),
              value: this.width == null ? 0 : this.width
            };

            this.weightAttributeData = {
              attributeCore: 4,
              product: parseInt(productId),
              value: this.weight == null ? 0 : this.weight
            };

            this._service.addAttribute(this.colorAttributeData).subscribe(
              data => { },
              error => this.handleError(error));

            this._service.addAttribute(this.heightAttributeData).subscribe(
              data => { },
              error => this.handleError(error));

              this._service.addAttribute(this.widthAttributeData).subscribe(
                data => { },
                error => this.handleError(error));

            this._service.addAttribute(this.weightAttributeData).subscribe(
              data => { },
              error => this.handleError(error));

            this.router.navigate(['/products']);
          },
          error => this.handleError(error));
      }
      else {
        this._service.updateProduct(this.productData, this.Id).subscribe(
          () => {

            this.colorAttributeData = {
              attributeCore:1,
              product: this.Id,
              value: this.color == null || this.color == 0 ? "" : this.colors[this.color].viewValue.toLowerCase()
            };

            this.heightAttributeData = {
              attributeCore:2,
              product: this.Id,
              value: this.height == null ? 0 : this.height
            };

            this.widthAttributeData = {
              attributeCore:3,
              product: this.Id,
              value: this.width == null ? 0 : this.width
            };

            this.weightAttributeData = {
              attributeCore:4,
              product: this.Id,
              value: this.weight == null ? 0 : this.weight
            };

            for (let i = 0; i < this.attributes.length; i++) {
              if (this.attributes[i].attributeCore == 1)//color
              {
                this._service.updateAttribute(this.attributes[i].id, this.colorAttributeData).subscribe(
                  data => { },
                  error => this.handleError(error));
              }
              if (this.attributes[i].attributeCore == 2)//height
              {
                this._service.updateAttribute(this.attributes[i].id, this.heightAttributeData).subscribe(
                  data => { },
                  error => this.handleError(error));
              }
              if (this.attributes[i].attributeCore == 3)//width
              {
                this._service.updateAttribute(this.attributes[i].id, this.widthAttributeData).subscribe(
                  data => { },
                  error => this.handleError(error));
              }
              if (this.attributes[i].attributeCore == 4)//weight
              {
                this._service.updateAttribute(this.attributes[i].id, this.weightAttributeData).subscribe(
                  data => { },
                  error => this.handleError(error));
              }
            }

            this.router.navigate(['/products']);
          },
          error => this.handleError(error));
      }


    }
  }

  handleError(error) {
    console.log(error);
    const errorObject = JSON.parse(error._body);

    if (errorObject.error == "invalid_token") {
      this._userService.logOut();
      this.router.navigate(['/login']);
    }

    this.errorMsg = errorObject.message;
  }
}

