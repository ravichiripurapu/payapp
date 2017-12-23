import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PayrollSchedule } from './payroll-schedule.model';
import { PayrollSchedulePopupService } from './payroll-schedule-popup.service';
import { PayrollScheduleService } from './payroll-schedule.service';

@Component({
    selector: 'jhi-payroll-schedule-delete-dialog',
    templateUrl: './payroll-schedule-delete-dialog.component.html'
})
export class PayrollScheduleDeleteDialogComponent {

    payrollSchedule: PayrollSchedule;

    constructor(
        private payrollScheduleService: PayrollScheduleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.payrollScheduleService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'payrollScheduleListModification',
                content: 'Deleted an payrollSchedule'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-payroll-schedule-delete-popup',
    template: ''
})
export class PayrollScheduleDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private payrollSchedulePopupService: PayrollSchedulePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.payrollSchedulePopupService
                .open(PayrollScheduleDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
