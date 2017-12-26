import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeeBank } from './employee-bank.model';
import { EmployeeBankService } from './employee-bank.service';

@Component({
    selector: 'jhi-employee-bank-detail',
    templateUrl: './employee-bank-detail.component.html'
})
export class EmployeeBankDetailComponent implements OnInit, OnDestroy {

    employeeBank: EmployeeBank;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private employeeBankService: EmployeeBankService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEmployeeBanks();
    }

    load(id) {
        this.employeeBankService.find(id).subscribe((employeeBank) => {
            this.employeeBank = employeeBank;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmployeeBanks() {
        this.eventSubscriber = this.eventManager.subscribe(
            'employeeBankListModification',
            (response) => this.load(this.employeeBank.id)
        );
    }
}
