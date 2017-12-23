import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AnnualReports } from './annual-reports.model';
import { AnnualReportsService } from './annual-reports.service';

@Component({
    selector: 'jhi-annual-reports-detail',
    templateUrl: './annual-reports-detail.component.html'
})
export class AnnualReportsDetailComponent implements OnInit, OnDestroy {

    annualReports: AnnualReports;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private annualReportsService: AnnualReportsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAnnualReports();
    }

    load(id) {
        this.annualReportsService.find(id).subscribe((annualReports) => {
            this.annualReports = annualReports;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAnnualReports() {
        this.eventSubscriber = this.eventManager.subscribe(
            'annualReportsListModification',
            (response) => this.load(this.annualReports.id)
        );
    }
}
