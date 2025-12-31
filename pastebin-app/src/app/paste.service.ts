import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

export interface Paste {
  content: string;
  remaining_views?: number;
  expires_at?: string;
}

import { environment } from '../environments/environment';

@Injectable({ providedIn: 'root' })
export class PasteService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  createPaste(content: string, ttl?: number, maxViews?: number) {
    return this.http.post<any>('http://localhost:8080/api/pastes', {
      content: content,
      ttl_seconds: ttl,
      max_views: maxViews
    });
  }


  getPaste(id: string) {
    return this.http.get<Paste>(`${this.apiUrl}/${id}`);
  }
}
