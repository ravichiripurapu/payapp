import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyLocalTax } from './company-local-tax.model';
import { CompanyLocalTaxPopupService } from './company-local-tax-popup.service';
import { CompanyLocalTaxService } from './company-local-tax.service';

@Component({
    selector: 'jhi-company-local-tax-dialog',
    templateUrl: './company-local-tax-dialog.component.html'
})
export class CompanyLocalTaxDialogComponent implements OnInit {

    companyLocalTax: CompanyLocalTax;
    isSaving: boolean;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private companyLocalTaxService: CompanyLocalTaxService,
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
        if (this.companyLocalTax.id !== undefined) {
            this.subscribeToSaveResponse(
                this.companyLocalTaxService.update(this.companyLocalTax));
        } else {
            this.subscribeToSaveResponse(
                this.companyLocalTaxService.create(this.companyLocalTax));
        }
    }

    private subscribeToSaveResponse(result: Observable<CompanyLocalTax>) {
        result.subscribe((res: CompanyLocalTax) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CompanyLocalTax) {
        this.eventManager.broadcast({ name: 'companyLocalTaxListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-company-local-tax-popup',
    template: ''
})
export class CompanyLocalTaxPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyLocalTaxPopupService: CompanyLocalTaxPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.companyLocalTaxPopupService
                    .open(CompanyLocalTaxDialogComponent as Component, params['id']);
            } else {
                this.companyLocalTaxPopupService
                    .open(CompanyLocalTaxDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
