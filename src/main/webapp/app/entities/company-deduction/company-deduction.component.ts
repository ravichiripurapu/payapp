import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CompanyDeduction } from './company-deduction.model';
import { CompanyDeductionService } from './company-deduction.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-company-deduction',
    templateUrl: './company-deduction.component.html'
})
export class CompanyDeductionComponent implements OnInit, OnDestroy {
companyDeductions: CompanyDeduction[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private companyDeductionService: CompanyDeductionService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.companyDeductionService.query().subscribe(
            (res: ResponseWrapper) => {
                this.companyDeductions = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCompanyDeductions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CompanyDeduction) {
        return item.id;
    }
    registerChangeInCompanyDeductions() {
        this.eventSubscriber = this.eventManager.subscribe('companyDeductionListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
