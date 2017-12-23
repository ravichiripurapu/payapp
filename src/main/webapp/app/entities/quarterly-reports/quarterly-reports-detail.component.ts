import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { QuarterlyReports } from './quarterly-reports.model';
import { QuarterlyReportsService } from './quarterly-reports.service';

@Component({
    selector: 'jhi-quarterly-reports-detail',
    templateUrl: './quarterly-reports-detail.component.html'
})
export class QuarterlyReportsDetailComponent implements OnInit, OnDestroy {

    quarterlyReports: QuarterlyReports;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private quarterlyReportsService: QuarterlyReportsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInQuarterlyReports();
    }

    load(id) {
        this.quarterlyReportsService.find(id).subscribe((quarterlyReports) => {
            this.quarterlyReports = quarterlyReports;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInQuarterlyReports() {
        this.eventSubscriber = this.eventManager.subscribe(
            'quarterlyReportsListModification',
            (response) => this.load(this.quarterlyReports.id)
        );
    }
}
