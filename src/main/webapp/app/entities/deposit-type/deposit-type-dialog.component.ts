import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DepositType } from './deposit-type.model';
import { DepositTypePopupService } from './deposit-type-popup.service';
import { DepositTypeService } from './deposit-type.service';

@Component({
    selector: 'jhi-deposit-type-dialog',
    templateUrl: './deposit-type-dialog.component.html'
})
export class DepositTypeDialogComponent implements OnInit {

    depositType: DepositType;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private depositTypeService: DepositTypeService,
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
        if (this.depositType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.depositTypeService.update(this.depositType));
        } else {
            this.subscribeToSaveResponse(
                this.depositTypeService.create(this.depositType));
        }
    }

    private subscribeToSaveResponse(result: Observable<DepositType>) {
        result.subscribe((res: DepositType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: DepositType) {
        this.eventManager.broadcast({ name: 'depositTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-deposit-type-popup',
    template: ''
})
export class DepositTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private depositTypePopupService: DepositTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.depositTypePopupService
                    .open(DepositTypeDialogComponent as Component, params['id']);
            } else {
                this.depositTypePopupService
                    .open(DepositTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
