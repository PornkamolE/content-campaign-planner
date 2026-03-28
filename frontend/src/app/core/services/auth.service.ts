import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, tap } from "rxjs";
import { environment } from "../../../environments/environment";

interface LoginRequest {
  email: string;
  password: string;
}

interface AuthResponse {
  success: boolean;
  message: string;
  data: {
    accessToken: string;
    tokenType: string;
  };
}

interface MeResponse {
  success: boolean;
  message: string;
  data: {
    id: number;
    fullName: string | null;
    email: string;
    role: string;
    avatarUrl: string | null;
    location: string | null;
    jobTitle: string | null;
    organizationName: string | null;
    planName: string | null;
    twoFactorEnabled: boolean;
  };
}

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private readonly TOKEN_KEY = "access_token";

  constructor(private http: HttpClient) {}

  login(payload: LoginRequest): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${environment.apiBaseUrl}/auth/login`, payload)
      .pipe(
        tap((res) => {
          this.setSession(res);
        })
      );
  }

  register(payload: FormData): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${environment.apiBaseUrl}/auth/register`, payload)
      .pipe(
        tap((res) => {
          this.setSession(res);
        })
      );
  }

  getMe(): Observable<MeResponse> {
    return this.http.get<MeResponse>(`${environment.apiBaseUrl}/auth/me`);
  }

  setSession(res: AuthResponse): void {
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

    const payload = JSON.parse(atob(token.split(".")[1]));
    return {
      email: payload.sub,
      roles: payload.roles,
    };
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }
}