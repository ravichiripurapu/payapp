import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DeductionType } from './deduction-type.model';
import { DeductionTypeService } from './deduction-type.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-deduction-type',
    templateUrl: './deduction-type.component.html'
})
export class DeductionTypeComponent implements OnInit, OnDestroy {
deductionTypes: DeductionType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private deductionTypeService: DeductionTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.deductionTypeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.deductionTypes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDeductionTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DeductionType) {
        return item.id;
    }
    registerChangeInDeductionTypes() {
        this.eventSubscriber = this.eventManager.subscribe('deductionTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
