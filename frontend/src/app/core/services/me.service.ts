import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';

type ApiResponse<T> = {
  success: boolean;
  message: string;
  data: T;
};

export type MeResponse = {
  email: string;
  roles: string[];
};

@Injectable({ providedIn: 'root' })
export class MeService {
  constructor(private http: HttpClient) {}

  me() {
    return this.http.get<ApiResponse<MeResponse>>(`${environment.apiBaseUrl}/api/me`);
  }
}
