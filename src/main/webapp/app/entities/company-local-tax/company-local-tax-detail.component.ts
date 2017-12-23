import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyLocalTax } from './company-local-tax.model';
import { CompanyLocalTaxService } from './company-local-tax.service';

@Component({
    selector: 'jhi-company-local-tax-detail',
    templateUrl: './company-local-tax-detail.component.html'
})
export class CompanyLocalTaxDetailComponent implements OnInit, OnDestroy {

    companyLocalTax: CompanyLocalTax;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private companyLocalTaxService: CompanyLocalTaxService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCompanyLocalTaxes();
    }

    load(id) {
        this.companyLocalTaxService.find(id).subscribe((companyLocalTax) => {
            this.companyLocalTax = companyLocalTax;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCompanyLocalTaxes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'companyLocalTaxListModification',
            (response) => this.load(this.companyLocalTax.id)
        );
    }
}
