import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyLocalTax } from './company-local-tax.model';
import { CompanyLocalTaxPopupService } from './company-local-tax-popup.service';
import { CompanyLocalTaxService } from './company-local-tax.service';

@Component({
    selector: 'jhi-company-local-tax-delete-dialog',
    templateUrl: './company-local-tax-delete-dialog.component.html'
})
export class CompanyLocalTaxDeleteDialogComponent {

    companyLocalTax: CompanyLocalTax;

    constructor(
        private companyLocalTaxService: CompanyLocalTaxService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.companyLocalTaxService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'companyLocalTaxListModification',
                content: 'Deleted an companyLocalTax'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-company-local-tax-delete-popup',
    template: ''
})
export class CompanyLocalTaxDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyLocalTaxPopupService: CompanyLocalTaxPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.companyLocalTaxPopupService
                .open(CompanyLocalTaxDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
