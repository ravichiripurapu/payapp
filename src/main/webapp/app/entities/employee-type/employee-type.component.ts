import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EmployeeType } from './employee-type.model';
import { EmployeeTypeService } from './employee-type.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-employee-type',
    templateUrl: './employee-type.component.html'
})
export class EmployeeTypeComponent implements OnInit, OnDestroy {
employeeTypes: EmployeeType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private employeeTypeService: EmployeeTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.employeeTypeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.employeeTypes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEmployeeTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: EmployeeType) {
        return item.id;
    }
    registerChangeInEmployeeTypes() {
        this.eventSubscriber = this.eventManager.subscribe('employeeTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
