import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayrollEmployerTaxes } from './payroll-employer-taxes.model';
import { PayrollEmployerTaxesService } from './payroll-employer-taxes.service';

@Component({
    selector: 'jhi-payroll-employer-taxes-detail',
    templateUrl: './payroll-employer-taxes-detail.component.html'
})
export class PayrollEmployerTaxesDetailComponent implements OnInit, OnDestroy {

    payrollEmployerTaxes: PayrollEmployerTaxes;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private payrollEmployerTaxesService: PayrollEmployerTaxesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPayrollEmployerTaxes();
    }

    load(id) {
        this.payrollEmployerTaxesService.find(id).subscribe((payrollEmployerTaxes) => {
            this.payrollEmployerTaxes = payrollEmployerTaxes;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPayrollEmployerTaxes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'payrollEmployerTaxesListModification',
            (response) => this.load(this.payrollEmployerTaxes.id)
        );
    }
}
