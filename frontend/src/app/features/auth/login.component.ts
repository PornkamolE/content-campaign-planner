import { CommonModule } from "@angular/common";
import { Component } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Router } from "@angular/router";
import { AuthService } from "../../core/services/auth.service";

@Component({
  selector: "app-login",
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: "./login.component.html",
  styleUrl: "./login.component.scss",
})
export class LoginComponent {
  email = "";
  password = "";
  errorMsg = "";
  isSubmitting = false;
  showPassword = false;
  year = new Date().getFullYear();

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  submit() {
    this.errorMsg = "";

    if (!this.email || !this.password) {
      this.errorMsg = "Please enter email and password";
      return;
    }

    this.isSubmitting = true;

    this.authService.login({
      email: this.email,
      password: this.password,
    }).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.router.navigateByUrl("/me");
      },
      error: (err) => {
        this.isSubmitting = false;
        this.errorMsg =
          err?.error?.message || "Login failed. Please try again.";
      },
    });
  }

  togglePassword() {
    this.showPassword = !this.showPassword;
  }
}