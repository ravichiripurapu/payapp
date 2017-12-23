import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { BankAccountType } from './bank-account-type.model';
import { BankAccountTypeService } from './bank-account-type.service';

@Component({
    selector: 'jhi-bank-account-type-detail',
    templateUrl: './bank-account-type-detail.component.html'
})
export class BankAccountTypeDetailComponent implements OnInit, OnDestroy {

    bankAccountType: BankAccountType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bankAccountTypeService: BankAccountTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBankAccountTypes();
    }

    load(id) {
        this.bankAccountTypeService.find(id).subscribe((bankAccountType) => {
            this.bankAccountType = bankAccountType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBankAccountTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bankAccountTypeListModification',
            (response) => this.load(this.bankAccountType.id)
        );
    }
}
