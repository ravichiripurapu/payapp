import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FutaExemptReasonCode } from './futa-exempt-reason-code.model';
import { FutaExemptReasonCodePopupService } from './futa-exempt-reason-code-popup.service';
import { FutaExemptReasonCodeService } from './futa-exempt-reason-code.service';

@Component({
    selector: 'jhi-futa-exempt-reason-code-dialog',
    templateUrl: './futa-exempt-reason-code-dialog.component.html'
})
export class FutaExemptReasonCodeDialogComponent implements OnInit {

    futaExemptReasonCode: FutaExemptReasonCode;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private futaExemptReasonCodeService: FutaExemptReasonCodeService,
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
        if (this.futaExemptReasonCode.id !== undefined) {
            this.subscribeToSaveResponse(
                this.futaExemptReasonCodeService.update(this.futaExemptReasonCode));
        } else {
            this.subscribeToSaveResponse(
                this.futaExemptReasonCodeService.create(this.futaExemptReasonCode));
        }
    }

    private subscribeToSaveResponse(result: Observable<FutaExemptReasonCode>) {
        result.subscribe((res: FutaExemptReasonCode) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: FutaExemptReasonCode) {
        this.eventManager.broadcast({ name: 'futaExemptReasonCodeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-futa-exempt-reason-code-popup',
    template: ''
})
export class FutaExemptReasonCodePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private futaExemptReasonCodePopupService: FutaExemptReasonCodePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.futaExemptReasonCodePopupService
                    .open(FutaExemptReasonCodeDialogComponent as Component, params['id']);
            } else {
                this.futaExemptReasonCodePopupService
                    .open(FutaExemptReasonCodeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
