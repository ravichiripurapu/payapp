import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DeductionSubType } from './deduction-sub-type.model';
import { DeductionSubTypeService } from './deduction-sub-type.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-deduction-sub-type',
    templateUrl: './deduction-sub-type.component.html'
})
export class DeductionSubTypeComponent implements OnInit, OnDestroy {
deductionSubTypes: DeductionSubType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private deductionSubTypeService: DeductionSubTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.deductionSubTypeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.deductionSubTypes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDeductionSubTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DeductionSubType) {
        return item.id;
    }
    registerChangeInDeductionSubTypes() {
        this.eventSubscriber = this.eventManager.subscribe('deductionSubTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
