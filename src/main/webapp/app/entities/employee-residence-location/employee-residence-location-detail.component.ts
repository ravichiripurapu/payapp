import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeeResidenceLocation } from './employee-residence-location.model';
import { EmployeeResidenceLocationService } from './employee-residence-location.service';

@Component({
    selector: 'jhi-employee-residence-location-detail',
    templateUrl: './employee-residence-location-detail.component.html'
})
export class EmployeeResidenceLocationDetailComponent implements OnInit, OnDestroy {

    employeeResidenceLocation: EmployeeResidenceLocation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private employeeResidenceLocationService: EmployeeResidenceLocationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEmployeeResidenceLocations();
    }

    load(id) {
        this.employeeResidenceLocationService.find(id).subscribe((employeeResidenceLocation) => {
            this.employeeResidenceLocation = employeeResidenceLocation;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmployeeResidenceLocations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'employeeResidenceLocationListModification',
            (response) => this.load(this.employeeResidenceLocation.id)
        );
    }
}
