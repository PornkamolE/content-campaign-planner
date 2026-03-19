import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, tap } from "rxjs";
import { environment } from "../../../environments/environment";

interface LoginRequest {
  email: string;
  password: string;
}

interface LoginResponse {
  success: boolean;
  message: string;
  data: {
    accessToken: string;
    tokenType: string;
  };
}

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private readonly TOKEN_KEY = "access_token";

  constructor(private http: HttpClient) { }

  login(payload: LoginRequest): Observable<LoginResponse> {
    return this.http
      .post<LoginResponse>(`${environment.apiBaseUrl}/auth/login`, payload)
      .pipe(
        tap((res) => {
          this.setSession(res);
        })
      );
  }

  setSession(res: LoginResponse): void {
    localStorage.setItem(this.TOKEN_KEY, res.data.accessToken);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  getStoredUser(): any {
    const token = this.getToken();

    if (!token) {
      return null;
    }

    const payload = JSON.parse(atob(token.split('.')[1]));
    return {
      email: payload.sub,
      roles: payload.roles
    };
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }
}