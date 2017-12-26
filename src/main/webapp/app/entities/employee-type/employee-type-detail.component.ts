import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeeType } from './employee-type.model';
import { EmployeeTypeService } from './employee-type.service';

@Component({
    selector: 'jhi-employee-type-detail',
    templateUrl: './employee-type-detail.component.html'
})
export class EmployeeTypeDetailComponent implements OnInit, OnDestroy {

    employeeType: EmployeeType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private employeeTypeService: EmployeeTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEmployeeTypes();
    }

    load(id) {
        this.employeeTypeService.find(id).subscribe((employeeType) => {
            this.employeeType = employeeType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmployeeTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'employeeTypeListModification',
            (response) => this.load(this.employeeType.id)
        );
    }
}
