import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MaritalStatus } from './marital-status.model';
import { MaritalStatusService } from './marital-status.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-marital-status',
    templateUrl: './marital-status.component.html'
})
export class MaritalStatusComponent implements OnInit, OnDestroy {
maritalStatuses: MaritalStatus[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private maritalStatusService: MaritalStatusService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.maritalStatusService.query().subscribe(
            (res: ResponseWrapper) => {
                this.maritalStatuses = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMaritalStatuses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MaritalStatus) {
        return item.id;
    }
    registerChangeInMaritalStatuses() {
        this.eventSubscriber = this.eventManager.subscribe('maritalStatusListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
