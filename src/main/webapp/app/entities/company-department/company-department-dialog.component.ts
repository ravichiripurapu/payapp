import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyDepartment } from './company-department.model';
import { CompanyDepartmentPopupService } from './company-department-popup.service';
import { CompanyDepartmentService } from './company-department.service';

@Component({
    selector: 'jhi-company-department-dialog',
    templateUrl: './company-department-dialog.component.html'
})
export class CompanyDepartmentDialogComponent implements OnInit {

    companyDepartment: CompanyDepartment;
    isSaving: boolean;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private companyDepartmentService: CompanyDepartmentService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.companyDepartment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.companyDepartmentService.update(this.companyDepartment));
        } else {
            this.subscribeToSaveResponse(
                this.companyDepartmentService.create(this.companyDepartment));
        }
    }

    private subscribeToSaveResponse(result: Observable<CompanyDepartment>) {
        result.subscribe((res: CompanyDepartment) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CompanyDepartment) {
        this.eventManager.broadcast({ name: 'companyDepartmentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-company-department-popup',
    template: ''
})
export class CompanyDepartmentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyDepartmentPopupService: CompanyDepartmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.companyDepartmentPopupService
                    .open(CompanyDepartmentDialogComponent as Component, params['id']);
            } else {
                this.companyDepartmentPopupService
                    .open(CompanyDepartmentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
