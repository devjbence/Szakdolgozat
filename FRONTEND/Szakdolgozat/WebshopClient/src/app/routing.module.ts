import {NgModule} from '@angular/core';
import {Routes,RouterModule} from '@angular/router';
import { RegisterComponent } from './components/register/register.component';
import { ProductsComponent } from './components/products/products.component';

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
    }
];

@NgModule({
    imports:[RouterModule.forRoot(routes)],
    exports:[RouterModule]
})
export class  ShopRouterModule{}