import { CommonModule } from "@angular/common";
import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { AuthService } from "../../core/services/auth.service";

@Component({
  selector: "app-me",
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="page">
      <div class="card">
        <h2>My Profile</h2>
        <p><strong>Email:</strong> {{ user?.email || "-" }}</p>
        <p><strong>Name:</strong> {{ user?.name || "-" }}</p>
        <button (click)="logout()">Logout</button>
      </div>
    </div>
  `,
  styles: [`
    .page {
      min-height: 100vh;
      display: grid;
      place-items: center;
      background: #f7f7fb;
    }

    .card {
      width: 100%;
      max-width: 420px;
      background: #fff;
      padding: 24px;
      border-radius: 16px;
      box-shadow: 0 10px 30px rgba(17, 24, 39, 0.08);
    }

    button {
      margin-top: 12px;
      height: 40px;
      border: none;
      border-radius: 10px;
      padding: 0 16px;
      background: #111827;
      color: white;
      cursor: pointer;
    }
  `]
})
export class MeComponent {
  user: any = null;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {
    this.user = this.authService.getStoredUser();
  }

  logout() {
    this.authService.logout();
    this.router.navigateByUrl("/login");
  }
}