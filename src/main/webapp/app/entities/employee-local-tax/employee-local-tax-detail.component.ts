import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeeLocalTax } from './employee-local-tax.model';
import { EmployeeLocalTaxService } from './employee-local-tax.service';

@Component({
    selector: 'jhi-employee-local-tax-detail',
    templateUrl: './employee-local-tax-detail.component.html'
})
export class EmployeeLocalTaxDetailComponent implements OnInit, OnDestroy {

    employeeLocalTax: EmployeeLocalTax;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private employeeLocalTaxService: EmployeeLocalTaxService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEmployeeLocalTaxes();
    }

    load(id) {
        this.employeeLocalTaxService.find(id).subscribe((employeeLocalTax) => {
            this.employeeLocalTax = employeeLocalTax;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmployeeLocalTaxes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'employeeLocalTaxListModification',
            (response) => this.load(this.employeeLocalTax.id)
        );
    }
}
