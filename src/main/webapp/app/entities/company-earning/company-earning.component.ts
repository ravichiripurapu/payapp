import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CompanyEarning } from './company-earning.model';
import { CompanyEarningService } from './company-earning.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-company-earning',
    templateUrl: './company-earning.component.html'
})
export class CompanyEarningComponent implements OnInit, OnDestroy {
companyEarnings: CompanyEarning[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private companyEarningService: CompanyEarningService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.companyEarningService.query().subscribe(
            (res: ResponseWrapper) => {
                this.companyEarnings = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCompanyEarnings();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CompanyEarning) {
        return item.id;
    }
    registerChangeInCompanyEarnings() {
        this.eventSubscriber = this.eventManager.subscribe('companyEarningListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
