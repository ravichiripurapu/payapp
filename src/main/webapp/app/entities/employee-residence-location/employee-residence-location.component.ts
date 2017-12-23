import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EmployeeResidenceLocation } from './employee-residence-location.model';
import { EmployeeResidenceLocationService } from './employee-residence-location.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-employee-residence-location',
    templateUrl: './employee-residence-location.component.html'
})
export class EmployeeResidenceLocationComponent implements OnInit, OnDestroy {
employeeResidenceLocations: EmployeeResidenceLocation[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private employeeResidenceLocationService: EmployeeResidenceLocationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.employeeResidenceLocationService.query().subscribe(
            (res: ResponseWrapper) => {
                this.employeeResidenceLocations = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEmployeeResidenceLocations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: EmployeeResidenceLocation) {
        return item.id;
    }
    registerChangeInEmployeeResidenceLocations() {
        this.eventSubscriber = this.eventManager.subscribe('employeeResidenceLocationListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
