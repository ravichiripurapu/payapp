import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { W2Reports } from './w-2-reports.model';
import { W2ReportsService } from './w-2-reports.service';

@Component({
    selector: 'jhi-w-2-reports-detail',
    templateUrl: './w-2-reports-detail.component.html'
})
export class W2ReportsDetailComponent implements OnInit, OnDestroy {

    w2Reports: W2Reports;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private w2ReportsService: W2ReportsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInW2Reports();
    }

    load(id) {
        this.w2ReportsService.find(id).subscribe((w2Reports) => {
            this.w2Reports = w2Reports;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInW2Reports() {
        this.eventSubscriber = this.eventManager.subscribe(
            'w2ReportsListModification',
            (response) => this.load(this.w2Reports.id)
        );
    }
}
