import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyDeduction } from './company-deduction.model';
import { CompanyDeductionPopupService } from './company-deduction-popup.service';
import { CompanyDeductionService } from './company-deduction.service';

@Component({
    selector: 'jhi-company-deduction-delete-dialog',
    templateUrl: './company-deduction-delete-dialog.component.html'
})
export class CompanyDeductionDeleteDialogComponent {

    companyDeduction: CompanyDeduction;

    constructor(
        private companyDeductionService: CompanyDeductionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.companyDeductionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'companyDeductionListModification',
                content: 'Deleted an companyDeduction'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-company-deduction-delete-popup',
    template: ''
})
export class CompanyDeductionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyDeductionPopupService: CompanyDeductionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.companyDeductionPopupService
                .open(CompanyDeductionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
