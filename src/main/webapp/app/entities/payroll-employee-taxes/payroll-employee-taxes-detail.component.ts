import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayrollEmployeeTaxes } from './payroll-employee-taxes.model';
import { PayrollEmployeeTaxesService } from './payroll-employee-taxes.service';

@Component({
    selector: 'jhi-payroll-employee-taxes-detail',
    templateUrl: './payroll-employee-taxes-detail.component.html'
})
export class PayrollEmployeeTaxesDetailComponent implements OnInit, OnDestroy {

    payrollEmployeeTaxes: PayrollEmployeeTaxes;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private payrollEmployeeTaxesService: PayrollEmployeeTaxesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPayrollEmployeeTaxes();
    }

    load(id) {
        this.payrollEmployeeTaxesService.find(id).subscribe((payrollEmployeeTaxes) => {
            this.payrollEmployeeTaxes = payrollEmployeeTaxes;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPayrollEmployeeTaxes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'payrollEmployeeTaxesListModification',
            (response) => this.load(this.payrollEmployeeTaxes.id)
        );
    }
}
