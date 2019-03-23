import {NgModule} from '@angular/core';
import {Routes,RouterModule} from '@angular/router';
import { RegisterComponent } from './components/register/register.component';
import { ProductsComponent } from './components/products/products.component';
import { LoginComponent } from './components/login/login.component';
import { IndexComponent } from './components/index/index.component';

const routes:Routes=[
    {
      path:'',redirectTo:'',pathMatch:'full'
    },
    {
      path:'register',
      component:RegisterComponent
    },
    {
      path:'products/:username',
      component:ProductsComponent
    },
    {
      path:'login',
      component:LoginComponent
    },
    {
      path:'index',
      component:IndexComponent
    }
];

@NgModule({
    imports:[RouterModule.forRoot(routes)],
    exports:[RouterModule]
})
export class  ShopRouterModule{}