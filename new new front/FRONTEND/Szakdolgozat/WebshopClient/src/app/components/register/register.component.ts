import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { RegisterService } from 'src/app/services/register.service';
import { FormGroup, FormControl, Validators, ValidationErrors, ValidatorFn } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private _service: RegisterService,private router:Router) { }

  registerForm: FormGroup;
  registerData:any;
  errorMsg:string;

  ngOnInit() {
    this.registerForm = new FormGroup({
      username: new FormControl("",[Validators.required, Validators.minLength(3)]),
      email: new FormControl("",[Validators.required, Validators.email]),
      password: new FormControl("",[Validators.required, Validators.minLength(8)]),
      confirmPassword: new FormControl("",[Validators.required, Validators.minLength(8)])
    });

    this.registerForm.setValidators(this.passwordConfirmationValidator());
  }

  public onSubmit()
  {
    if(this.registerForm.valid)
    {
      this.registerData = {
        username: this.registerForm.controls['username'].value,
        password: this.registerForm.controls['password'].value,
        email: this.registerForm.controls['email'].value,
        role: 'ROLE_USER'
      };

       this._service.register(this.registerData).subscribe(
        data => this.router.navigate(['/login']),
        error => this.handleError(error));
    }
  }

  handleError(error)
  {
    const errorObject = JSON.parse(error._body);
    this.errorMsg = errorObject.message;
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
