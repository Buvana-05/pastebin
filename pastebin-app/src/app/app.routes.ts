import { Routes } from '@angular/router';
import { PasteCreateComponent } from './paste-create.component';
import { PasteViewComponent } from './paste-view.component';

export const routes: Routes = [
  { path: '', component: PasteCreateComponent },
  { path: 'p/:id', component: PasteViewComponent }
];