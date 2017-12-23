import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CompanyStateTax } from './company-state-tax.model';
import { CompanyStateTaxService } from './company-state-tax.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-company-state-tax',
    templateUrl: './company-state-tax.component.html'
})
export class CompanyStateTaxComponent implements OnInit, OnDestroy {
companyStateTaxes: CompanyStateTax[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private companyStateTaxService: CompanyStateTaxService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.companyStateTaxService.query().subscribe(
            (res: ResponseWrapper) => {
                this.companyStateTaxes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCompanyStateTaxes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CompanyStateTax) {
        return item.id;
    }
    registerChangeInCompanyStateTaxes() {
        this.eventSubscriber = this.eventManager.subscribe('companyStateTaxListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
