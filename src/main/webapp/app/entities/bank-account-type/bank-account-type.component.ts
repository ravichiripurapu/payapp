import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BankAccountType } from './bank-account-type.model';
import { BankAccountTypeService } from './bank-account-type.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-bank-account-type',
    templateUrl: './bank-account-type.component.html'
})
export class BankAccountTypeComponent implements OnInit, OnDestroy {
bankAccountTypes: BankAccountType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private bankAccountTypeService: BankAccountTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.bankAccountTypeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.bankAccountTypes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBankAccountTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: BankAccountType) {
        return item.id;
    }
    registerChangeInBankAccountTypes() {
        this.eventSubscriber = this.eventManager.subscribe('bankAccountTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
