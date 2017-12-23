import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DepositFrequency } from './deposit-frequency.model';
import { DepositFrequencyPopupService } from './deposit-frequency-popup.service';
import { DepositFrequencyService } from './deposit-frequency.service';

@Component({
    selector: 'jhi-deposit-frequency-dialog',
    templateUrl: './deposit-frequency-dialog.component.html'
})
export class DepositFrequencyDialogComponent implements OnInit {

    depositFrequency: DepositFrequency;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private depositFrequencyService: DepositFrequencyService,
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
        if (this.depositFrequency.id !== undefined) {
            this.subscribeToSaveResponse(
                this.depositFrequencyService.update(this.depositFrequency));
        } else {
            this.subscribeToSaveResponse(
                this.depositFrequencyService.create(this.depositFrequency));
        }
    }

    private subscribeToSaveResponse(result: Observable<DepositFrequency>) {
        result.subscribe((res: DepositFrequency) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: DepositFrequency) {
        this.eventManager.broadcast({ name: 'depositFrequencyListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-deposit-frequency-popup',
    template: ''
})
export class DepositFrequencyPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private depositFrequencyPopupService: DepositFrequencyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.depositFrequencyPopupService
                    .open(DepositFrequencyDialogComponent as Component, params['id']);
            } else {
                this.depositFrequencyPopupService
                    .open(DepositFrequencyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
