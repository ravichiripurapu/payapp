import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { W2Reports } from './w-2-reports.model';
import { W2ReportsPopupService } from './w-2-reports-popup.service';
import { W2ReportsService } from './w-2-reports.service';

@Component({
    selector: 'jhi-w-2-reports-dialog',
    templateUrl: './w-2-reports-dialog.component.html'
})
export class W2ReportsDialogComponent implements OnInit {

    w2Reports: W2Reports;
    isSaving: boolean;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private w2ReportsService: W2ReportsService,
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
        if (this.w2Reports.id !== undefined) {
            this.subscribeToSaveResponse(
                this.w2ReportsService.update(this.w2Reports));
        } else {
            this.subscribeToSaveResponse(
                this.w2ReportsService.create(this.w2Reports));
        }
    }

    private subscribeToSaveResponse(result: Observable<W2Reports>) {
        result.subscribe((res: W2Reports) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: W2Reports) {
        this.eventManager.broadcast({ name: 'w2ReportsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-w-2-reports-popup',
    template: ''
})
export class W2ReportsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private w2ReportsPopupService: W2ReportsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.w2ReportsPopupService
                    .open(W2ReportsDialogComponent as Component, params['id']);
            } else {
                this.w2ReportsPopupService
                    .open(W2ReportsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
