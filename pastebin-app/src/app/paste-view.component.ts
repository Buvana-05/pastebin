import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { PasteService } from './paste.service';
import { FormsModule } from '@angular/forms';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-paste-view',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './paste-view.component.html'
})
export class PasteViewComponent implements OnInit {
  paste: any = null;
  error: string | null = null;
  loading = true;

  constructor(
    private route: ActivatedRoute,
    private pasteService: PasteService,
    private cd: ChangeDetectorRef
  ) { }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id')!;
    console.log('PasteViewComponent: Init with ID:', id);

    this.pasteService.getPaste(id).subscribe({
      next: (data) => {
        console.log('PasteViewComponent: Data received:', data);
        this.paste = data;
        this.loading = false;
        this.cd.detectChanges();
      },
      error: (err) => {
        console.error('PasteViewComponent: Error:', err);
        this.error = err.error?.error || "Paste not found or expired";
        this.loading = false;
        this.cd.detectChanges();
      }
    });
  }
}
