import {Question} from '../../datamodel/question';
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';


@Component({
  selector: 'app-question-form',
  templateUrl: './question-form.component.html',
  styleUrls: ['./question-form.component.css']
})
export class QuestionFormComponent implements OnInit {
  question: Question;

  constructor(private router: Router) {}

  ngOnInit() {
    this.question = new Question(0, 'test', 1, ['']);
  }

  validate() {
    this.router.navigate(['questions']);
  }
}
