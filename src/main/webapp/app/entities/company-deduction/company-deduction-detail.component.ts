import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyDeduction } from './company-deduction.model';
import { CompanyDeductionService } from './company-deduction.service';

@Component({
    selector: 'jhi-company-deduction-detail',
    templateUrl: './company-deduction-detail.component.html'
})
export class CompanyDeductionDetailComponent implements OnInit, OnDestroy {

    companyDeduction: CompanyDeduction;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private companyDeductionService: CompanyDeductionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCompanyDeductions();
    }

    load(id) {
        this.companyDeductionService.find(id).subscribe((companyDeduction) => {
            this.companyDeduction = companyDeduction;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCompanyDeductions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'companyDeductionListModification',
            (response) => this.load(this.companyDeduction.id)
        );
    }
}
