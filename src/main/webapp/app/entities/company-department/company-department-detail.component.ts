import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyDepartment } from './company-department.model';
import { CompanyDepartmentService } from './company-department.service';

@Component({
    selector: 'jhi-company-department-detail',
    templateUrl: './company-department-detail.component.html'
})
export class CompanyDepartmentDetailComponent implements OnInit, OnDestroy {

    companyDepartment: CompanyDepartment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private companyDepartmentService: CompanyDepartmentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCompanyDepartments();
    }

    load(id) {
        this.companyDepartmentService.find(id).subscribe((companyDepartment) => {
            this.companyDepartment = companyDepartment;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCompanyDepartments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'companyDepartmentListModification',
            (response) => this.load(this.companyDepartment.id)
        );
    }
}
