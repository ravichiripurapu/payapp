import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyStateTax } from './company-state-tax.model';
import { CompanyStateTaxService } from './company-state-tax.service';

@Component({
    selector: 'jhi-company-state-tax-detail',
    templateUrl: './company-state-tax-detail.component.html'
})
export class CompanyStateTaxDetailComponent implements OnInit, OnDestroy {

    companyStateTax: CompanyStateTax;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private companyStateTaxService: CompanyStateTaxService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCompanyStateTaxes();
    }

    load(id) {
        this.companyStateTaxService.find(id).subscribe((companyStateTax) => {
            this.companyStateTax = companyStateTax;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCompanyStateTaxes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'companyStateTaxListModification',
            (response) => this.load(this.companyStateTax.id)
        );
    }
}
