import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DepositType } from './deposit-type.model';
import { DepositTypeService } from './deposit-type.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-deposit-type',
    templateUrl: './deposit-type.component.html'
})
export class DepositTypeComponent implements OnInit, OnDestroy {
depositTypes: DepositType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private depositTypeService: DepositTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.depositTypeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.depositTypes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDepositTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DepositType) {
        return item.id;
    }
    registerChangeInDepositTypes() {
        this.eventSubscriber = this.eventManager.subscribe('depositTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
