import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CompanyDepartment } from './company-department.model';
import { CompanyDepartmentService } from './company-department.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-company-department',
    templateUrl: './company-department.component.html'
})
export class CompanyDepartmentComponent implements OnInit, OnDestroy {
companyDepartments: CompanyDepartment[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private companyDepartmentService: CompanyDepartmentService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.companyDepartmentService.query().subscribe(
            (res: ResponseWrapper) => {
                this.companyDepartments = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCompanyDepartments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CompanyDepartment) {
        return item.id;
    }
    registerChangeInCompanyDepartments() {
        this.eventSubscriber = this.eventManager.subscribe('companyDepartmentListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
