import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyContactType } from './company-contact-type.model';
import { CompanyContactTypePopupService } from './company-contact-type-popup.service';
import { CompanyContactTypeService } from './company-contact-type.service';

@Component({
    selector: 'jhi-company-contact-type-dialog',
    templateUrl: './company-contact-type-dialog.component.html'
})
export class CompanyContactTypeDialogComponent implements OnInit {

    companyContactType: CompanyContactType;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private companyContactTypeService: CompanyContactTypeService,
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
        if (this.companyContactType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.companyContactTypeService.update(this.companyContactType));
        } else {
            this.subscribeToSaveResponse(
                this.companyContactTypeService.create(this.companyContactType));
        }
    }

    private subscribeToSaveResponse(result: Observable<CompanyContactType>) {
        result.subscribe((res: CompanyContactType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CompanyContactType) {
        this.eventManager.broadcast({ name: 'companyContactTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-company-contact-type-popup',
    template: ''
})
export class CompanyContactTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyContactTypePopupService: CompanyContactTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.companyContactTypePopupService
                    .open(CompanyContactTypeDialogComponent as Component, params['id']);
            } else {
                this.companyContactTypePopupService
                    .open(CompanyContactTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
