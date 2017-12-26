import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyDepartment } from './company-department.model';
import { CompanyDepartmentPopupService } from './company-department-popup.service';
import { CompanyDepartmentService } from './company-department.service';

@Component({
    selector: 'jhi-company-department-delete-dialog',
    templateUrl: './company-department-delete-dialog.component.html'
})
export class CompanyDepartmentDeleteDialogComponent {

    companyDepartment: CompanyDepartment;

    constructor(
        private companyDepartmentService: CompanyDepartmentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.companyDepartmentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'companyDepartmentListModification',
                content: 'Deleted an companyDepartment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-company-department-delete-popup',
    template: ''
})
export class CompanyDepartmentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyDepartmentPopupService: CompanyDepartmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.companyDepartmentPopupService
                .open(CompanyDepartmentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
