import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, ValidationErrors, ValidatorFn } from '@angular/forms';
import { LoginService } from 'src/app/services/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private _service: LoginService,private router:Router) { }

  loginForm: FormGroup;
  loginData:any;
  errorMsg:string;

  ngOnInit() {
    this.loginForm = new FormGroup({
      username: new FormControl("",[Validators.required, Validators.minLength(3)]),
      password: new FormControl("",[Validators.required, Validators.minLength(8)]),
      confirmPassword: new FormControl("",[Validators.required, Validators.minLength(8)])
    });

    this.loginForm.setValidators(this.passwordConfirmationValidator());
  }

  public onSubmit()
  {
    if(this.loginForm.valid)
    {
      this.loginData = {
        username: this.loginForm.controls['username'].value,
        password: this.loginForm.controls['password'].value
      };

       this._service.login(this.loginData).subscribe(
        data => {
          this._service.saveToken(data);
          this.router.navigate(['/index']);
        },
        error => this.handleError(error));
    }
  }

  handleError(error)
  {
    ///const errorObject = JSON.parse(error._body);
    this.errorMsg = 'Username or password is wrong';
  }

  public passwordConfirmationValidator(): ValidatorFn {
    return (group: FormGroup): ValidationErrors => {
      
      const pass = group.controls['password'];
      const cpass = group.controls['confirmPassword'];

      if (pass.value !== cpass.value) {
        cpass.setErrors({ notEquivalent: true });
      } 
      else {
        cpass.setErrors(null);
      }
      return;
    };
  }

}
