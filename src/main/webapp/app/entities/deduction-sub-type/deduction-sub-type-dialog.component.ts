import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DeductionSubType } from './deduction-sub-type.model';
import { DeductionSubTypePopupService } from './deduction-sub-type-popup.service';
import { DeductionSubTypeService } from './deduction-sub-type.service';

@Component({
    selector: 'jhi-deduction-sub-type-dialog',
    templateUrl: './deduction-sub-type-dialog.component.html'
})
export class DeductionSubTypeDialogComponent implements OnInit {

    deductionSubType: DeductionSubType;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private deductionSubTypeService: DeductionSubTypeService,
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
        if (this.deductionSubType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.deductionSubTypeService.update(this.deductionSubType));
        } else {
            this.subscribeToSaveResponse(
                this.deductionSubTypeService.create(this.deductionSubType));
        }
    }

    private subscribeToSaveResponse(result: Observable<DeductionSubType>) {
        result.subscribe((res: DeductionSubType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: DeductionSubType) {
        this.eventManager.broadcast({ name: 'deductionSubTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-deduction-sub-type-popup',
    template: ''
})
export class DeductionSubTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private deductionSubTypePopupService: DeductionSubTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.deductionSubTypePopupService
                    .open(DeductionSubTypeDialogComponent as Component, params['id']);
            } else {
                this.deductionSubTypePopupService
                    .open(DeductionSubTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
