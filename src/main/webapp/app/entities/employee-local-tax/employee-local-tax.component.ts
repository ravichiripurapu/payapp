import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EmployeeLocalTax } from './employee-local-tax.model';
import { EmployeeLocalTaxService } from './employee-local-tax.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-employee-local-tax',
    templateUrl: './employee-local-tax.component.html'
})
export class EmployeeLocalTaxComponent implements OnInit, OnDestroy {
employeeLocalTaxes: EmployeeLocalTax[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private employeeLocalTaxService: EmployeeLocalTaxService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.employeeLocalTaxService.query().subscribe(
            (res: ResponseWrapper) => {
                this.employeeLocalTaxes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEmployeeLocalTaxes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: EmployeeLocalTax) {
        return item.id;
    }
    registerChangeInEmployeeLocalTaxes() {
        this.eventSubscriber = this.eventManager.subscribe('employeeLocalTaxListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
