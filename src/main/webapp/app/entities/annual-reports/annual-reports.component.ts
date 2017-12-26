import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AnnualReports } from './annual-reports.model';
import { AnnualReportsService } from './annual-reports.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-annual-reports',
    templateUrl: './annual-reports.component.html'
})
export class AnnualReportsComponent implements OnInit, OnDestroy {
annualReports: AnnualReports[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private annualReportsService: AnnualReportsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.annualReportsService.query().subscribe(
            (res: ResponseWrapper) => {
                this.annualReports = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAnnualReports();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AnnualReports) {
        return item.id;
    }
    registerChangeInAnnualReports() {
        this.eventSubscriber = this.eventManager.subscribe('annualReportsListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
