import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeeStateTax } from './employee-state-tax.model';
import { EmployeeStateTaxService } from './employee-state-tax.service';

@Component({
    selector: 'jhi-employee-state-tax-detail',
    templateUrl: './employee-state-tax-detail.component.html'
})
export class EmployeeStateTaxDetailComponent implements OnInit, OnDestroy {

    employeeStateTax: EmployeeStateTax;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private employeeStateTaxService: EmployeeStateTaxService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEmployeeStateTaxes();
    }

    load(id) {
        this.employeeStateTaxService.find(id).subscribe((employeeStateTax) => {
            this.employeeStateTax = employeeStateTax;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmployeeStateTaxes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'employeeStateTaxListModification',
            (response) => this.load(this.employeeStateTax.id)
        );
    }
}
