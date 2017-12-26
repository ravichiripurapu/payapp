import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DeductionType } from './deduction-type.model';
import { DeductionTypePopupService } from './deduction-type-popup.service';
import { DeductionTypeService } from './deduction-type.service';

@Component({
    selector: 'jhi-deduction-type-dialog',
    templateUrl: './deduction-type-dialog.component.html'
})
export class DeductionTypeDialogComponent implements OnInit {

    deductionType: DeductionType;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private deductionTypeService: DeductionTypeService,
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
        if (this.deductionType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.deductionTypeService.update(this.deductionType));
        } else {
            this.subscribeToSaveResponse(
                this.deductionTypeService.create(this.deductionType));
        }
    }

    private subscribeToSaveResponse(result: Observable<DeductionType>) {
        result.subscribe((res: DeductionType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: DeductionType) {
        this.eventManager.broadcast({ name: 'deductionTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-deduction-type-popup',
    template: ''
})
export class DeductionTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private deductionTypePopupService: DeductionTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.deductionTypePopupService
                    .open(DeductionTypeDialogComponent as Component, params['id']);
            } else {
                this.deductionTypePopupService
                    .open(DeductionTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
