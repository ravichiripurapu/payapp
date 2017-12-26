import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FilingStatus } from './filing-status.model';
import { FilingStatusService } from './filing-status.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-filing-status',
    templateUrl: './filing-status.component.html'
})
export class FilingStatusComponent implements OnInit, OnDestroy {
filingStatuses: FilingStatus[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private filingStatusService: FilingStatusService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.filingStatusService.query().subscribe(
            (res: ResponseWrapper) => {
                this.filingStatuses = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFilingStatuses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: FilingStatus) {
        return item.id;
    }
    registerChangeInFilingStatuses() {
        this.eventSubscriber = this.eventManager.subscribe('filingStatusListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
