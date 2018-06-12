export class Question {
  id: number;
  title: string;
  difficulty: number;
  tags: string[];

  constructor(id: number, title: string, difficulty: number, tags: string[] ) {
    this.difficulty = difficulty;
    this.title = title;
    this.tags = tags;
    this.id = id;

  }
}
