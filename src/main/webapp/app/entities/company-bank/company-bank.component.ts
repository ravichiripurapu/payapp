import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CompanyBank } from './company-bank.model';
import { CompanyBankService } from './company-bank.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-company-bank',
    templateUrl: './company-bank.component.html'
})
export class CompanyBankComponent implements OnInit, OnDestroy {
companyBanks: CompanyBank[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private companyBankService: CompanyBankService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.companyBankService.query().subscribe(
            (res: ResponseWrapper) => {
                this.companyBanks = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCompanyBanks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CompanyBank) {
        return item.id;
    }
    registerChangeInCompanyBanks() {
        this.eventSubscriber = this.eventManager.subscribe('companyBankListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
