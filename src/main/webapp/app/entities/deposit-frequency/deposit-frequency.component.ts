import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DepositFrequency } from './deposit-frequency.model';
import { DepositFrequencyService } from './deposit-frequency.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-deposit-frequency',
    templateUrl: './deposit-frequency.component.html'
})
export class DepositFrequencyComponent implements OnInit, OnDestroy {
depositFrequencies: DepositFrequency[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private depositFrequencyService: DepositFrequencyService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.depositFrequencyService.query().subscribe(
            (res: ResponseWrapper) => {
                this.depositFrequencies = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDepositFrequencies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DepositFrequency) {
        return item.id;
    }
    registerChangeInDepositFrequencies() {
        this.eventSubscriber = this.eventManager.subscribe('depositFrequencyListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
