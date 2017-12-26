import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EmployeeWorkLocation } from './employee-work-location.model';
import { EmployeeWorkLocationService } from './employee-work-location.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-employee-work-location',
    templateUrl: './employee-work-location.component.html'
})
export class EmployeeWorkLocationComponent implements OnInit, OnDestroy {
employeeWorkLocations: EmployeeWorkLocation[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private employeeWorkLocationService: EmployeeWorkLocationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.employeeWorkLocationService.query().subscribe(
            (res: ResponseWrapper) => {
                this.employeeWorkLocations = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEmployeeWorkLocations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: EmployeeWorkLocation) {
        return item.id;
    }
    registerChangeInEmployeeWorkLocations() {
        this.eventSubscriber = this.eventManager.subscribe('employeeWorkLocationListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
