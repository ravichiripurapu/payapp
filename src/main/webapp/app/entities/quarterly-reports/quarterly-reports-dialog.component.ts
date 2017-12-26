import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { QuarterlyReports } from './quarterly-reports.model';
import { QuarterlyReportsPopupService } from './quarterly-reports-popup.service';
import { QuarterlyReportsService } from './quarterly-reports.service';

@Component({
    selector: 'jhi-quarterly-reports-dialog',
    templateUrl: './quarterly-reports-dialog.component.html'
})
export class QuarterlyReportsDialogComponent implements OnInit {

    quarterlyReports: QuarterlyReports;
    isSaving: boolean;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private quarterlyReportsService: QuarterlyReportsService,
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
        if (this.quarterlyReports.id !== undefined) {
            this.subscribeToSaveResponse(
                this.quarterlyReportsService.update(this.quarterlyReports));
        } else {
            this.subscribeToSaveResponse(
                this.quarterlyReportsService.create(this.quarterlyReports));
        }
    }

    private subscribeToSaveResponse(result: Observable<QuarterlyReports>) {
        result.subscribe((res: QuarterlyReports) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: QuarterlyReports) {
        this.eventManager.broadcast({ name: 'quarterlyReportsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-quarterly-reports-popup',
    template: ''
})
export class QuarterlyReportsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private quarterlyReportsPopupService: QuarterlyReportsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.quarterlyReportsPopupService
                    .open(QuarterlyReportsDialogComponent as Component, params['id']);
            } else {
                this.quarterlyReportsPopupService
                    .open(QuarterlyReportsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
