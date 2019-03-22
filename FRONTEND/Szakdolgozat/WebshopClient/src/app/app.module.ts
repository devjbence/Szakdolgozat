import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { RegisterComponent } from './components/register/register.component';
import { ShopRouterModule } from './routing.module';
import { ProductsComponent } from './components/products/products.component';
import { RegisterService } from './services/register.service';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    ProductsComponent
  ],
  imports: [
    BrowserModule,
    ShopRouterModule,
    FormsModule,
    HttpModule,
    ReactiveFormsModule
  ],
  providers: [RegisterService],
  bootstrap: [AppComponent]
})
export class AppModule { }