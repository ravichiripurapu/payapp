import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeeBank } from './employee-bank.model';
import { EmployeeBankPopupService } from './employee-bank-popup.service';
import { EmployeeBankService } from './employee-bank.service';

@Component({
    selector: 'jhi-employee-bank-delete-dialog',
    templateUrl: './employee-bank-delete-dialog.component.html'
})
export class EmployeeBankDeleteDialogComponent {

    employeeBank: EmployeeBank;

    constructor(
        private employeeBankService: EmployeeBankService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.employeeBankService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'employeeBankListModification',
                content: 'Deleted an employeeBank'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-employee-bank-delete-popup',
    template: ''
})
export class EmployeeBankDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employeeBankPopupService: EmployeeBankPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.employeeBankPopupService
                .open(EmployeeBankDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
