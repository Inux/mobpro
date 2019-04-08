import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-list',
  templateUrl: 'list.page.html',
  styleUrls: ['list.page.scss']
})
export class ListPage implements OnInit {
  private selectedItem: any;
  private movieList = [
    'Matrix',
    'The Island',
    'Lord of the Rings',
    'The Mask'
  ];
  private idList = [
    'tt0133093',
    'tt0399201',
    'tt0120737',
    'tt0110475'
  ];
  public movies: Array<{ title: string, id: string }> = [];
  constructor() {
    for (let i = 0; i < 4; i++) {
      this.movies.push({
        title: this.movieList[i],
        id: this.idList[i]
      });
    }
  }

  ngOnInit() {
  }
  // add back when alpha.4 is out
  // navigate(item) {
  //   this.router.navigate(['/list', JSON.stringify(item)]);
  // }

}
