import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CompanyCompensation } from './company-compensation.model';
import { CompanyCompensationService } from './company-compensation.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-company-compensation',
    templateUrl: './company-compensation.component.html'
})
export class CompanyCompensationComponent implements OnInit, OnDestroy {
companyCompensations: CompanyCompensation[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private companyCompensationService: CompanyCompensationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.companyCompensationService.query().subscribe(
            (res: ResponseWrapper) => {
                this.companyCompensations = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCompanyCompensations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CompanyCompensation) {
        return item.id;
    }
    registerChangeInCompanyCompensations() {
        this.eventSubscriber = this.eventManager.subscribe('companyCompensationListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
