import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyDeduction } from './company-deduction.model';
import { CompanyDeductionPopupService } from './company-deduction-popup.service';
import { CompanyDeductionService } from './company-deduction.service';

@Component({
    selector: 'jhi-company-deduction-dialog',
    templateUrl: './company-deduction-dialog.component.html'
})
export class CompanyDeductionDialogComponent implements OnInit {

    companyDeduction: CompanyDeduction;
    isSaving: boolean;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private companyDeductionService: CompanyDeductionService,
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
        if (this.companyDeduction.id !== undefined) {
            this.subscribeToSaveResponse(
                this.companyDeductionService.update(this.companyDeduction));
        } else {
            this.subscribeToSaveResponse(
                this.companyDeductionService.create(this.companyDeduction));
        }
    }

    private subscribeToSaveResponse(result: Observable<CompanyDeduction>) {
        result.subscribe((res: CompanyDeduction) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CompanyDeduction) {
        this.eventManager.broadcast({ name: 'companyDeductionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-company-deduction-popup',
    template: ''
})
export class CompanyDeductionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyDeductionPopupService: CompanyDeductionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.companyDeductionPopupService
                    .open(CompanyDeductionDialogComponent as Component, params['id']);
            } else {
                this.companyDeductionPopupService
                    .open(CompanyDeductionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
