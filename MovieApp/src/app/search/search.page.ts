import {MovieService, SearchType} from '../movie.service';
import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';

@Component({
    selector: 'app-search',
    templateUrl: './search.page.html',
    styleUrls: ['./search.page.scss'],
})

export class SearchPage implements OnInit {
    results: Observable<any>;
    searchTerm: string = '';
    searchQuery: string = '';
    type: SearchType = SearchType.all;
    /**
     * Constructor of our first page
     * @param movieService The movie Service to get data
     */
    constructor(private movieService: MovieService) {
    }
    ngOnInit() {
    }
    searchChanged() {
        // Call our service function which returns an Observable
        this.results = this.movieService.searchData(this.searchTerm, this.type);
    }
    doSearch() {
        this.results = this.movieService.searchData(this.searchQuery, this.type);
    }
}
