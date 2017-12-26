import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayrollEmployee } from './payroll-employee.model';
import { PayrollEmployeeService } from './payroll-employee.service';

@Component({
    selector: 'jhi-payroll-employee-detail',
    templateUrl: './payroll-employee-detail.component.html'
})
export class PayrollEmployeeDetailComponent implements OnInit, OnDestroy {

    payrollEmployee: PayrollEmployee;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private payrollEmployeeService: PayrollEmployeeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPayrollEmployees();
    }

    load(id) {
        this.payrollEmployeeService.find(id).subscribe((payrollEmployee) => {
            this.payrollEmployee = payrollEmployee;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPayrollEmployees() {
        this.eventSubscriber = this.eventManager.subscribe(
            'payrollEmployeeListModification',
            (response) => this.load(this.payrollEmployee.id)
        );
    }
}
