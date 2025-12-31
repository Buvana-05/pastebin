import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

export interface Paste {
  content: string;
  remaining_views?: number;
  expires_at?: string;
}

@Injectable({ providedIn: 'root' })
export class PasteService {

  private apiUrl: string;

  constructor(private http: HttpClient) {
    if (typeof window !== 'undefined' && window.location.hostname === 'localhost') {
      this.apiUrl = 'http://localhost:8080/api/pastes';
    } else {
      this.apiUrl = 'https://pastebin-backend-kwjl.onrender.com/api/pastes';
    }
  }

  createPaste(content: string, ttl?: number, maxViews?: number) {
    return this.http.post<any>(this.apiUrl, {
      content: content,
      ttl_seconds: ttl,
      max_views: maxViews
    });
  }


  getPaste(id: string) {
    return this.http.get<Paste>(`${this.apiUrl}/${id}`);
  }
}