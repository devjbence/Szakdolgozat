import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {NgbModule,NgbCarouselModule} from '@ng-bootstrap/ng-bootstrap';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { HttpModule } from '@angular/http';
import { CookieService } from 'ngx-cookie-service';

import { AppComponent } from './app.component';
import { RegisterComponent } from './components/register/register.component';
import { ShopRouterModule } from './routing.module';
import { ProductsComponent } from './components/products/products.component';
import { LoginComponent } from './components/login/login.component';
import { IndexComponent } from './components/index/index.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';
import { ProductComponent } from './components/product/product.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    ProductsComponent,
    LoginComponent,
    IndexComponent,
    NavbarComponent,
    FooterComponent,
    ProductComponent
  ],
  imports: [
    BrowserModule,
    ShopRouterModule,
    FormsModule,
    HttpModule,
    ReactiveFormsModule,
    NgbModule,
    NgbCarouselModule
  ],
  providers: [CookieService],
  bootstrap: [AppComponent]
})
export class AppModule { }