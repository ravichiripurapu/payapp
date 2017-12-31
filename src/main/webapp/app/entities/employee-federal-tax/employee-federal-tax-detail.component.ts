import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeeFederalTax } from './employee-federal-tax.model';
import { EmployeeFederalTaxService } from './employee-federal-tax.service';

@Component({
    selector: 'jhi-employee-federal-tax-detail',
    templateUrl: './employee-federal-tax-detail.component.html'
})
export class EmployeeFederalTaxDetailComponent implements OnInit, OnDestroy {

    employeeTaxDeduction: EmployeeFederalTax;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private employeeFederalTaxService: EmployeeFederalTaxService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEmployeeFederalTaxes();
    }

    load(id) {
        this.employeeFederalTaxService.find(id).subscribe((employeeTaxDeduction) => {
            this.employeeTaxDeduction = employeeTaxDeduction;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmployeeFederalTaxes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'employeeFederalTaxListModification',
            (response) => this.load(this.employeeTaxDeduction.id)
        );
    }
}
