import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyBank } from './company-bank.model';
import { CompanyBankPopupService } from './company-bank-popup.service';
import { CompanyBankService } from './company-bank.service';

@Component({
    selector: 'jhi-company-bank-dialog',
    templateUrl: './company-bank-dialog.component.html'
})
export class CompanyBankDialogComponent implements OnInit {

    companyBank: CompanyBank;
    isSaving: boolean;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private companyBankService: CompanyBankService,
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
        if (this.companyBank.id !== undefined) {
            this.subscribeToSaveResponse(
                this.companyBankService.update(this.companyBank));
        } else {
            this.subscribeToSaveResponse(
                this.companyBankService.create(this.companyBank));
        }
    }

    private subscribeToSaveResponse(result: Observable<CompanyBank>) {
        result.subscribe((res: CompanyBank) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CompanyBank) {
        this.eventManager.broadcast({ name: 'companyBankListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-company-bank-popup',
    template: ''
})
export class CompanyBankPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyBankPopupService: CompanyBankPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.companyBankPopupService
                    .open(CompanyBankDialogComponent as Component, params['id']);
            } else {
                this.companyBankPopupService
                    .open(CompanyBankDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
