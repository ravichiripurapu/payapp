import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyStateTax } from './company-state-tax.model';
import { CompanyStateTaxPopupService } from './company-state-tax-popup.service';
import { CompanyStateTaxService } from './company-state-tax.service';

@Component({
    selector: 'jhi-company-state-tax-dialog',
    templateUrl: './company-state-tax-dialog.component.html'
})
export class CompanyStateTaxDialogComponent implements OnInit {

    companyStateTax: CompanyStateTax;
    isSaving: boolean;
    effectiveDateDp: any;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private companyStateTaxService: CompanyStateTaxService,
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
        if (this.companyStateTax.id !== undefined) {
            this.subscribeToSaveResponse(
                this.companyStateTaxService.update(this.companyStateTax));
        } else {
            this.subscribeToSaveResponse(
                this.companyStateTaxService.create(this.companyStateTax));
        }
    }

    private subscribeToSaveResponse(result: Observable<CompanyStateTax>) {
        result.subscribe((res: CompanyStateTax) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CompanyStateTax) {
        this.eventManager.broadcast({ name: 'companyStateTaxListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-company-state-tax-popup',
    template: ''
})
export class CompanyStateTaxPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyStateTaxPopupService: CompanyStateTaxPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.companyStateTaxPopupService
                    .open(CompanyStateTaxDialogComponent as Component, params['id']);
            } else {
                this.companyStateTaxPopupService
                    .open(CompanyStateTaxDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
