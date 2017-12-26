import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeeWorkLocation } from './employee-work-location.model';
import { EmployeeWorkLocationService } from './employee-work-location.service';

@Component({
    selector: 'jhi-employee-work-location-detail',
    templateUrl: './employee-work-location-detail.component.html'
})
export class EmployeeWorkLocationDetailComponent implements OnInit, OnDestroy {

    employeeWorkLocation: EmployeeWorkLocation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private employeeWorkLocationService: EmployeeWorkLocationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEmployeeWorkLocations();
    }

    load(id) {
        this.employeeWorkLocationService.find(id).subscribe((employeeWorkLocation) => {
            this.employeeWorkLocation = employeeWorkLocation;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmployeeWorkLocations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'employeeWorkLocationListModification',
            (response) => this.load(this.employeeWorkLocation.id)
        );
    }
}
