import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { QuarterlyReports } from './quarterly-reports.model';
import { QuarterlyReportsService } from './quarterly-reports.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-quarterly-reports',
    templateUrl: './quarterly-reports.component.html'
})
export class QuarterlyReportsComponent implements OnInit, OnDestroy {
quarterlyReports: QuarterlyReports[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private quarterlyReportsService: QuarterlyReportsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.quarterlyReportsService.query().subscribe(
            (res: ResponseWrapper) => {
                this.quarterlyReports = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInQuarterlyReports();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: QuarterlyReports) {
        return item.id;
    }
    registerChangeInQuarterlyReports() {
        this.eventSubscriber = this.eventManager.subscribe('quarterlyReportsListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
