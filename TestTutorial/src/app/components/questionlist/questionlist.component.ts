import {Question} from '../../datamodel/question';
import {QuestionService} from '../../services/question.service';
import {Component, OnInit} from '@angular/core';
import {RouterModule, Router} from '@angular/router';

@Component({
  selector: 'app-questionlist',
  templateUrl: './questionlist.component.html',
  styleUrls: ['./questionlist.component.css']
})
export class QuestionlistComponent implements OnInit {

  questions: Question[];
  constructor(private questionService: QuestionService, private router: Router) {}

  ngOnInit() {
    //initialize "questions"
    this.questions = this.questionService.getAllQuestions();
  }

  goToQuestion() {
    this.router.navigate(['form']);
  }

}
