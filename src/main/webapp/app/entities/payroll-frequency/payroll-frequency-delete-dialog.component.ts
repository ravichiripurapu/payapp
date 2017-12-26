import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PayrollFrequency } from './payroll-frequency.model';
import { PayrollFrequencyPopupService } from './payroll-frequency-popup.service';
import { PayrollFrequencyService } from './payroll-frequency.service';

@Component({
    selector: 'jhi-payroll-frequency-delete-dialog',
    templateUrl: './payroll-frequency-delete-dialog.component.html'
})
export class PayrollFrequencyDeleteDialogComponent {

    payrollFrequency: PayrollFrequency;

    constructor(
        private payrollFrequencyService: PayrollFrequencyService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.payrollFrequencyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'payrollFrequencyListModification',
                content: 'Deleted an payrollFrequency'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-payroll-frequency-delete-popup',
    template: ''
})
export class PayrollFrequencyDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private payrollFrequencyPopupService: PayrollFrequencyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.payrollFrequencyPopupService
                .open(PayrollFrequencyDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
