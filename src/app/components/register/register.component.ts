import { NONE_TYPE } from '@angular/compiler';
import { Component, ViewEncapsulation } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export class RegisterComponent {
  constructor(){}
  ngOnInit(): void{ 

  }
  registerform=new FormGroup({   
    firstname: new FormControl(""),
    lastname: new FormControl(""),
    email: new FormControl(""),
    mobile: new FormControl(""),
    gender: new FormControl(""),
    pwd: new FormControl(""),
    rpwd: new FormControl(""),
  });

  registerSubmited(){
    console.log(this.registerform.value);
  }

}
