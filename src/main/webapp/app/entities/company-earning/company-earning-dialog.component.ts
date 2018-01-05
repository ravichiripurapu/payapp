import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyEarning } from './company-earning.model';
import { CompanyEarningPopupService } from './company-earning-popup.service';
import { CompanyEarningService } from './company-earning.service';

@Component({
    selector: 'jhi-company-earning-dialog',
    templateUrl: './company-earning-dialog.component.html'
})
export class CompanyEarningDialogComponent implements OnInit {

    companyEarningType: CompanyEarning;
    isSaving: boolean;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private companyEarningService: CompanyEarningService,
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
        if (this.companyEarningType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.companyEarningService.update(this.companyEarningType));
        } else {
            this.subscribeToSaveResponse(
                this.companyEarningService.create(this.companyEarningType));
        }
    }

    private subscribeToSaveResponse(result: Observable<CompanyEarning>) {
        result.subscribe((res: CompanyEarning) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CompanyEarning) {
        this.eventManager.broadcast({ name: 'companyEarningListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-company-earning-popup',
    template: ''
})
export class CompanyEarningPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyEarningPopupService: CompanyEarningPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.companyEarningPopupService
                    .open(CompanyEarningDialogComponent as Component, params['id']);
            } else {
                this.companyEarningPopupService
                    .open(CompanyEarningDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
