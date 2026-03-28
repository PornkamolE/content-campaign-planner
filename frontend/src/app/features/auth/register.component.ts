import { CommonModule } from "@angular/common";
import { Component } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Router, RouterLink } from "@angular/router";
import { AuthService } from "../../core/services/auth.service";

@Component({
  selector: "app-register",
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: "./register.component.html",
  styleUrls: ["./register.component.scss"],
})
export class RegisterComponent {
  fullName = "";
  email = "";
  password = "";
  confirmPassword = "";
  location = "";
  jobTitle = "";
  organizationName = "";
  planName = "Free";
  twoFactorEnabled = false;

  avatarFile: File | null = null;
  avatarFileName = "";
  avatarPreviewUrl = "";

  errorMsg = "";
  isSubmitting = false;
  showPassword = false;
  showConfirmPassword = false;
  year = new Date().getFullYear();

  constructor(
    private router: Router,
    private authService: AuthService
  ) { }

  onAvatarSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0] || null;

    this.avatarFile = file;
    this.avatarFileName = "";
    this.avatarPreviewUrl = "";

    if (!file) {
      return;
    }

    if (!file.type.startsWith("image/")) {
      this.errorMsg = "Please select an image file";
      this.avatarFile = null;
      return;
    }

    this.avatarFileName = file.name;
    this.avatarPreviewUrl = URL.createObjectURL(file);
  }

  submit() {
    this.errorMsg = "";

    if (!this.fullName || !this.email || !this.password || !this.confirmPassword) {
      this.errorMsg = "Please complete all required fields";
      return;
    }

    if (this.password !== this.confirmPassword) {
      this.errorMsg = "Passwords do not match";
      return;
    }

    if (this.password.length < 8) {
      this.errorMsg = "Password must be at least 8 characters";
      return;
    }

    this.isSubmitting = true;

    const formData = new FormData();
    formData.append("fullName", this.fullName);
    formData.append("email", this.email);
    formData.append("password", this.password);
    formData.append("location", this.location);
    formData.append("jobTitle", this.jobTitle);
    formData.append("organizationName", this.organizationName);
    formData.append("planName", this.planName);
    formData.append("twoFactorEnabled", String(this.twoFactorEnabled));

    if (this.avatarFile) {
      formData.append("avatarFile", this.avatarFile);
    }

    this.authService.register(formData).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.router.navigateByUrl("/me");
      },
      error: (err) => {
        this.isSubmitting = false;
        this.errorMsg =
          err?.error?.message || "Register failed. Please try again.";
      },
    });
  }

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  toggleConfirmPassword() {
    this.showConfirmPassword = !this.showConfirmPassword;
  }
}