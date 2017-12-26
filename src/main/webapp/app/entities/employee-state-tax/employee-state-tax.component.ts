import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EmployeeStateTax } from './employee-state-tax.model';
import { EmployeeStateTaxService } from './employee-state-tax.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-employee-state-tax',
    templateUrl: './employee-state-tax.component.html'
})
export class EmployeeStateTaxComponent implements OnInit, OnDestroy {
employeeStateTaxes: EmployeeStateTax[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private employeeStateTaxService: EmployeeStateTaxService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.employeeStateTaxService.query().subscribe(
            (res: ResponseWrapper) => {
                this.employeeStateTaxes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEmployeeStateTaxes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: EmployeeStateTax) {
        return item.id;
    }
    registerChangeInEmployeeStateTaxes() {
        this.eventSubscriber = this.eventManager.subscribe('employeeStateTaxListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
