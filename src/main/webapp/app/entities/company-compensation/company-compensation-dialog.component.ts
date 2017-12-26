import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyCompensation } from './company-compensation.model';
import { CompanyCompensationPopupService } from './company-compensation-popup.service';
import { CompanyCompensationService } from './company-compensation.service';

@Component({
    selector: 'jhi-company-compensation-dialog',
    templateUrl: './company-compensation-dialog.component.html'
})
export class CompanyCompensationDialogComponent implements OnInit {

    companyCompensation: CompanyCompensation;
    isSaving: boolean;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private companyCompensationService: CompanyCompensationService,
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
        if (this.companyCompensation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.companyCompensationService.update(this.companyCompensation));
        } else {
            this.subscribeToSaveResponse(
                this.companyCompensationService.create(this.companyCompensation));
        }
    }

    private subscribeToSaveResponse(result: Observable<CompanyCompensation>) {
        result.subscribe((res: CompanyCompensation) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CompanyCompensation) {
        this.eventManager.broadcast({ name: 'companyCompensationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-company-compensation-popup',
    template: ''
})
export class CompanyCompensationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyCompensationPopupService: CompanyCompensationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.companyCompensationPopupService
                    .open(CompanyCompensationDialogComponent as Component, params['id']);
            } else {
                this.companyCompensationPopupService
                    .open(CompanyCompensationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
