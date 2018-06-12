import {Routes, RouterModule} from '@angular/router';

import {AppComponent} from './app.component';
import { QuestionFormComponent } from './components/question-form/question-form.component';
import { QuestionlistComponent } from './components/questionlist/questionlist.component';

import {Question} from './datamodel/question';

const appRoutes: Routes = [
  {path: 'form', component: QuestionFormComponent},
  {path: '', component: QuestionlistComponent},
  {path: 'questions', component: QuestionlistComponent}


];


export const router = RouterModule.forRoot(appRoutes);
