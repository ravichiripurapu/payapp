import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyStateTax } from './company-state-tax.model';
import { CompanyStateTaxPopupService } from './company-state-tax-popup.service';
import { CompanyStateTaxService } from './company-state-tax.service';

@Component({
    selector: 'jhi-company-state-tax-delete-dialog',
    templateUrl: './company-state-tax-delete-dialog.component.html'
})
export class CompanyStateTaxDeleteDialogComponent {

    companyStateTax: CompanyStateTax;

    constructor(
        private companyStateTaxService: CompanyStateTaxService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.companyStateTaxService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'companyStateTaxListModification',
                content: 'Deleted an companyStateTax'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-company-state-tax-delete-popup',
    template: ''
})
export class CompanyStateTaxDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyStateTaxPopupService: CompanyStateTaxPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.companyStateTaxPopupService
                .open(CompanyStateTaxDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
