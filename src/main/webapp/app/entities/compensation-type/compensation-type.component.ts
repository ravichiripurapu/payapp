import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CompensationType } from './compensation-type.model';
import { CompensationTypeService } from './compensation-type.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-compensation-type',
    templateUrl: './compensation-type.component.html'
})
export class CompensationTypeComponent implements OnInit, OnDestroy {
compensationTypes: CompensationType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private compensationTypeService: CompensationTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.compensationTypeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.compensationTypes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCompensationTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CompensationType) {
        return item.id;
    }
    registerChangeInCompensationTypes() {
        this.eventSubscriber = this.eventManager.subscribe('compensationTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
