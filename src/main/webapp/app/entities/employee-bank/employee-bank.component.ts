import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EmployeeBank } from './employee-bank.model';
import { EmployeeBankService } from './employee-bank.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-employee-bank',
    templateUrl: './employee-bank.component.html'
})
export class EmployeeBankComponent implements OnInit, OnDestroy {
employeeBanks: EmployeeBank[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private employeeBankService: EmployeeBankService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.employeeBankService.query().subscribe(
            (res: ResponseWrapper) => {
                this.employeeBanks = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEmployeeBanks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: EmployeeBank) {
        return item.id;
    }
    registerChangeInEmployeeBanks() {
        this.eventSubscriber = this.eventManager.subscribe('employeeBankListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
