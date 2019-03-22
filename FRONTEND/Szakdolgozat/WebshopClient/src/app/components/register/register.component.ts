import { Component, OnInit } from '@angular/core';
import { RegisterService } from 'src/app/services/register.service';
import { FormGroup, FormControl, Validators, ValidationErrors, ValidatorFn } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private _service: RegisterService) { }

  registerForm: FormGroup;

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
    //this._service.saveAccessToken(registerData);
    if(!this.registerForm.valid)
    {
      console.log("nemvalid");
    }
    else{
      console.log(this.registerForm.value);
    }
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
