import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FilingStatus } from './filing-status.model';
import { FilingStatusPopupService } from './filing-status-popup.service';
import { FilingStatusService } from './filing-status.service';

@Component({
    selector: 'jhi-filing-status-dialog',
    templateUrl: './filing-status-dialog.component.html'
})
export class FilingStatusDialogComponent implements OnInit {

    filingStatus: FilingStatus;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private filingStatusService: FilingStatusService,
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
        if (this.filingStatus.id !== undefined) {
            this.subscribeToSaveResponse(
                this.filingStatusService.update(this.filingStatus));
        } else {
            this.subscribeToSaveResponse(
                this.filingStatusService.create(this.filingStatus));
        }
    }

    private subscribeToSaveResponse(result: Observable<FilingStatus>) {
        result.subscribe((res: FilingStatus) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: FilingStatus) {
        this.eventManager.broadcast({ name: 'filingStatusListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-filing-status-popup',
    template: ''
})
export class FilingStatusPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private filingStatusPopupService: FilingStatusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.filingStatusPopupService
                    .open(FilingStatusDialogComponent as Component, params['id']);
            } else {
                this.filingStatusPopupService
                    .open(FilingStatusDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
