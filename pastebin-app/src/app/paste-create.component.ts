import { ChangeDetectorRef, Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PasteService } from './paste.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-paste-create',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './paste-create.component.html'
})
export class PasteCreateComponent {

  content: string = '';
  ttl?: number;
  maxViews?: number;


  constructor(private pasteService: PasteService, private router: Router, private cd: ChangeDetectorRef) { }

  loading = false;
  shareUrl: string | null = null;
  error: string | null = null;

  createPaste() {
    if (!this.content || !this.content.trim()) {
      this.error = 'Content is required'; // ✅ show error immediately 
      return;
    }
    if (this.loading) return;
    this.loading = true;
    this.error = null;

    this.pasteService.createPaste(this.content, this.ttl, this.maxViews)
      .subscribe({
        next: res => {
          console.log('Response:', res);
          this.shareUrl = res.url;
          this.loading = false;

          // Clear inputs after success
          this.content = '';
          this.ttl = undefined;
          this.maxViews = undefined;

          this.cd.detectChanges();
        },

        error: err => {
          console.error(err);
          if (err.error && err.error.error) {
            this.error = err.error.error; // Show specific backend error
          } else {
            this.error = 'Failed to create paste';
          }
          this.loading = false;
        }
      });
  }
}
