import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CompanyLocalTax } from './company-local-tax.model';
import { CompanyLocalTaxService } from './company-local-tax.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-company-local-tax',
    templateUrl: './company-local-tax.component.html'
})
export class CompanyLocalTaxComponent implements OnInit, OnDestroy {
companyLocalTaxes: CompanyLocalTax[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private companyLocalTaxService: CompanyLocalTaxService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.companyLocalTaxService.query().subscribe(
            (res: ResponseWrapper) => {
                this.companyLocalTaxes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCompanyLocalTaxes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CompanyLocalTax) {
        return item.id;
    }
    registerChangeInCompanyLocalTaxes() {
        this.eventSubscriber = this.eventManager.subscribe('companyLocalTaxListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
