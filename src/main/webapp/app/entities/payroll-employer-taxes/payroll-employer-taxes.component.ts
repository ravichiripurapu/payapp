import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PayrollEmployerTaxes } from './payroll-employer-taxes.model';
import { PayrollEmployerTaxesService } from './payroll-employer-taxes.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-payroll-employer-taxes',
    templateUrl: './payroll-employer-taxes.component.html'
})
export class PayrollEmployerTaxesComponent implements OnInit, OnDestroy {
payrollEmployerTaxes: PayrollEmployerTaxes[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private payrollEmployerTaxesService: PayrollEmployerTaxesService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.payrollEmployerTaxesService.query().subscribe(
            (res: ResponseWrapper) => {
                this.payrollEmployerTaxes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPayrollEmployerTaxes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PayrollEmployerTaxes) {
        return item.id;
    }
    registerChangeInPayrollEmployerTaxes() {
        this.eventSubscriber = this.eventManager.subscribe('payrollEmployerTaxesListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
