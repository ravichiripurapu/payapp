import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EmployeeFederalTax } from './employee-federal-tax.model';
import { EmployeeFederalTaxService } from './employee-federal-tax.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-employee-federal-tax',
    templateUrl: './employee-federal-tax.component.html'
})
export class EmployeeFederalTaxComponent implements OnInit, OnDestroy {
employeeFederalTaxes: EmployeeFederalTax[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private employeeFederalTaxService: EmployeeFederalTaxService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.employeeFederalTaxService.query().subscribe(
            (res: ResponseWrapper) => {
                this.employeeFederalTaxes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEmployeeFederalTaxes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: EmployeeFederalTax) {
        return item.id;
    }
    registerChangeInEmployeeFederalTaxes() {
        this.eventSubscriber = this.eventManager.subscribe('employeeFederalTaxListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
