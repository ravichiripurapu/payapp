import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { W2Reports } from './w-2-reports.model';
import { W2ReportsService } from './w-2-reports.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-w-2-reports',
    templateUrl: './w-2-reports.component.html'
})
export class W2ReportsComponent implements OnInit, OnDestroy {
w2Reports: W2Reports[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private w2ReportsService: W2ReportsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.w2ReportsService.query().subscribe(
            (res: ResponseWrapper) => {
                this.w2Reports = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInW2Reports();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: W2Reports) {
        return item.id;
    }
    registerChangeInW2Reports() {
        this.eventSubscriber = this.eventManager.subscribe('w2ReportsListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
