import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyBank } from './company-bank.model';
import { CompanyBankService } from './company-bank.service';

@Component({
    selector: 'jhi-company-bank-detail',
    templateUrl: './company-bank-detail.component.html'
})
export class CompanyBankDetailComponent implements OnInit, OnDestroy {

    companyBank: CompanyBank;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private companyBankService: CompanyBankService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCompanyBanks();
    }

    load(id) {
        this.companyBankService.find(id).subscribe((companyBank) => {
            this.companyBank = companyBank;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCompanyBanks() {
        this.eventSubscriber = this.eventManager.subscribe(
            'companyBankListModification',
            (response) => this.load(this.companyBank.id)
        );
    }
}
